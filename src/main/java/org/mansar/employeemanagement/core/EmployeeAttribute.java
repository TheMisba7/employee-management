package org.mansar.employeemanagement.core;

public enum EmployeeAttribute {
    ALL("ALL"),
    FIRSTNAME("firstname"),
    LASTNAME("lastname"),
    JOB_TITLE("job_title"),
    DEPARTMENT("department"),
    CONTACT("contact"),
    ADDRESS("address"),
    CREATED("created"),
    UPDATED("updated"),
    STATUS("status");

    private final String value;
    EmployeeAttribute(String name) {
        this.value = name;
    }

    public String getValue() {
        return value;
    }
}
