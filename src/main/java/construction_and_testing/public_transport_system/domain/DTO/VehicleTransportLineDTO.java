package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.TransportLine;

import java.io.Serializable;

public class VehicleTransportLineDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    public VehicleTransportLineDTO() {
    }

    public VehicleTransportLineDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public VehicleTransportLineDTO(TransportLine transportLine) {
        this.id = transportLine.getId();
        this.name = transportLine.getName();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
