package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TransportLineServiceImpl implements TransportLineService {

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Override
    public List<TransportLine> getAll() {
        return transportLineRepository.findAll();
    }

    @Override
    public TransportLine findById(Long id) {
        return transportLineRepository.findById(id).orElseThrow(() ->
                new GeneralException("Requested transport line does not exist!", HttpStatus.BAD_REQUEST));
    }

    @Override
    public TransportLine add(TransportLine newTransportLine) {
        try {
            return transportLineRepository.save(newTransportLine);
        } catch (DataIntegrityViolationException e){
            return null;
        }

    }

    @Override
    public TransportLine update(TransportLine updatedTransportLine) {
        return transportLineRepository.save(updatedTransportLine);
    }

    @Override
    public void remove(Long id) {
        Optional<TransportLine> entity = transportLineRepository.findById(id);
        if(entity.isPresent()){
            TransportLine transportLine = entity.get();
            transportLine.setActive(false);
            transportLineRepository.save(transportLine);
        }else {
            throw new EntityNotFoundException();
        }
    }

}
