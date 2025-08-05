package gr.aueb.cf.schoolappssr.controller;

import gr.aueb.cf.schoolappssr.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolappssr.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolappssr.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappssr.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolappssr.mapper.Mapper;
import gr.aueb.cf.schoolappssr.model.Teacher;
import gr.aueb.cf.schoolappssr.repository.RegionRepository;
import gr.aueb.cf.schoolappssr.service.ITeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/school/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final ITeacherService teacherService;
    private final RegionRepository regionRepository;
    private final Mapper mapper;

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

    public String saveTeacher(@Valid @ModelAttribute("teacherInsertDTO") TeacherInsertDTO teacherInsertDTO,
                              BindingResult bindingResult,Model model, RedirectAttributes redirectAttributes){
        Teacher savedTeacher;

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
            return  "redirect:/school/teachers";   //!! not just return, but redirect

        }catch (EntityAlreadyExistsException | EntityInvalidArgumentException e){
            model.addAttribute("regions", regionRepository.findAll(Sort.by("name")));
            model.addAttribute("errorMessage", e.getMessage());
            return "teacher-form";
        }
    }
}
