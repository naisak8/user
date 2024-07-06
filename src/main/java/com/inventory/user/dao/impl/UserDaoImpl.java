package com.inventory.user.dao.impl;

import com.inventory.user.dao.UserDao;
import com.inventory.user.dto.UserInsertDto;
import com.inventory.user.dto.UserUpdateDto;
import com.inventory.user.exceptions.enums.OrderBy;
import com.inventory.user.exceptions.ValidationException;
import com.inventory.user.model.User;
import com.inventory.user.qo.UserQo;
import com.inventory.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public Page<User> findAllUsersByExample(UserQo userQo) {
        //validations
        // default value
        User user = new User();
        BeanUtils.copyProperties(userQo, user);
        Example<User> userExample = Example.of(user);
        PageRequest pageRequest = PageRequest.of(userQo.getPageNumber(), userQo.getPageSize());
        if (!CollectionUtils.isEmpty(userQo.getSortBy())) {
            AtomicReference<Sort> sort = new AtomicReference<>();
            userQo.getSortBy().forEach((key, value) -> {
                if (value.equals(OrderBy.ASC))
                    sort.set(sort.get().and(Sort.by(key).ascending()));
                else
                    sort.set(sort.get().and(Sort.by(key).descending()));
            });
            pageRequest = PageRequest.of(userQo.getPageNumber(), userQo.getPageSize(), sort.get());
        }
        // business logic
        return userRepository.findAll(userExample, pageRequest);
    }

    @Override
    public User saveUser(UserInsertDto userInsertDto) throws ValidationException {
        //validations
        User existingUsername = userRepository.findByUsername(userInsertDto.getUsername());
        if (Objects.nonNull(existingUsername))
            throw new ValidationException("username already exist!");
        User existingEmail = userRepository.findByEmail(userInsertDto.getEmail());
        if (Objects.nonNull(existingEmail))
            throw new ValidationException("email already exist!");
        // default value
        User user = new User();
        BeanUtils.copyProperties(userInsertDto, user);
        user.setPassword(bCryptPasswordEncoder.encode(userInsertDto.getPassword()));
        // business logic
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserUpdateDto userUpdateDto) throws ValidationException {
        //validations
        User existing = findById(userUpdateDto.getId());
        User existingUsername = userRepository.findByUsername(userUpdateDto.getUsername());
        if (Objects.nonNull(existingUsername))
            throw new ValidationException("username already exist!");
        User existingEmail = userRepository.findByEmail(userUpdateDto.getEmail());
        if (Objects.nonNull(existingEmail))
            throw new ValidationException("email already exist!");
        // default value
        User user = new User();
        BeanUtils.copyProperties(userUpdateDto, user);
        if (Objects.nonNull(existing))
            userRepository.save(user);
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        User user = findById(id);
        if (Objects.nonNull(user))
            userRepository.deleteById(id);
        return true;
    }
}
