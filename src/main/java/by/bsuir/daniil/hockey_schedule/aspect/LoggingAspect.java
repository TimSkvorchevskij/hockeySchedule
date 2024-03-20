package by.bsuir.daniil.hockey_schedule.aspect;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Pointcut("execution(* by.bsuir.daniil.hockey_schedule.service.*.*(..))")
    private void allServiceMethods() { }

    @Pointcut("@annotation(AspectAnnotation)")
    public void callAtMyServiceAnnotation() { }

    @Before(value = "callAtMyServiceAnnotation()")
    public void logBefore(final JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.toString() + " "
                + joinPoint.getSignature().getName();
        logger.info(">> {}() - {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "callAtMyServiceAnnotation()", returning = "result")
    public void logAfter(final JoinPoint joinPoint, final Object result) {
        String methodName = joinPoint.toString();
        logger.info("<< {}() - {}", methodName, result);
    }

    @AfterThrowing(pointcut = "callAtMyServiceAnnotation()", throwing = "exception")
    public void logException(final JoinPoint joinPoint, final Throwable exception) {
        String methodName = joinPoint.toString();
        logger.error("<< {}() - {}", methodName, exception.getMessage());
    }
    @PostConstruct
    public void initAspect() {
        logger.info("Aspect is initialized");
    }

    @PreDestroy
    public void destroyAspect() {
        logger.info("Aspect is destroyed");
    }
}
