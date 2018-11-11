package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.repository.OperatorRepository;
import construction_and_testing.public_transport_system.service.definition.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;
}
