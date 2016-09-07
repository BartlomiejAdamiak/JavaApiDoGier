package pl;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import pl.View.View;
import pl.configuration.MyConfig;

/**
 * JavaApiDoGier - program służący do przedstawiania statystyk gracza
 * Copyright (C) 19../20.. Bartłomiej Adamiak, Adam Szczeciński,
 * Michał Kudlewski, Beata Cabaj
 * <p>
 * Niniejszy program jest wolnym oprogramowaniem; możesz go
 * rozprowadzać dalej i/lub modyfikować na warunkach Powszechnej
 * Licencji Publicznej GNU, wydanej przez Fundację Wolnego
 * Oprogramowania - według wersji 2-giej tej Licencji lub którejś
 * z późniejszych wersji.
 * <p>
 * Niniejszy program rozpowszechniany jest z nadzieją, iż będzie on
 * użyteczny - jednak BEZ JAKIEJKOLWIEK GWARANCJI, nawet domyślnej
 * gwarancji PRZYDATNOŚCI HANDLOWEJ albo PRZYDATNOŚCI DO OKREŚLONYCH
 * ZASTOSOWAŃ. W celu uzyskania bliższych informacji - Powszechna
 * Licencja Publiczna GNU.
 * <p>
 * Z pewnością wraz z niniejszym programem otrzymałeś też egzemplarz
 * Powszechnej Licencji Publicznej GNU (GNU General Public License);
 * jeśli nie - napisz do Free Software Foundation, Inc., 675 Mass Ave,
 * Cambridge, MA 02139, USA.
 */
@Service
@Aspect
public class Init extends Application {

    final static Logger logger = Logger.getLogger(Init.class);

    public static void main(String[] args) throws Exception {
        logger.info("\nSTART!\n");

        //TODO: usunąć, jeśli nic nie zadziała.
        printSth();

        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Init init = context.getBean(Init.class);
        logger.info("------------########------------");
        for (String beanBean : context.getBeanDefinitionNames()) {
            logger.info(beanBean);
        }

        logger.info("\nTHE END\n");

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        View.viewInstance.prepareView(primaryStage);
    }

    public static void printSth(){
        System.out.println("Example text for printSth method. Let's check AspectLogger.");
    }
}