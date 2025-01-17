package org.mansar.employeemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.core.PermissionEnum;
import org.mansar.employeemanagement.core.Trail;
import org.mansar.employeemanagement.dao.UserDao;
import org.mansar.employeemanagement.dto.UserDTO;
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

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final IDepartmentService departmentService;


    private User mapAndSave(UserRQ userRQ, User user) {
        userMapper.fromDTO(userRQ, user);
        if (user.getRole() == null
                || (!Objects.equals(userRQ.getRoleId(), user.getRole().getId()))) {
            user.setRole(roleService.getRoleById(userRQ.getRoleId()));
        }
        if (user.getDepartment() == null
                || !Objects.equals(userRQ.getDepartmentId(), user.getDepartment().getId())) {
            user.setDepartment(departmentService.getDepartment(userRQ.getDepartmentId()));
        }
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(userRQ.getPassword()));
            user.setUsername(userRQ.getUsername());
        }
        return userDao.save(user);
    }
    @Override
    @Trail(action = PermissionEnum.CREATE)
    public UserDTO save(UserRQ newUser) {
        userDao.findByUsername(newUser.getUsername())
                .ifPresent(u -> {throw new BusinessException("Username already exist");});
        User user = new User();
        return userMapper.toDTO(mapAndSave(newUser, user));
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
    @Trail(action = PermissionEnum.UPDATE)
    public UserDTO update(Long userId, UserRQ user) {
        User subjectUser = getById(userId);
        return userMapper.toDTO(mapAndSave(user, subjectUser));
    }

    @Override
    @Trail(action = PermissionEnum.DELETE)
    public void delete(Long userId) {
        userDao.deleteById(userId);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userMapper.toDTO(userDao.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }
}
