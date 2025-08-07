package gr.aueb.cf.schoolappssr.service;

import gr.aueb.cf.schoolappssr.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolappssr.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolappssr.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolappssr.dto.TeacherEditDTO;
import gr.aueb.cf.schoolappssr.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappssr.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolappssr.model.Teacher;
import org.springframework.data.domain.Page;

public interface ITeacherService {
    Teacher saveTeacher(TeacherInsertDTO teacherInsertDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;

    Page<TeacherReadOnlyDTO> getPaginatedTeachers(int page, int size);

    Teacher updateTeacher(TeacherEditDTO dto) throws EntityAlreadyExistsException, EntityInvalidArgumentException, EntityNotFoundException;
}
