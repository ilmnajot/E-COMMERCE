package uz.ilmnajot.registration.dto.auth;

import lombok.Data;
import uz.ilmnajot.registration.entity.User;

@Data
public class UserDto {

    private Long id;

    private String fullName;

    private String username;

    public static UserDto toDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
