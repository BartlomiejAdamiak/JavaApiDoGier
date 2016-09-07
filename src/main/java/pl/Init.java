package pl;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import pl.View.View;
import pl.configuration.MyConfig;

/**
 * Created by kaima_000 on 2016-06-09.
 */
@Service
public class Init extends Application {

    final static Logger logger = Logger.getLogger(Init.class);

    public static void main(String[] args) throws Exception {
        logger.info("\nSTART!\n");

        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Init init = context.getBean(Init.class);
        logger.info("------------########------------");
        for(String beanBean:context.getBeanDefinitionNames()){
            logger.info(beanBean);
        }

        logger.info("\nTHE END\n");

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        View.viewInstance.prepareView(primaryStage);
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));*/
    }
}