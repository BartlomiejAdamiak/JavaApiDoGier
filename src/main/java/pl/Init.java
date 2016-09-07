package pl;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.ptql.ProcessFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import pl.View.View;
import pl.configuration.MyConfig;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.Iterator;
import java.util.List;

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
public class Init extends Application {

    final static Logger logger = Logger.getLogger(Init.class);

    public static void main(String[] args) throws Exception {
        Sigar sigar = new Sigar();

        for (int i = 0; i < 100; i++) {

            ProcessFinder find = new ProcessFinder(sigar);
            //get the list of current java processes, and optionally query the list to choose which process to monitor
            long[] pidList = sigar.getProcList();
            //assuming we know the process id, we may query the process finder
            long pid = find.findSingleProcess("Pid.Pid.eq=54730");

            //get memory info for the process id
            ProcMem memory = new ProcMem();
            memory.gather(sigar, pid);

            //get cou info for the oricess id
            ProcCpu cpu = new ProcCpu();
            cpu.gather(sigar, pid);

            //print the memory used by the process id
            System.out.println("Current memory used: " + Long.toString(memory.getSize()));
            //print all memory info
            System.out.println(memory.toMap());
            //print all cpu info
            System.out.println(cpu.toMap());

            Thread.sleep(1000);
        }
        logger.info("\nSTART!\n");

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
}