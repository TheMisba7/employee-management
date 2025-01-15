package org.mansar.employeemanagement.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.mansar.employeemanagement.core.RoleEnum;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "role")
@Getter @Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Permission> permissions = new HashSet<>();

    public void addPermission(Permission permission) {
        permissions.add(permission);
        permission.setRole(this);
    }
    public void removePermission(Permission permission) {
        permissions.remove(permission);
        permission.setRole(null);
    }

    public void clearPermissions() {
        permissions.forEach(permission -> permission.setRole(null));
        permissions.clear();
    }
}
