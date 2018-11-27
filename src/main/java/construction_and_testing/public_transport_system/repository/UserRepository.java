package construction_and_testing.public_transport_system.repository;


import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value  = "SELECT * FROM User u WHERE u.username = ?1", nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "SELECT type FROM User u WHERE u.username = ?1", nativeQuery = true)
    AuthorityType getAuthority(String username);

    @Query( value = "SELECT * FROM User u WHERE u.confirmation = false", nativeQuery = true)
    List<User> getUnvalidatedUsers();

}
