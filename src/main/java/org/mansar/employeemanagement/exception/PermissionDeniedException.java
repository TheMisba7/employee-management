package org.mansar.employeemanagement.exception;

import org.mansar.employeemanagement.core.PermissionEnum;

public class PermissionDeniedException extends RuntimeException {
    private final PermissionEnum permission;

    public PermissionDeniedException(PermissionEnum permission) {
        super("permission denied for " + permission);
        this.permission = permission;
    }
}
