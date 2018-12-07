package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.repository.VehicleRepository;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() ->
                new GeneralException("Requested vehicle does not exist!", HttpStatus.BAD_REQUEST));
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
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
            throw new GeneralException("Vehicle with id:" + id + " does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

}
