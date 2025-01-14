package org.mansar.employeemanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mobilePhone;
    private String workPhone;
    private String homePhone;
    private String personalEmail;
    private String workEmail;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String linkedInProfile;
    private String twitterHandle;
    private String faxNumber;
    private String preferredContactMethod;
}
