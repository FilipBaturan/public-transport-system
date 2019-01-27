package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;

import java.io.Serializable;
import java.util.List;

public class ScheduleDTO implements Serializable {

    private static final long serialVersionUID = 111L;

    private Long id;

    private List<String> departures;

    private TransportLine transportLine;

    private DayOfWeek dayOfWeek;

    private boolean active;

    public ScheduleDTO() {
        this.active = true;
    }

    public ScheduleDTO(Long id, List<String> departures, TransportLine transportLine, DayOfWeek dayOfWeek, boolean active) {
        this.id = id;
        this.departures = departures;
        this.transportLine = transportLine;
        this.dayOfWeek = dayOfWeek;
        this.active = active;
    }

    public ScheduleDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.departures = schedule.getDepartures();
        this.transportLine = schedule.getTransportLine();
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

    public TransportLine getTransportLine() {
        return transportLine;
    }

    public void setTransportLine(TransportLine transportLine) {
        this.transportLine = transportLine;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        try {
            return "ScheduleDTO{" +
                    "id=" + id +
                    ", departures=" + departures.toString() +
                    ", transportLine=" + transportLine +
                    ", dayOfWeek=" + dayOfWeek +
                    ", active=" + active +
                    '}';

        } catch (Exception e) {
            return "ScheduleDTO{" +
                    ", departures=" + departures +
                    ", transportLine=" + transportLine +
                    ", dayOfWeek=" + dayOfWeek +
                    ", active=" + active +
                    '}';
        }

    }
}
