package uz.ilmnajot.registration.dto.auth;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import uz.ilmnajot.registration.enums.RoleName;

@Data
public class AdminForm {

    private String fullName;

    private String username;

    private String password;

    private RoleName roleName;

}
