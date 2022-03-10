package com.polsl.prir_proj.services;

import com.polsl.prir_proj.config.LoginCredentials;
import com.polsl.prir_proj.models.User;
import com.polsl.prir_proj.repositories.UserRepository;
import com.polsl.prir_proj.web.errors.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    public String getUserId(String username) {
        return userRepository.findByUsername(username).getId();
    }

    public User register(LoginCredentials dto) throws UserAlreadyExistException {

        if (usernameExist(dto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username: " + dto.getUsername());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword("{bcrypt}" + passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
