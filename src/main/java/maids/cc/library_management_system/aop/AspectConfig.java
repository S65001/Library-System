package maids.cc.library_management_system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectConfig {
    private static final Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @Around(value = "execution(* maids.cc.library_management_system.service.*(..))")
    public void logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable{
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();

        logger.info("Method {} called with arguments: {}", methodName, joinPoint.getArgs());

        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Method {} completed in {} ms", methodName, timeTaken);
        logger.info("results: {}", result);

    }
    @AfterThrowing(pointcut = "execution(* maids.cc.library_management_system.service.*(..))", throwing = "exception")
    public void ExceptionLogging(JoinPoint joinPoint,Throwable exception){
        String methodName = joinPoint.getSignature().getName();
        logger.error("<< {}({}) - {}", methodName,joinPoint.getArgs(), exception.getMessage());
    }
}
