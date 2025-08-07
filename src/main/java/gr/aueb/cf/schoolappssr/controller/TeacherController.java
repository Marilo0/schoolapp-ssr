package gr.aueb.cf.schoolappssr.controller;

import gr.aueb.cf.schoolappssr.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolappssr.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolappssr.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolappssr.dto.TeacherEditDTO;
import gr.aueb.cf.schoolappssr.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappssr.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolappssr.mapper.Mapper;
import gr.aueb.cf.schoolappssr.model.Teacher;
import gr.aueb.cf.schoolappssr.repository.RegionRepository;
import gr.aueb.cf.schoolappssr.repository.TeacherRepository;
import gr.aueb.cf.schoolappssr.service.ITeacherService;
import gr.aueb.cf.schoolappssr.validator.TeacherInsertValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/school/teachers")
@RequiredArgsConstructor      //used in dependency-injection, fields need to be final in order to recognise them
@Slf4j
public class TeacherController {

    private final ITeacherService teacherService;
    private final RegionRepository regionRepository;
    private final TeacherRepository teacherRepository;
    private final Mapper mapper;
    private final TeacherInsertValidator teacherInsertValidator;

//    @Autowired
//    public TeacherController(RegionRepository regionRepository) {
//        this.regionRepository = regionRepository;
//    }

    @GetMapping("/insert")
    public String getTeacherForm(Model model){
        model.addAttribute("teacherInsertDTO", new TeacherInsertDTO());
        model.addAttribute("regions", regionRepository.findAll(Sort.by("name")));  //PagingAndSortingRepository
        return "teacher-form";
    }

    @PostMapping("/insert")
    public String saveTeacher(@Valid @ModelAttribute("teacherInsertDTO") TeacherInsertDTO teacherInsertDTO,
                              BindingResult bindingResult,Model model, RedirectAttributes redirectAttributes){
        Teacher savedTeacher;

        teacherInsertValidator.validate(teacherInsertDTO, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("regions", regionRepository.findAll(Sort.by("name")));
            return "teacher-form";
        }

        try {
            savedTeacher = teacherService.saveTeacher(teacherInsertDTO);
            TeacherReadOnlyDTO readOnlyDTO = mapper.mapToTeacherReadOnlyDTO(savedTeacher);
            redirectAttributes.addFlashAttribute("teacher", readOnlyDTO);
            // Post - Redirect-Get (PRG) pattern είναι ένα web-development pattern
            // εμποδίζει τα duplicate submissions κάνοντας redirect μετά από ένα POST
            return  "redirect:/school/teachers/view";   //!! not just return, but redirect

        }catch (EntityAlreadyExistsException | EntityInvalidArgumentException e){
            model.addAttribute("regions", regionRepository.findAll(Sort.by("name")));
            model.addAttribute("errorMessage", e.getMessage());
            return "teacher-form";
        }
    }

    @GetMapping("/view")
    public String getPaginatedTeachers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model){

        Page<TeacherReadOnlyDTO> teachersPage = teacherService.getPaginatedTeachers(page,size);

        model.addAttribute("teachersPage", teachersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", teachersPage.getTotalPages());
        return "teachers";
    }

    @GetMapping("/edit/{uuid}")
    public String showEditForm(@PathVariable String  uuid,  Model model){
        try{
            Teacher teacher = teacherRepository.findByUuid(uuid)
                    .orElseThrow(()-> EntityNotFoundException("Teacher", "Teacher not found"));
            model.addAttribute("teacherEditDTO", mapper.mapToTeacherEditDTO(teacher));
            model.addAttribute("regions", regionRepository.findAll(Sort.by("name")));
            return "teacher-edit-form";
        }catch (EntityNotFoundException e){
            log.error("Teacher with uuid={} not updated", uuid, e);
            model.addAttribute("regions", regionRepository.findAll(Sort.by("name")));
            model.addAttribute("errorMessage", e.getMessage());
            return "teacher-edit-form";
        }

    }

    @PostMapping("/edit")
    public String updateTeacher(@Valid @ModelAttribute("teacherEditDTO") TeacherEditDTO teacherEditDTO,
                              BindingResult bindingResult,Model model){

        Teacher updatedTeacher;

        //teacherInsertValidator.validate(teacherEditDTOtDTO, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("regions", regionRepository.findAll(Sort.by("name")));
            return "teacher-edit-form";
        }

        try {
            updatedTeacher = teacherService.updatedTeacher(teacherEditDTO);
            TeacherReadOnlyDTO readOnlyDTO = mapper.mapToTeacherReadOnlyDTO(updatedTeacher);
            //redirectAttributes.addFlashAttribute("teacher", readOnlyDTO);
            model.addAttribute("teacher", mapper.mapToTeacherReadOnlyDTO(updatedTeacher));
            return  "/school/teachers/view";

        }catch (EntityAlreadyExistsException | EntityInvalidArgumentException | EntityNotFoundException e){
            model.addAttribute("regions", regionRepository.findAll(Sort.by("name")));
            model.addAttribute("errorMessage", e.getMessage());
            return "teacher-edit-form";
        }
    }
}
