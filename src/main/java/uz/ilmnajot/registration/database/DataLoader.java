package uz.ilmnajot.registration.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.registration.entity.User;
import uz.ilmnajot.registration.enums.RoleName;
import uz.ilmnajot.registration.repository.UserRepository;

import java.util.UUID;

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
            userRepository.save(User.builder()
                    .fullName("Omar Xalil")
                    .username("admin@gmail.com")
                    .password(passwordEncoder.encode("pass-admin"))
                    .roleName(RoleName.ROLE_ADMIN)
                    .enabled(true)
                    .build()
            );
//            userRepository.save(User.builder()
//                    .fullName("manager")
//                    .username("manager@gmail.com")
//                    .password(passwordEncoder.encode("pass-manager"))
//                    .roleName(RoleName.ROLE_MANAGER)
//                    .enabled(true)
//                    .build()
//            );
        }
    }
}
