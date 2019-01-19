package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.repository.ZoneRepository;
import construction_and_testing.public_transport_system.service.definition.ZoneService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Interface that provides service for zones
 */
@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Override
    public List<Zone> getAll() {
        return zoneRepository.findAll();
    }

    @Override
    public Zone findById(Long id) {
        try {
            return zoneRepository.findById(id).orElseThrow(() -> new GeneralException("Requested zone does not exist!",
                    HttpStatus.BAD_REQUEST));
        } catch (InvalidDataAccessApiUsageException e) { // null id
            throw new GeneralException("Invalid id!", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public Zone save(Zone zone) {
        try {
            this.validate(zone);
            if (zone.getLines() == null) {
                zone.setLines(new HashSet<>());
                return zoneRepository.save(zone);
            } else {
                Zone previousZone = null;
                if (zone.getId() != null) {
                    Optional<Zone> optionalPreviousZone = zoneRepository.findById(zone.getId());
                    if (optionalPreviousZone.isPresent()) {
                        previousZone = optionalPreviousZone.get();
                    }
                }
                Set<TransportLine> temp = new HashSet<>(transportLineRepository.findAllById(zone.getLines().stream()
                        .map((TransportLine::getId)).collect(Collectors.toList())));
                if (temp.size() != zone.getLines().size()) {
                    throw new GeneralException("Invalid transport line data associated!", HttpStatus.BAD_REQUEST);
                }

                if (previousZone != null) {
                    previousZone.getLines().removeIf(temp::contains);
                    if (!previousZone.getLines().isEmpty()) {
                        Zone defaultZone = zoneRepository.findById(1L).orElseThrow(() ->
                                new GeneralException("Default zone does not exist!", HttpStatus.INTERNAL_SERVER_ERROR));
                        defaultZone.getLines().addAll(previousZone.getLines());
                        defaultZone.getLines().forEach(transportLine -> transportLine.setZone(defaultZone));
                        zoneRepository.save(defaultZone);
                    }
                    previousZone.setName(zone.getName());
                    previousZone.setLines(temp);
                    return zoneRepository.save(previousZone);
                } else {
                    zone.setLines(temp);
                    return zoneRepository.save(zone);
                }
            }
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException("Zone with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void remove(Long id) {
        if (id == 1) {
            throw new GeneralException("Default zone can not be removed!", HttpStatus.BAD_REQUEST);
        }
        Optional<Zone> entity = zoneRepository.findById(id);
        if (entity.isPresent()) {
            Zone zone = entity.get();
            zone.setActive(false);
            Zone defaultZone = zoneRepository.findById(1L).orElseThrow(
                    () -> new GeneralException("Default zone does not exist!", HttpStatus.INTERNAL_SERVER_ERROR));
            zone.getLines().forEach((transportLine -> transportLine.setZone(defaultZone)));
            zoneRepository.save(zone);
        } else {
            throw new GeneralException("Requested zone does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Validates zone properties
     *
     * @param zone that needs to be validated
     */
    private void validate(Zone zone) {
        Set<ConstraintViolation<Zone>> violations = Validation.buildDefaultValidatorFactory()
                .getValidator().validate(zone);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("Name ");
            for (ConstraintViolation<Zone> violation : violations) {
                builder.append(violation.getMessage());
                builder.append("\n");
            }
            throw new GeneralException(builder.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
