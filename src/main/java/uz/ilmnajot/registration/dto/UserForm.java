package uz.ilmnajot.registration.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserForm {

    @NotNull(message = "space cannot be null")
    private String fullName;

    @NotNull(message = "space cannot be null")
    private String username;

    @NotNull(message = "space cannot be null")
    private String password;

}
