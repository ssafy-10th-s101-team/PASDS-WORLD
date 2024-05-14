package world.pasds.kms.common.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(*..*Controller)")
    public void controller() {
    }

    @Around("controller()")
    public Object logResponseInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        logger.info("Request URL: " + request.getRequestURL().toString());
        logger.info("HTTP Method: " + request.getMethod());
        logger.info("Controller: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Method: " + joinPoint.getSignature().getName());

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();  // 컨트롤러 메소드 실행
        long endTime = System.currentTimeMillis();

        logger.info("Response from: " + joinPoint.getSignature().getName());
        logger.info("Response Time: " + (endTime - startTime) + "ms");

        return result;
    }
}

