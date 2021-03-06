package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.DTO.ImageUploadDTO;
import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.repository.RegisteredUserRepository;
import construction_and_testing.public_transport_system.service.definition.RegisteredUserService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        if (registeredUserRepository.findById(id).isPresent()) {
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
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean modify(RegisteredUser user) {
        if (registeredUserRepository.findById(user.getId()).isPresent()) {
            RegisteredUser toChange = registeredUserRepository.findById(user.getId()).get();
            toChange.setFirstName(user.getFirstName());
            toChange.setLastName(user.getLastName());
            toChange.setEmail(user.getEmail());
            toChange.setUsername(user.getUsername());
            toChange.setPassword(user.getPassword());
            toChange.setTelephone(user.getTelephone());
            registeredUserRepository.saveAndFlush(toChange);
            return true;
        }
        return false;
    }

    @Override
    public void remove(Long id) {
        Optional<RegisteredUser> entity = registeredUserRepository.findById(id);
        if (entity.isPresent()) {
            RegisteredUser user = entity.get();
            user.setActive(false);
            registeredUserRepository.save(user);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public void updateValidationDocument(ImageUploadDTO imageUploadDTO) {
        registeredUserRepository.updateUsersValidationDocument(imageUploadDTO.getImage(), imageUploadDTO.getId());
    }

    @Override
    public ImageUploadDTO getValidationDocument(Long id) {
        String image = registeredUserRepository.getUsersDocument(id);
        if(image != null){
            ImageUploadDTO imageUploadDTO = new ImageUploadDTO(id, image);
            return imageUploadDTO;
        }
        else{
            return null;
        }

    }

}
