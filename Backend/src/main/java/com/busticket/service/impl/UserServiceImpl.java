package com.busticket.service.impl;

import com.busticket.entity.User;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.respository.IUserRepo;
import com.busticket.service.interfaces.IUserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepo userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUser(@NotBlank @Email String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}
