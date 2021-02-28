package com.bcgdv.mobileservice.service;

import com.bcgdv.mobileservice.controller.model.RegistrationRequest;
import com.bcgdv.mobileservice.model.User;

public interface UserService {
    void register(RegistrationRequest registrationRequest);

    void confirm(String token);

    User findById(String id);
}
