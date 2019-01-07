package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.repository.StationRepository;
import construction_and_testing.public_transport_system.service.definition.StationService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        try {
            return stationRepository.findById(id).orElseThrow(() ->
                    new GeneralException("Requested station does not exist!", HttpStatus.BAD_REQUEST));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new GeneralException("Invalid id!", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public Station save(Station station) {
        try {
            this.validate(station);
            return stationRepository.save(station);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException("Invalid vehicle data!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void remove(Long id) {
        Optional<Station> entity = stationRepository.findById(id);
        if (entity.isPresent()) {
            Station station = entity.get();
            station.setActive(false);
            stationRepository.save(station);
        } else {
            throw new GeneralException("Requested station does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Station> replaceAll(Iterable<Station> stations) {
        this.validate(stations);
        stationRepository.deleteAll();
        return stationRepository.saveAll(stations);
    }

    /**
     * @param station that needs to be validated
     */
    private void validate(Station station) {
        Set<ConstraintViolation<Station>> violations = Validation.buildDefaultValidatorFactory()
                .getValidator().validate(station);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("Name ");
            for (ConstraintViolation<Station> violation : violations) {
                builder.append(violation.getMessage());
                builder.append("\n");
            }
            throw new GeneralException(builder.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param stations that need to be validated
     */
    private void validate(Iterable<Station> stations) {
        stations.forEach(this::validate);
    }

}
