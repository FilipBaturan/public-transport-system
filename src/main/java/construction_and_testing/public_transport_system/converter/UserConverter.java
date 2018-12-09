package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.UserDTO;
import construction_and_testing.public_transport_system.domain.User;

public class UserConverter extends AbstractConverter {

    public static UserDTO fromEntity(User u){
        return new UserDTO(u);
    }

    public static User toEntity(UserDTO dto){
        return new User(dto);
    }

}
