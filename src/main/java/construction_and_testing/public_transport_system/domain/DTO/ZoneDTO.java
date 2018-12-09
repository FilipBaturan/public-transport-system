package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Zone;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class ZoneDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Set<ZoneTransportLineDTO> lines;

    private boolean active;

    public ZoneDTO() {
        this.active = true;
    }

    public ZoneDTO(Long id, String name, Set<ZoneTransportLineDTO> lines, boolean active) {
        this.id = id;
        this.name = name;
        this.lines = lines;
        this.active = active;
    }

    public ZoneDTO(Zone zone) {
        this.id = zone.getId();
        this.name = zone.getName();
        this.active = zone.isActive();
        this.lines = zone.getLines().stream().map(ZoneTransportLineDTO::new).collect(Collectors.toSet());
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

    public Set<ZoneTransportLineDTO> getLines() {
        return lines;
    }

    public void setLines(Set<ZoneTransportLineDTO> lines) {
        this.lines = lines;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
