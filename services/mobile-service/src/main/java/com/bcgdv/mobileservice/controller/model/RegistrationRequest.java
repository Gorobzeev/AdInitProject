package com.bcgdv.mobileservice.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest {

    /**
     * Password must be 6-100 characters, and must include at least one upper case letter,
     * one lower case letter, and one numeric digit.
     */
    @NotBlank
    @Pattern(regexp = "^\\S*(?=\\S{6,100})(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*[\\d])\\S*$", message = "weak password")
    public final String password;

    @NotBlank
    public final String email;

    @NotBlank
    public final String number;


    @NotBlank
    public final String userName;

    @JsonCreator
    public RegistrationRequest(
            @JsonProperty("password") final String password,
            @JsonProperty("email") final String email,
            @JsonProperty("number") final String number,
            @JsonProperty("userName") final String userName
    ) {
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.number = number;
    }

}
