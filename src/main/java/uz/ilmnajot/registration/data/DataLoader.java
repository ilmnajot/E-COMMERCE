package uz.ilmnajot.registration.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.registration.entity.User;
import uz.ilmnajot.registration.enums.RoleName;
import uz.ilmnajot.registration.exception.AppException;
import uz.ilmnajot.registration.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String mode;
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }
    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            userRepository.save(new User(
                    "Elbekjon",
                    " samarkandps@gmail.com",
                    passwordEncoder.encode("pass-admin"),
                    RoleName.ROLE_ADMIN
            ));
            userRepository.save(new User(
                    "Nurbekjon",
                    "nurbek@gmail.com",
                    passwordEncoder.encode("nurbekjon"),
                    RoleName.ROLE_MANAGER
            ));
        }
    }
}
