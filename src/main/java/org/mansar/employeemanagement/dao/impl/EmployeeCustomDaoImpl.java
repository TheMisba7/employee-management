package org.mansar.employeemanagement.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.core.EmployeeStatus;
import org.mansar.employeemanagement.dao.EmployeeCustomDao;
import org.mansar.employeemanagement.model.Address;
import org.mansar.employeemanagement.model.Contact;
import org.mansar.employeemanagement.model.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeCustomDaoImpl implements EmployeeCustomDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Employee> selectAttributes(Set<EmployeeAttribute> attributes) {
        if (attributes == null || attributes.isEmpty())
            return Collections.emptyList();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Employee> root = query.from(Employee.class);

        List<Selection<?>> selections = new ArrayList<>();
        for (EmployeeAttribute attribute : attributes) {
            selections.add(root.get(attribute.getValue()).alias(attribute.getValue()));
        }

        query.multiselect(selections);
        List<Tuple> tuples = entityManager.createQuery(query).getResultList();

        // Map the tuples to Employee objects manually
        return tuples.stream().map(tuple -> {
            Employee employee = new Employee();
            attributes.forEach(attribute -> {
                Object value = tuple.get(attribute.getValue());
                mapResult(value, employee, attribute);
            });
            return employee;
        }).collect(Collectors.toList());
    }

    private void mapResult(Object value, Employee target, EmployeeAttribute attribute) {
        switch (attribute) {
            case STATUS -> target.setStatus((EmployeeStatus) value);
            case ADDRESS -> target.setAddress((Address) value);
            case CREATED -> target.setCreated((LocalDateTime) value);
            case UPDATED -> target.setUpdated((LocalDateTime) value);
            case LASTNAME -> target.setLastname((String) value);
            case FIRSTNAME -> target.setFirstname((String) value);
            case JOB_TITLE -> target.setJobTitle((String) value);
            case CONTACT -> target.setContact((Contact) value);
            case DEPARTMENT -> target.setDepartment(target.getDepartment());
        }
    }

    @Override
    public Employee selectAttributesByEmployeeId(Long employeeId, Set<EmployeeAttribute> attributes) {
        if (attributes == null || attributes.isEmpty() || employeeId == null)
            return null;

        String selectedFields = buildSelectClause(attributes);
        String queryString = String.format("SELECT %s FROM Employee e WHERE e.id = :employeeId", selectedFields);

        TypedQuery<Employee> query = entityManager.createQuery(queryString, Employee.class);
        query.setParameter("employeeId", employeeId);
        return query.getSingleResult();
    }
    private String buildSelectClause(Set<EmployeeAttribute> attributes) {
        return attributes.stream()
                .map(attribute -> "e." + attribute.getValue())
                .collect(Collectors.joining(", "));
    }
}
