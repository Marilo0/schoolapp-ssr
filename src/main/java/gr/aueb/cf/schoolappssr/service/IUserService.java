package gr.aueb.cf.schoolappssr.service;

import gr.aueb.cf.schoolappssr.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolappssr.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolappssr.dto.UserInsertDTO;

public interface IUserService {

    void saveUser(UserInsertDTO userInsertDTO) throws EntityAlreadyExistsException;
}
