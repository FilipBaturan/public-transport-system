package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Schedule;

import java.io.Serializable;

public class ScheduleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String startTime;

    private Long transportLine;

    private boolean active;

    public ScheduleDTO() {
        this.active = true;
    }

    public ScheduleDTO(Long id, String startTime, Long transportLine, boolean active) {
        this.id = id;
        this.startTime = startTime;
        this.transportLine = transportLine;
        this.active = active;
    }

    public ScheduleDTO(Schedule schedule){
        this.id = schedule.getId();
        this.startTime = schedule.getStartTime();
        this.transportLine = schedule.getId();
        this.active = schedule.isActive();
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

    public Long getTransportLine() {
        return transportLine;
    }

    public void setTransportLine(Long transportLine) {
        this.transportLine = transportLine;
    }
}
