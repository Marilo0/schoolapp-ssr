package gr.aueb.cf.schoolappssr.controller;

import gr.aueb.cf.schoolappssr.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappssr.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/school/teachers")
public class TeacherController {

    private final RegionRepository regionRepository;
    @Autowired
    public TeacherController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping("/insert")
    public String getTeacherForm(Model model){
        model.addAttribute("teacherInsertDTO", new TeacherInsertDTO());
        model.addAttribute("regions", regionRepository.findAll());
        return "teacher-form";
    }
}
