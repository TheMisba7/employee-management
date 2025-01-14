package org.mansar.employeemanagement.core;

public enum EmployeeAttributes {
    FIRSTNAME("firstname"),
    LASTNAME("lastname"),
    JOB_TITLE("jobtitle"),
    DEPARTMENT("department"),
    CONTACT("contact"),
    ADDRESS("address");

    private final String value;
    EmployeeAttributes(String name) {
        this.value = name;
    }

    public String getValue() {
        return value;
    }
}
