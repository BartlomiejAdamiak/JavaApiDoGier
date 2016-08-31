package pl;

import pl.configuration.MyConfig;
import pl.model.Player;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kaima_000 on 2016-06-09.
 */
@Service
public class Init {

    @Autowired
    HttpWargamingClient wot;

    @Autowired
    HttpRiotClient httpRiotClient;

    final static Logger logger = Logger.getLogger(Init.class);

    public static void main(String[] args) throws Exception {

        logger.info("\nSTART!\n");

        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Init init = context.getBean(Init.class);
        logger.info("------------########_-_-----------");
        for(String beanBean:context.getBeanDefinitionNames()){
            logger.info(beanBean);
        }
        init.start(args);

        logger.info("\nTHE END\n");

    }


    private void start(String[] args){

        //wot = new HttpWargamingClient("Edzio_Niszczyciel");
        wot.findPlayerByName("Edzio_Niszczyciel");

        System.out.println("Testing 1 - Send Http GET request");

        Player player = wot.player;
        System.out.println(player.getId());
        System.out.println(player.getName());
        System.out.println("FRAGI");
        System.out.println(player.getKills());
        System.out.println(player.getWins());
        System.out.println(player.getLosses());

        //HttpRiotClient riot = new HttpRiotClient("kaimada");

        httpRiotClient.findPlayerByName("kaimada");
        System.out.println("Testing 1 - Send Http GET request");

        Player player2 = httpRiotClient.player;
        System.out.println(player2.getId());
        System.out.println(player2.getName());
        System.out.println(player2.getKills());
        System.out.println(player2.getWins());
        System.out.println(player2.getLosses());

    }
}
