package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.ValidatorDTO;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("VALIDATOR")
public class Validator extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    public Validator() {
    }

    public Validator(Long id, String name, String lastName, String username, String password, String email,
                     String telephone, boolean active) {
        super(id, name, lastName, username, password, email, telephone, active);
    }

    public Validator(User user) {
        super(user.id, user.firstName, user.lastName, user.username, user.password, user.email, user.telephone, user.isActive());
        this.setAuthorityType(AuthorityType.VALIDATOR);
    }

    public Validator(ValidatorDTO user) {
        super(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getTelephone(), user.isActive());
        this.setAuthorityType(AuthorityType.VALIDATOR);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
