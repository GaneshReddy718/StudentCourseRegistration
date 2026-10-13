package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void serviceMethods() {
    }

    @Around("serviceMethods()")
    public Object logExecutionTime(
            ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName =
                joinPoint.getSignature().toShortString();

        long startTime = System.currentTimeMillis();

        System.out.println("[AOP] Starting: " + methodName);

        try {
            Object result = joinPoint.proceed();

            long duration =
                    System.currentTimeMillis() - startTime;

            System.out.println(
                "[AOP] Completed: " + methodName
                + " | Execution time: " + duration + " ms"
            );

            return result;

        } catch (Throwable ex) {
            throw ex;
        }
    }

    @AfterThrowing(
        pointcut = "serviceMethods()",
        throwing = "ex"
    )
    public void logException(Throwable ex) {

        System.out.println(
            "[AOP] Exception: " + ex.getMessage()
        );
    }
	
}
