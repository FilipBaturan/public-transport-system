package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Add later
 */
@Entity
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private VehicleType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private TransportLine currentLine;
}
