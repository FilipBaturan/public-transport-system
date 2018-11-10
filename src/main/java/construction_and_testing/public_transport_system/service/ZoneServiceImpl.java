package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Interface that provides service for zones
 */
@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public List<Zone> getAll() {
        return zoneRepository.findAll();
    }

    @Override
    public Zone findById(Long id) {
        return zoneRepository.findById(id).orElseThrow(() -> new GeneralException("Requested zone does not exist!",
                HttpStatus.BAD_REQUEST));
    }

    @Override
    public Zone save(Zone zone) {
        try {
            return zoneRepository.save(zone);
        } catch (DataIntegrityViolationException e){
             throw new GeneralException("Zone with given name already exist!", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void remove(Long id) {
        Optional<Zone> entity = zoneRepository.findById(id);
        if(entity.isPresent()){
            Zone zone = entity.get();
            zone.setActive(false);
            zoneRepository.save(zone);
        }else {
            throw new GeneralException("Requested zone does not exist!", HttpStatus.BAD_REQUEST);
        }
    }
}
