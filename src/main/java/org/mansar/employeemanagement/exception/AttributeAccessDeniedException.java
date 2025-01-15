package org.mansar.employeemanagement.exception;

import org.mansar.employeemanagement.core.EmployeeAttribute;

public class AttributeAccessDeniedException extends RuntimeException {
    private final EmployeeAttribute target;

    public AttributeAccessDeniedException(EmployeeAttribute target) {
        super("Access denied to attribute " + target);
        this.target = target;
    }
}
