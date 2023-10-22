package uz.ilmnajot.registration.service.impl;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ilmnajot.registration.apiResponse.ApiResponse;
import uz.ilmnajot.registration.database.DataLoader;
import uz.ilmnajot.registration.dto.auth.*;
import uz.ilmnajot.registration.entity.User;
import uz.ilmnajot.registration.enums.RoleName;
import uz.ilmnajot.registration.exception.AppException;
import uz.ilmnajot.registration.repository.UserRepository;
import uz.ilmnajot.registration.security.JwtProvider;
import uz.ilmnajot.registration.service.JwtService;

import java.util.Optional;
import java.util.Random;

@Service
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final JavaMailSender javaMailSender;

    private final MailService mailService;

    private final DataLoader dataLoader;
//
//    @Value("${server.set-password}")
//    String setPasswordUrl;
    @Value("${hash.secret}")
    String hashSecret;

    @Value("${email.base.url}")
    String url;


    public JwtServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider,
            JavaMailSender javaMailSender,
            MailService mailService,
            DataLoader dataLoader) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.javaMailSender = javaMailSender;
        this.mailService = mailService;
        this.dataLoader = dataLoader;
    }

//    public void sendMail(String username, String emailCode) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("no-reply@gmail.com");
//            message.setTo(username);
//            message.setSubject("verify account");
//            message.setText(emailCode);
//            javaMailSender.send(message);
//        } catch (Exception ignored) {
//        }
//    }
//    public void sendLinkToMail(String username, String token) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("no-reply@gmail.com");
//            message.setTo(username);
//            message.setSubject("verify account");
//            message.setText("<a href='http://localhost:5555/api/auth/validate' " + token + username +">!</a>");
//            javaMailSender.send(message);
//        } catch (Exception ignored) {
//        }
//    }

    @Override
    public ApiResponse register(UserForm form) {
        Optional<User> optionalUser = userRepository.findUserByUsername(form.getUsername());
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setFullName(form.getFullName());
            user.setUsername(form.getUsername());
            user.setRoleName(RoleName.ROLE_USER);
            user.setPassword(passwordEncoder.encode(form.getPassword()));

            int nextedInt = new Random().nextInt(9999999);
            user.setEmailCode(String.valueOf(nextedInt).substring(0, 4));
            userRepository.save(user);
            mailService.sendMail(user.getUsername(), user.getEmailCode());
            return new ApiResponse("send code to your email", true);
        }
        throw new AppException("there is already exist username " + form.getUsername());
    }

    @Override
    public LoginDto login(@NotNull LoginForm form) {
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
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setFullName(form.getFullName());
            user.setUsername(form.getUsername());
            user.setRoleName(form.getRoleName());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            User saved = userRepository.save(user);
            return AdminDto.toDto(saved);
        }
        throw new AppException("there is no user with the given username + " + form.getUsername());
    }


    @Override
    public UserDto createManager(ManagerForm form) {
        Optional<User> optionalUser = userRepository.findUserByUsername(form.getUsername());
        if (optionalUser.isEmpty()) {
        String hashCode = passwordEncoder.encode(form.getUsername()  + hashSecret);
        String link  = url + hashCode;
        mailService.sendLinkToMail(form.getUsername(), link);
        return addUserDB(form, RoleName.ROLE_MANAGER, hashCode);
        }
        throw new AppException("there is already exists user with the given username + " + form.getUsername());
    }

    @Override
    public ApiResponse verifyEmail(String username, String emailCode) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (emailCode.equals(user.getEmailCode())) {
                user.setEnabled(true);
                userRepository.save(user);
                return new ApiResponse("verified successfully", true);
            }
            return new ApiResponse("unverified or code is not matched", false);
        }
        return new ApiResponse("there is no exists", false);
    }

    public LoginDto validateToken(String token) {
        Optional<User> optionalUser = userRepository.findUserByToken(token);
        if (optionalUser.isPresent()) {
            LoginDto loginDto = new LoginDto();
            String jwtToken = jwtProvider.generateToken(optionalUser.get().getUsername());
            loginDto.setToken(jwtToken);
            return loginDto;
        }
        // TODO: 7/25/2023 Kelgan tokenni database search qiladi agar mavjud bo'lsa return jwt
        throw new AppException("you are not allowed to set username and password, please contact with your administration to register you");
    }

    public ApiResponse setPassword(SetPasswordDto setPasswordDto) {
        String email = SecurityDetails.getUserEmail().get();
        Optional<User> optionalUser = userRepository.findUserByUsername(email);
        if (optionalUser.isEmpty()) {
            throw new AppException("User " + email + " does not exist");
        }
        User user = optionalUser.get();
        user.setPassword(setPasswordDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("successfully created password", true);
    }


    public UserDto addUserDB(ManagerForm form, RoleName roleName, String confirmHash) {
        return UserDto.toDto(User.builder()
                .fullName(form.getFullName())
                .username(form.getUsername())
                .roleName(roleName)
                .enabled(false)
                .hashedCode(confirmHash)
                .build());
    }

}
