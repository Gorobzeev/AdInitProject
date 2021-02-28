package com.bcgdv.mobileservice.service.impl;

import com.bcgdv.mobileservice.controller.model.RegistrationRequest;
import com.bcgdv.mobileservice.model.User;
import com.bcgdv.mobileservice.repository.UserRepository;
import com.bcgdv.mobileservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void register(RegistrationRequest registrationRequest) {
        if (userRepository.findByEmail(registrationRequest.email).isPresent()) {
            throw new RuntimeException("User exist");
        }

        User user = User.builder()
                .email(registrationRequest.email)
                .password(registrationRequest.password)
                .mobileNumber(registrationRequest.number)
                .created(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @Override
    public void confirm(String token) {

    }

    @Override
    public User findById(String id) {
        return this.userRepository.findById(UUID.fromString(id)).get();
    }
}
