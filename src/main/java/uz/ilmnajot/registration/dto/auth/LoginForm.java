package uz.ilmnajot.registration.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginForm {

    @NotNull(message = "space cannot be null")
    private String username;

    @NotNull(message = "space cannot be null")
    private String password;
}
