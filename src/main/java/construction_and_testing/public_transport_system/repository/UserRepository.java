package construction_and_testing.public_transport_system.repository;


import construction_and_testing.public_transport_system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
