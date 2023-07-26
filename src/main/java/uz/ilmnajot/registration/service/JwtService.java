package uz.ilmnajot.registration.service;

import uz.ilmnajot.registration.apiResponse.ApiResponse;
import uz.ilmnajot.registration.dto.auth.*;

public interface JwtService{

    ApiResponse register(UserForm form);

    LoginDto login(LoginForm form);

    AdminDto createAdmin(AdminForm form);

    ManagerDto createManager(ManagerForm form);

    ApiResponse verifyEmail(String username, String emailCode);

    ApiResponse setPassword(SetPasswordDto setPasswordDto);

    LoginDto validateToken(String token);
}
