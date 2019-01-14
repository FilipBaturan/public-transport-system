package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.Schedule;

import java.util.List;

public interface ScheduleService {

    /**
     * @return all available schedules in database
     */
    List<Schedule> getAll();

    /**
     * @param id of requested schedule
     * @return schedule with requested id
     */
    Schedule findById(Long id);

    /**
     * @param schedule that needs to be saved
     * @return saved schedule in database
     */
    Schedule save(Schedule schedule);

    /**
     * @param id of schedule that needs to be removed
     */
    void remove(Long id);

    /**
     * @param id        id of a transport line
     * @param dayOfWeek that the schedule is requested for
     * @return schedule for the requested transport line and day of week
     */
    Schedule findByTransportLineIdAndDayOfWeek(Long id, Integer dayOfWeek);

    /**
     *
     * @param id id of a transport line
     * @ list of schedules for the requested transport line
     */
    List<Schedule> findByTransportLineId(Long id);
}
