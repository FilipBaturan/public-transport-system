package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.repository.VehicleRepository;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Override
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle findById(Long id) {
        try {
            return vehicleRepository.findById(id).orElseThrow(() ->
                    new GeneralException("Requested vehicle does not exist!", HttpStatus.BAD_REQUEST));
        } catch (InvalidDataAccessApiUsageException e) { // null id
            throw new GeneralException("Invalid id!", HttpStatus.BAD_REQUEST);

        }
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        this.validate(vehicle);
        if (vehicle.getCurrentLine() != null) {
            vehicle.setCurrentLine(transportLineRepository.findById(vehicle.getCurrentLine().getId())
                    .orElseThrow(() -> new GeneralException("Invalid transport line data associated!",
                            HttpStatus.BAD_REQUEST)));
            if (vehicle.getType() != vehicle.getCurrentLine().getType()) {
                throw new GeneralException("Invalid transport line and vehicle types!", HttpStatus.BAD_REQUEST);
            }
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void remove(Long id) {
        Optional<Vehicle> entity = vehicleRepository.findById(id);
        if (entity.isPresent()) {
            Vehicle vehicle = entity.get();
            vehicle.setActive(false);
            vehicleRepository.save(vehicle);
        } else {
            throw new GeneralException("Requested vehicle does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Vehicle> getAllVehiclesWithLines() {
        return vehicleRepository.findByNotNullTransportLine();
    }

    /**
     * Validates vehicle properties
     *
     * @param vehicle that needs to be validated
     */
    private void validate(Vehicle vehicle) {
        Set<ConstraintViolation<Vehicle>> violations = Validation.buildDefaultValidatorFactory()
                .getValidator().validate(vehicle);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("Name ");
            for (ConstraintViolation<Vehicle> violation : violations) {
                builder.append(violation.getMessage());
                builder.append("\n");
            }
            throw new GeneralException(builder.toString(), HttpStatus.BAD_REQUEST);
        }
    }

}
