package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.repository.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorServiceImpl implements ValidatorService {

    @Autowired
    private ValidatorRepository validatorRepository;

}
