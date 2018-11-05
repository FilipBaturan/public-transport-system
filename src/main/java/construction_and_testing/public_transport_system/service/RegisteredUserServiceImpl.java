package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Override
    public List<RegisteredUser> getAll() {
        return registeredUserRepository.findAll();
    }

    @Override
    public RegisteredUser getById(Long id) {
        if(registeredUserRepository.findById(id).isPresent()){
            RegisteredUser user = registeredUserRepository.findById(id).get();
            return user;
        }
        return null;
    }

    @Override
    public boolean addNew(RegisteredUser user) {
        try {
            registeredUserRepository.saveAndFlush(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean modify(RegisteredUser user) {
        if(registeredUserRepository.findById(user.getId()).isPresent()){
            registeredUserRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }



}
