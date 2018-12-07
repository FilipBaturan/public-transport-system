package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import construction_and_testing.public_transport_system.repository.TransportLinePositionRepository;
import construction_and_testing.public_transport_system.service.definition.TransportLinePositionService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportLinePositionServiceImpl implements TransportLinePositionService {

    @Autowired
    private TransportLinePositionRepository transportLinePositionRepository;

    @Override
    public List<TransportLinePosition> getAll() {
        return transportLinePositionRepository.findAll();
    }

    @Override
    public TransportLinePosition findById(Long id) {
        return transportLinePositionRepository.findById(id).orElseThrow(() ->
                new GeneralException("Requested transport line position does not exist!", HttpStatus.BAD_REQUEST));
    }

    @Override
    public TransportLinePosition save(TransportLinePosition transportLinePosition) {
        return transportLinePositionRepository.save(transportLinePosition);
    }

    @Override
    public void remove(Long id) {
        Optional<TransportLinePosition> entity = transportLinePositionRepository.findById(id);
        if (entity.isPresent()) {
            TransportLinePosition transportLinePosition = entity.get();
            transportLinePosition.setActive(false);
            transportLinePositionRepository.save(transportLinePosition);
        } else {
            throw new GeneralException("Transport line position with id:" + id + " does not exist!", HttpStatus.BAD_REQUEST);
        }
    }
}
