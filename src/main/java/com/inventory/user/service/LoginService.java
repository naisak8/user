package com.inventory.user.service;

import com.inventory.user.dto.LoginDto;
import com.inventory.user.dto.UserInsertDto;
import com.inventory.user.exceptions.ValidationException;

public interface LoginService {

    String authenticateUser(LoginDto loginDto);

    void signUp(UserInsertDto userInsertDto) throws ValidationException;
}
