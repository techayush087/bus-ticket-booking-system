package com.busticket.service.interfaces;

import com.busticket.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface IUserService {
    User getUser(@NotBlank @Email String email);
}
