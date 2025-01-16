package org.mansar.employeemanagement.dto;

public record LoginResponse(String token, long expireAt) {
}
