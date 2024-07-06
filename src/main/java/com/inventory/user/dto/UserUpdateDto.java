package com.inventory.user.dto;

import com.inventory.user.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
}
