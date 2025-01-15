package org.mansar.employeemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.dao.UserDao;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.mansar.employeemanagement.model.User;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserDao userDao;
    @Override
    public void save(User user) {
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
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long userId) {
        userDao.deleteById(userId);
    }
}
