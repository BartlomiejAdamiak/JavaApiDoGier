package pl.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Adam on 2016-09-07.
 */

@Component
@Aspect
public class AspectLogger {
    Logger logger = Logger.getLogger(AspectLogger.class);

    @Pointcut("execution(* pl.View.View.prepareLayout(..))")
    public void prepareLayout() {    }


    @Before("prepareLayout()")
    public void logBeforePrepareLayout(JoinPoint joinPoint) {
        logger.error("Called method " + joinPoint.getSignature().getName() + "LALALALA");
    }

    @After("prepareLayout()")
    public void logAfterPrepareLayout(JoinPoint joinPoint) {
        logger.error("Finished method " + joinPoint.getSignature().getName() + ".");
    }
}
