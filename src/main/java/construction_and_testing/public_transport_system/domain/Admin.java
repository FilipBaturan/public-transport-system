package construction_and_testing.public_transport_system.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Add later
 */
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
