package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        return stationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Station add(Station newStation) {
        try {
            return stationRepository.save(newStation);
        } catch (DataIntegrityViolationException e){
            return null;
        }

    }

    @Override
    public Station update(Station updatedStation) {
        return stationRepository.save(updatedStation);
    }

    @Override
    public void remove(Long id) {
        Optional<Station> entity = stationRepository.findById(id);
        if(entity.isPresent()){
            Station station = entity.get();
            station.setActive(false);
            stationRepository.save(station);
        }else {
            throw new EntityNotFoundException();
        }
    }

}
