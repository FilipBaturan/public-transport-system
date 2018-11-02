package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
