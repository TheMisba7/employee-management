package org.mansar.employeemanagement.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mansar.employeemanagement.core.PermissionEnum;
import org.mansar.employeemanagement.core.Trail;
import org.mansar.employeemanagement.model.User;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditTrailAspect {
    private final ObjectMapper objectMapper;

    @Builder
    @ToString
    public static class AuditMessage {
        private PermissionEnum action;
        private String actor;
        private String subject;
        private LocalDateTime timestamp;
    }

    @Around("@annotation(trail)")
    public Object logChanges(ProceedingJoinPoint joinPoint, Trail trail) throws Throwable {
            Object result = joinPoint.proceed();
            AuditMessage auditMessage = AuditMessage.builder()
                    .action(trail.action())
                    .actor(buildActorInfo(IUserService.getCurrentUser()))
                    .subject(buildSubjectInfo(result, joinPoint, trail))
                    .timestamp(LocalDateTime.now())
                    .build();
            log.info(auditMessage.toString());
            return result;
    }

    private String buildActorInfo(User user) {
        return String.format("Actor: [id: %d, Full Name: %s]", 
            user.getId(), 
            user.getFullName());
    }

    private String buildSubjectInfo(Object result, ProceedingJoinPoint joinPoint, Trail trail) throws JsonProcessingException {
        if (trail.action() == PermissionEnum.DELETE) {
            //extract deleted entity
            Object[] args = joinPoint.getArgs();
            if (args.length == 1 && args[0] instanceof Long value) {
                return "Employee Id: " + value;
            }
            return "Unknown";
        }
        return objectMapper.writeValueAsString(result);
    }
} 