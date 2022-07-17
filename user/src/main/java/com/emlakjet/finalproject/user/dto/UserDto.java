package com.emlakjet.finalproject.user.dto;

import com.emlakjet.finalproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private User user;

    private boolean admin;
}
