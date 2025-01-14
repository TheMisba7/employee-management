package org.mansar.employeemanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.Set;
@Entity
public class PermissionAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "permission_id") // Optional: Specifies the foreign key column name
    private Permission permission;

    @ManyToMany
    @JoinTable(
            name = "permission_attribute_mapping", // Table name for the relationship
            joinColumns = @JoinColumn(name = "permission_attributes_id"), // Foreign key in this entity's table
            inverseJoinColumns = @JoinColumn(name = "attribute_id") // Foreign key in the "attribute" table
    )
    private Set<Attribute> attributes;
}
