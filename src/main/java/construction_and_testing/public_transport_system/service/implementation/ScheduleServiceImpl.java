package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.repository.ScheduleRepository;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.definition.ScheduleService;
import construction_and_testing.public_transport_system.util.GeneralException;
import construction_and_testing.public_transport_system.util.TimeStringComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Override
    public List<Schedule> getAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        Iterator<Schedule> it = schedules.iterator();
        while (it.hasNext()){
            Schedule schedule = it.next();
            schedule.getDepartures().sort(new TimeStringComparator());
            if (!schedule.isActive()) it.remove();
        }
        return schedules;
    }

    @Override
    public Schedule findById(Long id) {
        try {
            return scheduleRepository.findById(id).orElseThrow(() ->
                    new GeneralException("Requested schedule does not exist!", HttpStatus.BAD_REQUEST));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new GeneralException("Invalid id!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public void remove(Long id) {
        Optional<Schedule> entity = scheduleRepository.findById(id);
        if (entity.isPresent()) {
            Schedule schedule = entity.get();
            schedule.setActive(false);
            scheduleRepository.save(schedule);
        } else {
            throw new GeneralException("Schedule with id:" + id + " does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Schedule findByTransportLineIdAndDayOfWeek(Long id, Integer dayOfWeek) {
        try {
            Schedule schedule = this.scheduleRepository.findAllScheduleByTransportLineIdAndDayOfWeek(id, dayOfWeek);
            schedule.getDepartures().sort(new TimeStringComparator());
            return schedule;
        } catch (Exception e) {
            throw new GeneralException("Requested schedule doesn't exist!", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Schedule> findByTransportLineId(Long id) {
        try {
            List<Schedule> schedules = this.scheduleRepository.findAllSchedulesByTransportLineId(id);
            for (Schedule schedule : schedules) {
                schedule.getDepartures().sort(new TimeStringComparator());
            }
            return schedules;
        } catch (Exception e) {
            throw new GeneralException("Transport line doesn't exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Schedule findScheduleIfExists(Schedule schedule) {
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule s: schedules) {
            if (s.getTransportLine().getName().equals(schedule.getTransportLine().getName()) && s.getDayOfWeek().name().equals(schedule.getDayOfWeek().name())){
                return s;
            }
        }
        return null;
    }
}
