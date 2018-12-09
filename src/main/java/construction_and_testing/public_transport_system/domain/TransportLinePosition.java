package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.TransportLinePositionDTO;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Add later
 */
@Entity
@Where(clause = "active =1")
public class TransportLinePosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private TransportLine transportLine;

    @Column(nullable = false, name = "active")
    private boolean active;

    public TransportLinePosition() {
    }

    public TransportLinePosition(Long id, String content, TransportLine transportLine, boolean active) {
        this.id = id;
        this.content = content;
        this.transportLine = transportLine;
        this.active = active;
    }

    public TransportLinePosition(TransportLinePositionDTO position, TransportLine transportLine) {
        this.id = position.getId();
        this.content = position.getContent();
        this.active = position.isActive();
        this.transportLine = transportLine;
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

    public TransportLine getTransportLine() {
        return transportLine;
    }

    public void setTransportLine(TransportLine transportLine) {
        this.transportLine = transportLine;
    }
}
