package uz.ilmnajot.registration.dto.auth;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.registration.enums.RoleName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminForm {

    private String fullName;

    private String username;

    private String password;

    private RoleName roleName;


}
