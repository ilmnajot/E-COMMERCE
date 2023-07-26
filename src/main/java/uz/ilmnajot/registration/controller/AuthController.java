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
    public ResponseEntity<ManagerDto> registerManager(@RequestBody ManagerForm form){
        ManagerDto managerDto = jwtService.createManager(form);
        return ResponseEntity.ok(managerDto);
    }
    @PutMapping("/verifyEmail")
    public HttpEntity<ApiResponse> verifyEmail(@RequestParam String username, @RequestParam String emailCode){
        ApiResponse apiResponse = jwtService.verifyEmail(username, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}


