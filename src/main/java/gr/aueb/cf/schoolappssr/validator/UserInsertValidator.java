package gr.aueb.cf.schoolappssr.validator;

import gr.aueb.cf.schoolappssr.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappssr.dto.UserInsertDTO;
import gr.aueb.cf.schoolappssr.repository.RegionRepository;
import gr.aueb.cf.schoolappssr.repository.TeacherRepository;
import gr.aueb.cf.schoolappssr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserInsertValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserInsertDTO.class == clazz;
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UserInsertDTO userInsertDTO = (UserInsertDTO) target;

        if (userRepository.findByUsername(userInsertDTO.getUsername()).isPresent()) {
            log.error("Save failed for user with username={}.", userInsertDTO.getUsername());
            errors.rejectValue("username", "username.user.exists");
        }
    }
}
