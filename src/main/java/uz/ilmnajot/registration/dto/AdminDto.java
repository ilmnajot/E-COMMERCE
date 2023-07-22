package uz.ilmnajot.registration.dto;

import lombok.Data;
import uz.ilmnajot.registration.entity.User;
import uz.ilmnajot.registration.enums.RoleName;

@Data
public class AdminDto {

    private Long id;

    private String fullName;

    private String username;

    private RoleName roleName;

    public static AdminDto toDto(User user){
        AdminDto dto = new AdminDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setUsername(user.getUsername());
        dto.setRoleName(user.getRoleName());
        return dto;
    }

}
