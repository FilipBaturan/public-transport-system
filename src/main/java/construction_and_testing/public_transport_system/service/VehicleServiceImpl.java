package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        return vehicleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Vehicle add(Vehicle newVehicle) {
        try {
            return vehicleRepository.save(newVehicle);
        } catch (DataIntegrityViolationException e){
            return null;
        }

    }

    @Override
    public Vehicle update(Vehicle updatedVehicle) {
        return vehicleRepository.save(updatedVehicle);
    }

    @Override
    public void remove(Long id) {
        Optional<Vehicle> entity = vehicleRepository.findById(id);
        if(entity.isPresent()){
            Vehicle vehicle = entity.get();
            vehicle.setActive(false);
            vehicleRepository.save(vehicle);
        }else {
            throw new EntityNotFoundException();
        }
    }

}
