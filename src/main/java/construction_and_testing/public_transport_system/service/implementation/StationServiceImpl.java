package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.repository.StationRepository;
import construction_and_testing.public_transport_system.service.definition.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Override
    public List<Station> getAll() {
        return stationRepository.findAll();
    }

    @Override
    public Station findById(Long id) {
        return stationRepository.findById(id).orElseThrow(() ->
                new GeneralException("Requested station does not exist!", HttpStatus.BAD_REQUEST));
    }

    @Override
    public Station save(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public void remove(Long id) {
        Optional<Station> entity = stationRepository.findById(id);
        if(entity.isPresent()){
            Station station = entity.get();
            station.setActive(false);
            stationRepository.save(station);
        }else {
            throw new GeneralException("Station with id:" + id + " does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

}
