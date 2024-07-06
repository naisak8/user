package com.inventory.user.service.impl;

import com.inventory.user.dao.UserDao;
import com.inventory.user.dto.LoginDto;
import com.inventory.user.dto.UserInsertDto;
import com.inventory.user.exceptions.ValidationException;
import com.inventory.user.service.JwtService;
import com.inventory.user.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserDao userDao;

    @Override
    public String authenticateUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(userDetailsService.loadUserByUsername(loginDto.getUsername()));
        else
            throw new UsernameNotFoundException("Invalid credentials");
    }

    @Override
    public void signUp(UserInsertDto userInsertDto) throws ValidationException {
        if (StringUtils.isBlank(userInsertDto.getUsername()))
            throw new ValidationException("Username cannot be blank");
        if (StringUtils.isBlank(userInsertDto.getEmail()))
            throw new ValidationException("Email cannot be blank");
        if (StringUtils.isBlank(userInsertDto.getPassword()))
            throw new ValidationException("Password cannot be blank");
        userDao.saveUser(userInsertDto);
    }
}
