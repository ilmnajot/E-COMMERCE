package uz.ilmnajot.registration.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.ilmnajot.registration.entity.CustomUserDetails;
import uz.ilmnajot.registration.entity.User;
import uz.ilmnajot.registration.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        return optionalUser.map(CustomUserDetails::new).orElse(null);
    }











}
