package uz.ilmnajot.registration.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.ilmnajot.registration.dto.*;
import uz.ilmnajot.registration.repository.UserRepository;

public interface JwtService{

    UserDto register(UserForm form);

    LoginDto login(LoginForm form);

    AdminDto createAdmin(AdminForm form);

    ManagerDto createManager(ManagerForm form);
}
