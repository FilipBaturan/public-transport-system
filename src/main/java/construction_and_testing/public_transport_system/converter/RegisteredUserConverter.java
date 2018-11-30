package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.RegisteringUserDTO;
import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;

public class RegisteredUserConverter extends AbstractConverter {
    public static RegisteringUserDTO toRegisteringUserDTO(RegisteredUser registeredUser){
        RegisteringUserDTO dto = new RegisteringUserDTO();
        dto.setName(registeredUser.getName());
        dto.setLastName(registeredUser.getLastName());
        dto.setUsername(registeredUser.getUsername());
        dto.setPassword(registeredUser.getPassword());
        dto.setEmail(registeredUser.getEmail());
        dto.setTelephone(registeredUser.getTelephone());
        return dto;
    }

    public static RegisteredUser fromRegisteringUserDTO(RegisteringUserDTO registeringUserDTO){
        RegisteredUser entity = new RegisteredUser();
        entity.setName(registeringUserDTO.getName());
        entity.setLastName(registeringUserDTO.getLastName());
        entity.setUsername(registeringUserDTO.getUsername());
        entity.setPassword(registeringUserDTO.getPassword());
        entity.setEmail(registeringUserDTO.getEmail());
        entity.setTelephone(registeringUserDTO.getTelephone());
        entity.setConfirmation(UsersDocumentsStatus.UNCHECKED);
        entity.setActive(false);
        entity.setBalance(0);
        return entity;
    }
}
