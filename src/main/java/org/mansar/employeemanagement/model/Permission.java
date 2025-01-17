package org.mansar.employeemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.mansar.employeemanagement.converter.EmployeeAttributesSetConverter;
import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.core.PermissionEnum;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "permissions")
@Getter @Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"role_id", "name"},
                name = "uk_role_permission_name")
})
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PermissionEnum name;
    @Convert(converter = EmployeeAttributesSetConverter.class)
    @Column(name = "attributes")
    private Set<EmployeeAttribute> attributes = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;


    public void addAttributes(Set<EmployeeAttribute> attributes) {
        this.attributes.addAll(attributes);
    }

    public void removeAttribute(EmployeeAttribute attr) {
        this.attributes.remove(attr);
    }
}
