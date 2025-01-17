package org.mansar.employeemanagement.core;

public enum EmployeeAttribute {
    ALL("ALL"),
    ID("id"),
    FIRSTNAME("firstname"),
    LASTNAME("lastname"),
    JOB_TITLE("jobTitle"),
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
