package com.inventory.user.controller;

import com.inventory.user.dao.UserDao;
import com.inventory.user.dto.UserInsertDto;
import com.inventory.user.dto.UserUpdateDto;
import com.inventory.user.exceptions.ValidationException;
import com.inventory.user.model.User;
import com.inventory.user.qo.UserQo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping
    public Page<User> findAllUsersByExample(UserQo userQo) {
        return userDao.findAllUsersByExample(userQo);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userDao.findById(id);
    }

    @PostMapping
    public User createUser(@RequestBody UserInsertDto userInsertDto) throws ValidationException {
        return userDao.saveUser(userInsertDto);
    }

    @PutMapping
    public User updateUser(@RequestBody UserUpdateDto userUpdateDto) throws ValidationException {
        return userDao.updateUser(userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return userDao.deleteUser(id);
    }
}
