package gr.aueb.cf.schoolappssr.mapper;

//import gr.aueb.cf.schoolappssr.core.enums.Role;
import gr.aueb.cf.schoolappssr.dto.TeacherEditDTO;
import gr.aueb.cf.schoolappssr.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappssr.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolappssr.dto.UserInsertDTO;
import gr.aueb.cf.schoolappssr.model.Teacher;
import gr.aueb.cf.schoolappssr.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Teacher mapToTeacherEntity (TeacherInsertDTO dto) {
        return  new Teacher(null, null, dto.getVat(), dto.getFirstname(), dto.getLastname(), null);
    }

    public TeacherReadOnlyDTO mapToTeacherReadOnlyDTO (Teacher teacher ) {
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getCreatedAt(), teacher.getUpdatedAt(), teacher.getUuid(),
                teacher.getFirstname(), teacher.getLastname(), teacher.getVat(), teacher.getRegion().getName());
    }

    public TeacherEditDTO mapToTeacherEditDTO( Teacher teacher){
        return new TeacherEditDTO(teacher.getUuid(), teacher.getFirstname(),
                teacher.getLastname(), teacher.getVat(), teacher.getRegion().getId());
    }

    public User mapToUserEntity (UserInsertDTO userInsertDTO){
//        return new User(userInsertDTO.getUsername(), userInsertDTO.getPassword(),
//                Role.valueOf(userInsertDTO.getRole().toUpperCase())); //επειδή στον user το role είναι String , valueOf κανει typecast
        return User.builder()
                .username(userInsertDTO.getUsername())
                .password(userInsertDTO.getPassword())
//                .role(Role.valueOf(userInsertDTO.getRole().toUpperCase()))
                .build();
    }

    //After changing from enum to entity Role...ο user έχει μέσα του role entity ενώ το dto έχει roleId...
    // οπότε πρέπει να γίνει map , είτε στον mapper, είτε στο service.
    //We did that in service.
}
