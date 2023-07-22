package uz.ilmnajot.registration.dto;

import lombok.Data;
import uz.ilmnajot.registration.enums.RoleName;
@Data
public class ManagerForm {


    private String fullName;

    private String username;

    private String password;

    private RoleName roleName;

}
