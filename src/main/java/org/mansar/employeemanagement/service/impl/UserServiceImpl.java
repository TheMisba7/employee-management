package org.mansar.employeemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.dao.UserDao;
import org.mansar.employeemanagement.dto.request.UserRQ;
import org.mansar.employeemanagement.exception.BusinessException;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.mansar.employeemanagement.mapper.UserMapper;
import org.mansar.employeemanagement.model.User;
import org.mansar.employeemanagement.service.IDepartmentService;
import org.mansar.employeemanagement.service.IRoleService;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final IDepartmentService departmentService;


    @Override
    public void save(UserRQ newUser) {
        userDao.findByUsername(newUser.getUsername())
                .ifPresent(u -> {throw new BusinessException("Username already exist");});
        User user = new User();
        userMapper.fromDTO(newUser, user);
        user.setRole(roleService.getRoleById(newUser.getRoleId()));
        user.setDepartment(departmentService.getDepartment(newUser.getDepartmentId()));
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userDao.save(user);
    }

    @Override
    public User getUser(String username) {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException(username, "user"));
    }

    @Override
    public User getById(Long userId) {
        return userDao.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException(userId, "user"));
    }

    @Override
    public User update(Long userId, UserRQ user) {
        return null;
    }

    @Override
    public void delete(Long userId) {
        userDao.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }
}
