package org.mansar.employeemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.mansar.employeemanagement.core.EmployeeStatus;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "lastname", nullable = false)
    private String lastname;
    @Column(name = "job_title", nullable = false)
    private String jobTitle;
    @Column(name = "status", nullable = false)
    private EmployeeStatus status;
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}