package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.repository.AdminRepository;
import construction_and_testing.public_transport_system.service.definition.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

}
