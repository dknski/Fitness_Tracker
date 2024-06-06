package com.capgemini.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTO {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String email;

    public UserTO(Long id, String firstName, String lastName, LocalDate birthdate, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }

    public UserTO(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }
}
