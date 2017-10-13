package ys.prototype.fmtaq.application;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ApplicationLogger {

    private final Logger logger = LoggerFactory.getLogger("Aspect logging");

    @Around("execution(public ys.prototype.fmtaq.application.dto.TaskIdDTO "
            + "startAsyncTask(ys.prototype.fmtaq.application.dto.TaskDTO))")
    public Object logStartAsyncTask(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("start processing taskDTO: {}", joinPoint.getArgs());
        Object returnObject = joinPoint.proceed();
        logger.info("processing taskDTO - ok");

        return returnObject;
    }
}
