package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

    @Modifying
    @Query(value = "UPDATE user SET document = :document WHERE id = :id", nativeQuery = true)
    public void updateUsersValidationDocument(@Param("document") String document, @Param("id") Long id);

    @Query(value = "SELECT document FROM user WHERE id = :id", nativeQuery = true)
    public String getUsersDocument(@Param("id") Long id);
}
