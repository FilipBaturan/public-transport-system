package construction_and_testing.public_transport_system.repository;


import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;
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

    @Query( value = "SELECT * FROM User u WHERE u.confirmation = 2 AND" +
            " u.authority = 0 AND u.active = true", nativeQuery = true)
    List<User> getUnvalidatedUsers();


    @Query( value = "SELECT * FROM User u WHERE u.active = true AND u.authority = 2", nativeQuery = true)
    List<Validator> getValidators();

    @Query( value = "SELECT * FROM User u WHERE u.active = true AND u.authority = 0", nativeQuery = true)
    List<RegisteredUser> getRegisteredUsers();
}
