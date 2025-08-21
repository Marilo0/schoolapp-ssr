package gr.aueb.cf.schoolappssr.service;

import gr.aueb.cf.schoolappssr.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolappssr.dto.UserInsertDTO;
import gr.aueb.cf.schoolappssr.mapper.Mapper;
import gr.aueb.cf.schoolappssr.model.User;
import gr.aueb.cf.schoolappssr.model.auth.Role;
import gr.aueb.cf.schoolappssr.repository.RoleRepository;
import gr.aueb.cf.schoolappssr.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;
    private final RoleRepository roleRepository;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveUser(UserInsertDTO userInsertDTO) throws EntityAlreadyExistsException {

        try {
            if (userRepository.findByUsername(userInsertDTO.getUsername()).isPresent()){
                throw new EntityAlreadyExistsException("User", "Ο χρήστης με username: " +
                        userInsertDTO.getUsername() + "υπάρχει ήδη.");
            }
            User user = mapper.mapToUserEntity(userInsertDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role role = roleRepository.findById(userInsertDTO.getRoleId()).orElse(null);
            user.setRole(role);
            userRepository.save(user);
            log.info("Η αποθήκευση ολοκληρώθηκε επιτυχώς για τον χρήστη με username={}", userInsertDTO.getUsername());


        }catch (EntityAlreadyExistsException e){
            log.error("Η αποθήκευση απέτυχε για τον χρήστη με username={}", userInsertDTO.getUsername(), e);
            throw e;
        }
    }
}
