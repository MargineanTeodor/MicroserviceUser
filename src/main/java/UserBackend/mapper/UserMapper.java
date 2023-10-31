package UserBackend.mapper;

import UserBackend.DTO.UserDTO;
import UserBackend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDTO mapModelToDto(User user)
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setRole(user.getRole());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
    public static User mapDtoToModel(UserDTO userDTO)
    {
        User user = new User();
        user.setId(userDTO.getId());
        user.setRole(userDTO.getRole());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        return user;
    }
}
