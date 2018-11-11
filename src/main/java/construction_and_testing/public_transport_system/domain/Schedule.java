package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.ScheduleDTO;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Add later
 */
@Entity
@Where(clause = "active =1")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TransportLine transportLine;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false, name = "active")
    private boolean active;

    public Schedule() {
        this.active = true;
    }

    public Schedule(Long id, TransportLine transportLine, String startTime, boolean active) {
        this.id = id;
        this.transportLine = transportLine;
        this.startTime = startTime;
        this.active = active;
    }

    public Schedule(ScheduleDTO schedule){
        this.id = schedule.getId();
        this.transportLine = new TransportLine(schedule.getTransportLine());
        this.startTime = schedule.getStartTime();
        this.active = isActive();
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

    public TransportLine getTransportLine() {
        return transportLine;
    }

    public void setTransportLine(TransportLine transportLine) {
        this.transportLine = transportLine;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
