package uz.ilmnajot.registration.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetPasswordDto {

    private String username; //email address
    @NotNull(message ="password cannot be null'")
    private String password;

    @NotNull(message ="password cannot be null'")
    private String checkPassword;
}
