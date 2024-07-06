package com.inventory.user.controller;

import com.inventory.user.dto.LoginDto;
import com.inventory.user.dto.UserInsertDto;
import com.inventory.user.exceptions.ValidationException;
import com.inventory.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginDto loginDto) {
        return loginService.authenticateUser(loginDto);
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody UserInsertDto userInsertDto) throws ValidationException {
        loginService.signUp(userInsertDto);
    }
}
