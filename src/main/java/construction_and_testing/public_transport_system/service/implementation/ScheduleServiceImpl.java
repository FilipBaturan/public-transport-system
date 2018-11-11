package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.repository.ScheduelRepository;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.definition.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduelRepository scheduelRepository;

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Override
    public List<Schedule> getAll() {
        return scheduelRepository.findAll();
    }

    @Override
    public Schedule findById(Long id) {
        return scheduelRepository.findById(id).orElseThrow(() ->
                new GeneralException("Requested schedule does not exist!", HttpStatus.BAD_REQUEST));
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduelRepository.save(schedule);
    }

    @Override
    public void remove(Long id) {
        Optional<Schedule> entity = scheduelRepository.findById(id);
        if(entity.isPresent()){
            Schedule schedule = entity.get();
            schedule.setActive(false);
            scheduelRepository.save(schedule);
        }else{
            throw new GeneralException("Requested schedule does not exist!", HttpStatus.BAD_REQUEST);
        }
    }
}
