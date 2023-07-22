package uz.ilmnajot.registration.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ilmnajot.registration.dto.*;
import uz.ilmnajot.registration.entity.User;
import uz.ilmnajot.registration.enums.RoleName;
import uz.ilmnajot.registration.exception.AppException;
import uz.ilmnajot.registration.repository.UserRepository;
import uz.ilmnajot.registration.security.JwtProvider;
import uz.ilmnajot.registration.service.JwtService;

import java.util.Optional;

@Service
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public JwtServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDto register(UserForm form) {
        Optional<User> optionalUser = userRepository.findUserByUsername(form.getUsername());
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setFullName(form.getFullName());
            user.setUsername(form.getUsername());
            user.setRoleName(RoleName.ROLE_USER);
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            User savedUser = userRepository.save(user);
            UserDto userDto = UserDto.toDto(savedUser);
            return userDto;
        }
        throw new AppException("there is already exist username " + form.getUsername());
    }

    @Override
    public LoginDto login(LoginForm form) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                form.getUsername(),
                form.getPassword()
        ));
        String token = jwtProvider.generateToken(form.getUsername());
        LoginDto dto = new LoginDto();
        dto.setToken(token);
        return dto;
    }
    @Override
    public AdminDto createAdmin(AdminForm form) {
        Optional<User> optionalUser = userRepository.findUserByUsername(form.getUsername());
        if(optionalUser.isEmpty()){
            User user = new User();
            user.setFullName(form.getFullName());
            user.setUsername(form.getUsername());
            user.setRoleName(form.getRoleName());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            User saved = userRepository.save(user);
            AdminDto dto = AdminDto.toDto(saved);
            return dto;
        }
throw new AppException("there is no user with the given username + " + form.getUsername());
    }

    @Override
    public ManagerDto createManager(ManagerForm form) {
        Optional<User> optionalUser = userRepository.findUserByUsername(form.getUsername());
        if (optionalUser.isEmpty()){
            User user1  = new User();
            user1.setFullName(form.getFullName());
            user1.setUsername(form.getUsername());
            user1.setRoleName(form.getRoleName());
            user1.setPassword(passwordEncoder.encode(form.getPassword()));
            User saved = userRepository.save(user1);
            return ManagerDto.toDto(saved);
        }
        throw new AppException("there is no user with the given username + " + form.getUsername());
    }
}
