package org.othuret.api.product.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.othuret.api.product.domain.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * This class inspect classes with {@link org.springframework.web.bind.annotation.RestController}
 * annotation only and join all public method within this annotation to log each coming request
 * or response.
 * <p>
 * <p>You are able to disabled or enabled this feature with environment variable TOGGLE_FEATURE_ASPECT_ENABLED.
 * <p>
 * <p>Supported values are:
 * <ul>
 * <li>true to enabled this feature</li>
 * <li>false (default) to disabled this feature</li>
 * </ul>
 * <p>
 *
 * @author Olivier THURET
 * @since 0.1
 */
@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "api.toggle-feature", name = "aspect", havingValue = "true")
public class RESTAspectLogger {

    private ObjectMapper objectMapper;

    @Autowired
    public RESTAspectLogger(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void rest() {
    }

    @Pointcut("execution(public * *(..))")
    protected void allMethod() {
    }

    @Before("rest() && allMethod()")
    public void logBefore(JoinPoint joinPoint) throws JsonProcessingException {
        log.debug("Entering in Method :  " + joinPoint.getSignature().getName());
        log.debug("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
        Stream.of(joinPoint.getArgs()).filter(arg -> arg instanceof ProductResource || arg instanceof String)
                .forEach(arg -> log.debug("Argument :  " + this.parseParameterMethod(arg)));
    }

    @AfterReturning(pointcut = "rest() && allMethod()", returning = "dto")
    public void logAfter(JoinPoint joinPoint, Object dto) throws Exception {
        log.debug("Method Return value : " + this.objectMapper.writeValueAsString(dto));
    }

    @AfterThrowing(pointcut = "rest() && allMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("An exception has been thrown in " + joinPoint.getSignature().getName());
        log.error("Exception type: " + exception);
        if (exception.getMessage() != null)
            log.error("Exception msg: " + exception.getMessage());
    }

    @Around("rest() && allMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        final String className = joinPoint.getSignature().getDeclaringTypeName();
        final String methodName = joinPoint.getSignature().getName();
        final Object result = joinPoint.proceed();
        final long elapsedTime = System.currentTimeMillis() - start;
        log.debug("Method " + className + "." + methodName + " ()" + " execution time : " + elapsedTime + " ms");
        return result;

    }

    private String parseParameterMethod(final Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
