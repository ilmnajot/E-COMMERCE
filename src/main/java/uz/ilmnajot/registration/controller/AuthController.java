package uz.ilmnajot.registration.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.registration.apiResponse.ApiResponse;
import uz.ilmnajot.registration.dto.auth.*;
import uz.ilmnajot.registration.exception.AppException;
import uz.ilmnajot.registration.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public HttpEntity<ApiResponse> registerUser(@Valid @RequestBody UserForm form) {
        ApiResponse registered = jwtService.register(form);
        return ResponseEntity.ok(registered);
    }

    @PostMapping("/login")
    public HttpEntity<LoginDto> login(@Valid @RequestBody LoginForm form) {
        LoginDto login = jwtService.login(form);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminForm form) {
        AdminDto admin = jwtService.createAdmin(form);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/registerManager")
    public ResponseEntity<ManagerDto> registerManager(@RequestBody ManagerForm form) {
        ManagerDto managerDto = jwtService.createManager(form);
        return ResponseEntity.ok(managerDto);
    }

    /**
     * bu yerda email bilan kod orqali tasdiqlash metod yozilgan
     * @param username
     * @param emailCode
     * @return apiResponse
     */
    @PutMapping("/verifyEmail")
    public HttpEntity<ApiResponse> verifyEmail(@RequestParam String username, @RequestParam String emailCode) {
        ApiResponse apiResponse = jwtService.verifyEmail(username, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * yokenni teshirish jarayoni yozilgan, bu yerda
     * @param token
     * @return LoginDto qaytadi
     */
    @PatchMapping("/validate-token")
    public ResponseEntity<LoginDto> setPassword(@RequestParam("token") String token) {
        return ResponseEntity.ok(jwtService.validateToken(token));
    }

    /**
     * bu yerda link orqali keladi va uzi uchun kod quyadi
     * @param setPasswordDto
     * @return
     */
    @PatchMapping("/set-password")
    public ResponseEntity<?> setPassword(SetPasswordDto setPasswordDto) {
        if (!setPasswordDto.getPassword().equals(setPasswordDto.getCheckPassword())) {
            throw new AppException("password does not match");
        }
        return ResponseEntity.ok(jwtService.setPassword(setPasswordDto));
    }

    String brotherWords = "it is not right time now to do your coding";
    String myWords = "it may seem not good for now";
    String XWay;



}


