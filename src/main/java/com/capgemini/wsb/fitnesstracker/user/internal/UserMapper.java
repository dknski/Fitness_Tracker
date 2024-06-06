package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    public UserTO toUserTO(User user) {
        return new UserTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    public User toEntity(UserDto dto) {
        return new User(dto.firstName(),
                dto.lastName(),
                dto.birthdate(),
                dto.email());
    }

    public void updateUserDto(UserDto dto, User user) {
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setBirthdate(dto.birthdate());
        user.setEmail(dto.email());
    }

}
