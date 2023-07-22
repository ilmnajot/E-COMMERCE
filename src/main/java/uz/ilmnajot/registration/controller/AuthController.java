package uz.ilmnajot.registration.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.registration.dto.*;
import uz.ilmnajot.registration.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public HttpEntity<UserDto> registerUser(@Valid @RequestBody UserForm form) {
        UserDto register = jwtService.register(form);
        return ResponseEntity.ok(register);
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
}


