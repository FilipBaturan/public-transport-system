package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.repository.ZoneRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        return zoneRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Zone add(Zone newZone) {
        try {
            return zoneRepository.save(newZone);
        } catch (DataIntegrityViolationException e){
             return null;
        }

    }

    @Override
    public Zone update(Zone updatedZone) {
        return zoneRepository.save(updatedZone);
    }

    @Override
    public void remove(Long id) {
        Optional<Zone> entity = zoneRepository.findById(id);
        if(entity.isPresent()){
            Zone zone = entity.get();
            zone.setActive(false);
            zoneRepository.save(zone);
        }else {
            throw new EntityNotFoundException();
        }
    }
}
