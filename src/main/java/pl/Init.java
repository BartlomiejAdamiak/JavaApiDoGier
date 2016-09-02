package pl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import pl.configuration.MyConfig;
import pl.model.Player;

/**
 * Created by kaima_000 on 2016-06-09.
 */
@Service
public class Init {

    @Autowired
    HttpWargamingClient wot;

    @Autowired
    HttpRiotClient httpRiotClient;

    @Autowired
    HttpCSGOClient httpL4D2Client;

    @Autowired
    HttpL4D2Client httpCSGOClient;

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
        wot.findPlayerByName("Edzio_Niszczyciel");

        Player player = wot.player;
        System.out.println(player.getId());
        System.out.println(player.getName());
        System.out.println("FRAGI");
        System.out.println(player.getKills());
        System.out.println(player.getWins());
        System.out.println(player.getLosses());

        System.out.println("League of Legends");
        httpRiotClient.findPlayerByName("kaimada");

        Player player2 = httpRiotClient.player;
        System.out.println(player2.getId());
        System.out.println(player2.getName());
        System.out.println(player2.getKills());
        System.out.println(player2.getWins());
        System.out.println(player2.getLosses());

        System.out.println("CS:GO");
        httpCSGOClient.findPlayerById("76561197990828076");

        Player player3 = httpCSGOClient.player;
        System.out.println(player3.getId());
        System.out.println(player3.getName());
        System.out.println(player3.getKills());
        System.out.println(player3.getWins());
        System.out.println(player3.getLosses());

        System.out.println("Left 4 Dead 2");
        httpL4D2Client.findPlayerById("76561197990828076");

        Player player4 = httpL4D2Client.player;
        System.out.println(player4.getId());
        System.out.println(player4.getName());
        System.out.println(player4.getKills());
        System.out.println(player4.getWins());
        System.out.println(player4.getLosses());

    }
}
