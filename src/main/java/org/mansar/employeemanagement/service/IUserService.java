package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.model.User;

public interface IUserService {
    void save(User user);
    User getUser(String username);
    User getById(Long userId);
    User update(User user);
    void delete(Long userId);
    static User getCurrentUser() {
        return  new User();
    }
}
