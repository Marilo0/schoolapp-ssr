package gr.aueb.cf.schoolappssr.repository;

import gr.aueb.cf.schoolappssr.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
