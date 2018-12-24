package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.repository.ScheduleRepository;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.definition.ScheduleService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule findById(Long id) {
        try{
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
        return this.scheduleRepository.findAllSchedulesByTransportLineIdAndDayOfWeek(id, dayOfWeek);
    }
}
