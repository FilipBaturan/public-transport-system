package construction_and_testing.public_transport_system.domain;

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
                     String telephone, boolean confirmation) {
        super(id, name, lastName, username, password, email, telephone, confirmation);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
