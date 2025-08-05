package gr.aueb.cf.schoolappssr.service;

import gr.aueb.cf.schoolappssr.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolappssr.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolappssr.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappssr.model.Teacher;

public interface ITeacherService {
    Teacher saveTeacher(TeacherInsertDTO teacherInsertDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;
}
