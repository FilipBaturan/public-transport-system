package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.LoggedUserDTO;
import construction_and_testing.public_transport_system.domain.DTO.OperatorDTO;
import construction_and_testing.public_transport_system.domain.DTO.UserDTO;
import construction_and_testing.public_transport_system.domain.DTO.ValidatorDTO;
import construction_and_testing.public_transport_system.domain.Operator;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;

public class UserConverter extends AbstractConverter {

    public static UserDTO fromEntity(User u) {
        return new UserDTO(u);
    }

    public static User toEntity(UserDTO dto) {
        return new User(dto);
    }

    public static ValidatorDTO fromEntity(Validator v) {
        return new ValidatorDTO(v);
    }

    public static User toEntity(ValidatorDTO dto) {
        return new Validator(dto);
    }

    public static OperatorDTO fromEntity(Operator o) {
        return new OperatorDTO(o);
    }

    public static User toEntity(OperatorDTO dto) {
        return new Operator(dto);
    }

    public static LoggedUserDTO fromLoggedEntity(User entity) {
        return new LoggedUserDTO(entity);

    }

}