package pl.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 JavaApiDoGier - program służący do przedstawiania statystyk gracza
 Copyright (C) 19../20.. Bartłomiej Adamiak, Adam Szczeciński,
 Michał Kudlewski, Beata Cabaj

 Niniejszy program jest wolnym oprogramowaniem; możesz go
 rozprowadzać dalej i/lub modyfikować na warunkach Powszechnej
 Licencji Publicznej GNU, wydanej przez Fundację Wolnego
 Oprogramowania - według wersji 2-giej tej Licencji lub którejś
 z późniejszych wersji.

 Niniejszy program rozpowszechniany jest z nadzieją, iż będzie on
 użyteczny - jednak BEZ JAKIEJKOLWIEK GWARANCJI, nawet domyślnej
 gwarancji PRZYDATNOŚCI HANDLOWEJ albo PRZYDATNOŚCI DO OKREŚLONYCH
 ZASTOSOWAŃ. W celu uzyskania bliższych informacji - Powszechna
 Licencja Publiczna GNU.

 Z pewnością wraz z niniejszym programem otrzymałeś też egzemplarz
 Powszechnej Licencji Publicznej GNU (GNU General Public License);
 jeśli nie - napisz do Free Software Foundation, Inc., 675 Mass Ave,
 Cambridge, MA 02139, USA.
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
