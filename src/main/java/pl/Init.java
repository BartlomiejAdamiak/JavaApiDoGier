package pl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import pl.configuration.MyConfig;
import pl.model.Player;
import pl.service.HttpWotLolInterface;
import pl.service.valve.HttpValveInterface;

/**
 * Created by kaima_000 on 2016-06-09.
 */
@Service
public class Init {

    @Autowired
    @Qualifier("httpWargamingClient")
    HttpWotLolInterface httpWargamingClient;

    @Autowired
    @Qualifier("httpRiotClient")
    HttpWotLolInterface httpRiotClient;

    @Autowired
    @Qualifier("httpCSGOClient")
    HttpValveInterface httpCSGOClient;

    @Autowired
    @Qualifier("httpCSGOClient")
    HttpValveInterface httpL4D2Client;

    final static Logger logger = Logger.getLogger(Init.class);

    public static void main(String[] args) throws Exception {

        logger.info("\nSTART!\n");

        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Init init = context.getBean(Init.class);
        logger.info("------------########------------");
        for(String beanBean:context.getBeanDefinitionNames()){
            logger.info(beanBean);
        }
        init.start(args);

        logger.info("\nTHE END\n");

    }


    private void start(String[] args){

        System.out.println("World of Tanks");


        Player player = httpWargamingClient.findPlayerByName("Edzio_Niszczyciel");
        System.out.println(player.getId());
        System.out.println(player.getName());
        System.out.println("FRAGI");
        System.out.println(player.getKills());
        System.out.println(player.getWins());
        System.out.println(player.getLosses());

        System.out.println("League of Legends");



        Player player2 = httpRiotClient.findPlayerByName("jagle13");
        System.out.println(player2.getId());
        System.out.println(player2.getName());
        System.out.println(player2.getKills());
        System.out.println(player2.getWins());
        System.out.println(player2.getLosses());

        System.out.println("CS:GO");

        Player player3 = httpCSGOClient.findPlayerById("76561197990828076");
        System.out.println(player3.getId());
        System.out.println(player3.getName());
        System.out.println(player3.getKills());
        System.out.println(player3.getWins());
        System.out.println(player3.getLosses());

        System.out.println("Left 4 Dead 2");

        Player player4 = httpL4D2Client.findPlayerById("76561197990828076");
        System.out.println(player4.getId());
        System.out.println(player4.getName());
        System.out.println(player4.getKills());
        System.out.println(player4.getWins());
        System.out.println(player4.getLosses());

    }
}
