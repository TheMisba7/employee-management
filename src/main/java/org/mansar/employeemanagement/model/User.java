package org.mansar.employeemanagement.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @Setter
public class User extends Employee {
    private String username;
    private String password;
    @ManyToOne
    private Role role;
}
