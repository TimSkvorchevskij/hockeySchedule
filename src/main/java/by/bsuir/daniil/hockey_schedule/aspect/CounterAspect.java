package by.bsuir.daniil.hockey_schedule.aspect;

import by.bsuir.daniil.hockey_schedule.counter.RequestCounter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CounterAspect {
    RequestCounter requestCounter = new RequestCounter();
    private static final Logger logger = LoggerFactory.getLogger(CounterAspect.class);

    @Pointcut("@annotation(RequestCounterAnnotation)")
    public void callAtMyServiceAnnotation() { }
    @Before(value = "callAtMyServiceAnnotation()")
    public void logBefore(final JoinPoint joinPoint) {
        requestCounter.increment();
        String methodName = joinPoint + " "
                + joinPoint.getSignature().getName();
        logger.info("Request Counter: {} - {}\n", requestCounter.getCount(), methodName);
    }
}
