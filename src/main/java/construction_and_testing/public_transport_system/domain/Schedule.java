package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.ScheduleDTO;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private TransportLine transportLine;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;

    @ElementCollection(targetClass=String.class)
    @CollectionTable(name = "schedule_departures" )
    @Column(name = "departure", nullable = false)
    private List<String> departures;

    @Column(nullable = false, name = "active")
    private boolean active;

    public Schedule() {
        this.active = true;
    }

    public Schedule(Long id, TransportLine transportLine){
        this.id = id;
        this.transportLine = transportLine;
        this.active = true;
    }

    public Schedule(Long id, TransportLine transportLine, DayOfWeek dayOfWeek, ArrayList<String> departures, boolean active) {
        this.id = id;
        this.transportLine = transportLine;
        this.dayOfWeek = dayOfWeek;
        this.departures = departures;
        this.active = active;
    }

    public Schedule(ScheduleDTO schedule){
        this.id = schedule.getId();
        this.transportLine = new TransportLine(schedule.getTransportLine().getId());
        this.departures = schedule.getDepartures();
        this.active = schedule.isActive();
        this.dayOfWeek = schedule.getDayOfWeek();
    }

    public Schedule(ScheduleDTO schedule, TransportLine transportLine){
        this.id = schedule.getId();
        this.transportLine = transportLine;
        this.departures = schedule.getDepartures();
        this.active = schedule.isActive();
        this.dayOfWeek = schedule.getDayOfWeek();
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

    public List<String> getDepartures() {
        return departures;
    }

    public void setDepartures(List<String> departures) {
        this.departures = departures;
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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
