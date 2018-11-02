package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportLineServiceImpl implements TransportLineService {

    @Autowired
    private TransportLineRepository transportLineRepository;

}
