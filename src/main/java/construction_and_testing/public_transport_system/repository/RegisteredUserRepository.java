package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
}
