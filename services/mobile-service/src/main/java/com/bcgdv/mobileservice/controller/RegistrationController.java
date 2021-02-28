package com.bcgdv.mobileservice.controller;


import com.bcgdv.mobileservice.controller.model.RegistrationRequest;
import com.bcgdv.mobileservice.model.User;
import com.bcgdv.mobileservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/signup", produces = APPLICATION_JSON_VALUE)
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public void register(@Valid @RequestBody final RegistrationRequest registrationRequest) {
        this.userService.register(registrationRequest);
    }

    @PostMapping("/verify/{token}")
    public void confirm(@PathVariable("token") final String token) {
        this.userService.confirm(token);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return userService.findById(id);
    }
}
