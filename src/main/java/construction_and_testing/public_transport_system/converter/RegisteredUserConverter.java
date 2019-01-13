package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.RegisteredUserDTO;
import construction_and_testing.public_transport_system.domain.DTO.RegisteringUserDTO;
import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;

import java.util.ArrayList;
import java.util.Collection;

public class RegisteredUserConverter extends AbstractConverter {

    public static RegisteringUserDTO toRegisteringUserDTO(RegisteredUser registeredUser) {
        RegisteringUserDTO dto = new RegisteringUserDTO();
        dto.setName(registeredUser.getName());
        dto.setLastName(registeredUser.getLastName());
        dto.setUsername(registeredUser.getUsername());
        dto.setPassword(registeredUser.getPassword());
        dto.setEmail(registeredUser.getEmail());
        dto.setTelephone(registeredUser.getTelephone());
        return dto;
    }

    public static RegisteredUser fromRegisteringUserDTO(RegisteringUserDTO registeringUserDTO) {
        RegisteredUser entity = new RegisteredUser();
        Collection<AuthorityType> authorityTypeCollection = new ArrayList<>();
        //((ArrayList<AuthorityType>) authorityTypeCollection).add(AuthorityType.REGISTERED_USER);
        entity.setName(registeringUserDTO.getName());
        entity.setLastName(registeringUserDTO.getLastName());
        entity.setUsername(registeringUserDTO.getUsername());
        entity.setPassword(registeringUserDTO.getPassword());
        entity.setEmail(registeringUserDTO.getEmail());
        entity.setTelephone(registeringUserDTO.getTelephone());
        entity.setConfirmation(UsersDocumentsStatus.UNCHECKED);
        entity.setActive(true);
        entity.setBalance(0);
        entity.setAuthorityType(AuthorityType.REGISTERED_USER);
        return entity;
    }

    public static RegisteredUserDTO toRegisteredUserDTO(RegisteredUser registeredUser) {
        RegisteredUserDTO dto = new RegisteredUserDTO();
        dto.setId(registeredUser.getId());
        dto.setName(registeredUser.getName());
        dto.setLastName(registeredUser.getLastName());
        dto.setUsername(registeredUser.getUsername());
        dto.setPassword(registeredUser.getPassword());
        dto.setEmail(registeredUser.getEmail());
        dto.setTelephone(registeredUser.getTelephone());
        dto.setActive(registeredUser.isActive());
        return dto;
    }

    public static RegisteredUser fromRegisteredUserDTO(RegisteredUserDTO registeredUserDTO) {
        RegisteredUser entity = new RegisteredUser();
        Collection<AuthorityType> authorityTypeCollection = new ArrayList<>();
        //((ArrayList<AuthorityType>) authorityTypeCollection).add(AuthorityType.REGISTERED_USER);
        entity.setId(registeredUserDTO.getId());
        entity.setName(registeredUserDTO.getName());
        entity.setLastName(registeredUserDTO.getLastName());
        entity.setUsername(registeredUserDTO.getUsername());
        entity.setPassword(registeredUserDTO.getPassword());
        entity.setEmail(registeredUserDTO.getEmail());
        entity.setTelephone(registeredUserDTO.getTelephone());
        entity.setConfirmation(UsersDocumentsStatus.UNCHECKED);
        entity.setActive(registeredUserDTO.isActive());
        entity.setBalance(0);
        entity.setAuthorityType(AuthorityType.REGISTERED_USER);
        return entity;
    }

}
