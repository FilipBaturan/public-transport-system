package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.TransportLinePosition;

public class TransportLinePositionDTO {

    private Long id;

    private String content;

    private boolean active;

    public TransportLinePositionDTO() {
        this.active = true;
    }

    public TransportLinePositionDTO(Long id, String content, boolean active) {
        this.id = id;
        this.content = content;
        this.active = active;
    }

    public TransportLinePositionDTO(TransportLinePosition transportLinePosition) {
        this.id = transportLinePosition.getId();
        this.content = transportLinePosition.getContent();
        this.active = transportLinePosition.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
