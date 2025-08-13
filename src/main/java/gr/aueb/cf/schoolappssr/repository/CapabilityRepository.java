package gr.aueb.cf.schoolappssr.repository;

import gr.aueb.cf.schoolappssr.model.auth.Capability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapabilityRepository extends JpaRepository<Capability,Long> {
}
