package uz.ilmnajot.registration.dto.auth;

import lombok.Data;
import uz.ilmnajot.registration.entity.User;
import uz.ilmnajot.registration.enums.RoleName;

@Data
public class ManagerDto {

    private Long id;

    private String fullName;

    private String username;

    private RoleName roleName;

    public static ManagerDto toDto(User user){
        ManagerDto dto  = new ManagerDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setUsername(user.getUsername());
        dto.setRoleName(user.getRoleName());
        return dto;
    }
}
