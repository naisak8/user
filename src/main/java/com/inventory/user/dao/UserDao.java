package com.inventory.user.dao;

import com.inventory.user.dto.UserInsertDto;
import com.inventory.user.dto.UserUpdateDto;
import com.inventory.user.exceptions.ValidationException;
import com.inventory.user.model.User;
import com.inventory.user.qo.UserQo;
import org.springframework.data.domain.Page;

public interface UserDao {

    User findById(Long id);

    Page<User> findAllUsersByExample(UserQo userQo);

    User saveUser(UserInsertDto userInsertDto) throws ValidationException;

    User updateUser(UserUpdateDto userUpdateDto) throws ValidationException;

    boolean deleteUser(Long id);
}
