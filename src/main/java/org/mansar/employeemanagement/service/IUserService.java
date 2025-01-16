package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.dto.request.UserRQ;
import org.mansar.employeemanagement.model.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    @Secured("ADMIN")
    void save(UserRQ user);
    User getUser(String username);
    User getById(Long userId);
    @Secured("ADMIN")
    User update(Long userId, UserRQ user);
    @Secured("ADMIN")
    void delete(Long userId);
    static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
