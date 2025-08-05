package gr.aueb.cf.schoolappssr.mapper;

import gr.aueb.cf.schoolappssr.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappssr.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Teacher mapToTeacherEntity (TeacherInsertDTO dto) {
        return  new Teacher(null, null, dto.getVat(), dto.getFirstname(), dto.getLastname(), null);
    }
}
