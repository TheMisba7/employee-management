package org.mansar.employeemanagement.dao;

import org.mansar.employeemanagement.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDao extends JpaRepository<Permission, Long> {
}
