package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.dto.UserDTO;
import org.mansar.employeemanagement.dto.request.UserRQ;
import org.mansar.employeemanagement.model.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    @Secured("ADMIN")
    UserDTO save(UserRQ user);
    User getUser(String username);
    User getById(Long userId);
    @Secured("ADMIN")
    UserDTO update(Long userId, UserRQ user);
    @Secured("ADMIN")
    void delete(Long userId);
    @Secured("ADMIN")
    List<UserDTO> getUsers();
    static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
