package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Validator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidatorRepository extends JpaRepository<Validator, Long> {
}
