package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.repository.ScheduleRepository;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransportLineServiceImpl implements TransportLineService {

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<TransportLine> getAll() {
        return transportLineRepository.findAll();
    }

    @Override
    public TransportLine findById(Long id) {
        return transportLineRepository.findById(id).orElseThrow(() ->
                new GeneralException("Requested transport line does not exist!", HttpStatus.BAD_REQUEST));
    }

    @Override
    public TransportLine save(TransportLine transportLine) {
        try {
            return transportLineRepository.save(transportLine);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException("Transport line with given name already exist!", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            throw new GeneralException("Transport lines contains bad formatted id data", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void remove(Long id) {
        Optional<TransportLine> entity = transportLineRepository.findById(id);
        if (entity.isPresent()) {
            TransportLine transportLine = entity.get();
            transportLine.setActive(false);
            transportLineRepository.save(transportLine);
        } else {
            throw new GeneralException("Transport line with id:" + id + " does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<TransportLine> replaceAll(Iterable<TransportLine> transportLines) {

        //dobavi sve sheculde
        //obrisi one koje se ne nalaze u transprotLines
        //kreiraj sve schedules za sve nove rute i uvezi ih sa njima
        //obrisi sve rute u bazi, a zbog kaskade obrisace se i tranportLinePosition
        //sacuvaj rute

        List<Schedule> associatedSchedules = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findAll();
        if (!schedules.isEmpty()) { // remove all unassociated schedules
            transportLines.forEach(transportLine -> {
                transportLine.getSchedule().forEach(schedule -> {
                    // corrupted schedule associated to transport line
                    if (schedule.getId() != null && (!schedules.contains(schedule))) {
                        throw new GeneralException("Corrupted schedule data received.", HttpStatus.BAD_REQUEST);
                    } else if (schedule.getId() != null) { // valid schedule associated to transport line
                        Schedule temp = null;
                        for (Schedule s : schedules) {
                            if (s.getId().equals(schedule.getId())) {
                                temp = s;
                                break;
                            }
                        }
                        if (temp != null) {
                            // replace item in transportLine
                            for (Schedule tempSchedule : transportLine.getSchedule()) {
                                if (tempSchedule.getId().equals(temp.getId())) {
                                    tempSchedule.setId(temp.getId());
                                    tempSchedule.setTransportLine(temp.getTransportLine());
                                    tempSchedule.setDepartures(temp.getDepartures());
                                    tempSchedule.setDayOfWeek(temp.getDayOfWeek());
                                    tempSchedule.setActive(temp.isActive());
                                    break;
                                }
                            }
                        }
                        associatedSchedules.add(schedule);
                    }
                });
            });
            // remove elements that are in persistentSchedules collection
            schedules.removeIf(schedule -> associatedSchedules.stream()
                    .anyMatch(schedule1 -> schedule1.getId().equals(schedule.getId())));
            scheduleRepository.deleteAll(schedules);
        }
        try {
            transportLineRepository.deleteAll();
            return transportLineRepository.saveAll(transportLines);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException("Transport line with given name already exist!", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) { // PersistentObjectException handled
            throw new GeneralException("Transport lines contains bad formatted id data", HttpStatus.BAD_REQUEST);
        }
        // POSTO SE KORISTI KASKADA ALL OCEKUJE SE DA SVI ID-JEVI KOJE VIDI TRANSPORTLINE DA BUDU NULL
        // (U SUPROTNOM BACA PERZISTENCEOBJECTEXCEPTION) TJ. ID-JEVI OD TRANSPORTLINEPOSITION.
        // TREBA NAPISATI TESTOVE KOJI PREOVERAVAJU ID-JEVE, PRVO MORAJU BITI NULL

        // testirati slucaj kada neodgovarajuci schedule je attachovan za transport line
        // mora da postoji barem zona za koju se vezuje ruta ako, namestiti na frontu da se veze za neku
        // defaltnu zonu za id=1 ili ovde staviti default rutu?
    }

}
