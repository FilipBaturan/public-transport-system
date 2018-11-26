package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.StationPosition;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.repository.StationPositionRepository;
import construction_and_testing.public_transport_system.service.definition.StationPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StationPositionServiceImpl implements StationPositionService {

    @Autowired
    private StationPositionRepository stationPositionRepository;

    @Override
    public List<StationPosition> getAll() {
        return stationPositionRepository.findAll();
    }

    @Override
    public StationPosition findById(Long id) {
        try {
            return stationPositionRepository.findById(id).orElseThrow(() ->
                    new GeneralException("Requested station position does not exist!", HttpStatus.BAD_REQUEST));
        } catch (IllegalArgumentException e){ // invoked when id is null
            return null;
        }

    }

    @Override
    public StationPosition save(StationPosition stationPosition) {
        return stationPositionRepository.save(stationPosition);
    }

    @Override
    public void remove(Long id) {
        Optional<StationPosition> entity = stationPositionRepository.findById(id);
        if(entity.isPresent()){
            StationPosition stationPosition = entity.get();
            stationPosition.setActive(false);
            stationPositionRepository.save(stationPosition);
        }else {
            throw new GeneralException("Station position with id:" + id + " does not exist!", HttpStatus.BAD_REQUEST);
        }
    }
}
