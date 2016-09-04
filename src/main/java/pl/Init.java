package pl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import pl.configuration.MyConfig;
import pl.gui.AlertBox;
import pl.gui.ConfirmBox;
import pl.model.Player;
import pl.service.HttpWotLolInterface;
import pl.service.valve.HttpValveInterface;

/**
 * Created by kaima_000 on 2016-06-09.
 */
@Service
public class Init extends Application /*implements EventHandler<ActionEvent>*/{

    Stage window;
    Scene scene1, scene2;
    BorderPane layout;

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
        init.startBackground(args);

        launch(args);
        logger.info("\nTHE END\n");

    }


    private void startBackground(String[] args){

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


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Java api do gier");

        //File menu
        Menu gamesInfoMenu = new Menu("Info o grach");
        MenuItem wotInfo = new MenuItem("World of tanks");
        wotInfo.setOnAction(e -> System.out.println("World of tanks info clicked"));

        MenuItem lolInfo = new MenuItem("League of Legends");
        lolInfo.setOnAction(e -> System.out.println("League of Legends info clicked"));

        MenuItem csgoInfo = new MenuItem("CS:GO");
        csgoInfo.setOnAction(e -> System.out.println("CS:GO info clicked"));

        MenuItem l4d2Info = new MenuItem("L4D2");
        l4d2Info.setOnAction(e -> System.out.println("L4D2 info clicked"));

        MenuItem separatorMenuItem = new SeparatorMenuItem();

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {
            System.out.println("Exit clicked");
        });
        gamesInfoMenu.getItems().addAll(wotInfo, lolInfo, csgoInfo, l4d2Info, separatorMenuItem, exit);

        //Help menu

        Menu helpMenu = new Menu("Pomoc");
        CheckMenuItem showLines = new CheckMenuItem("Wyświetl autorów programu");
        showLines.setSelected(false);
        showLines.setOnAction(e -> {
            if(showLines.isSelected())
            {
                System.out.println("Program wyświetli autorów programu");
            }
            else
                System.out.println("Ukryto autorów programu");
        });
        helpMenu.getItems().addAll(showLines);

        //Difficulty RadioMenuItems
        Menu someMenu = new Menu("Jakieś menu inne");
        ToggleGroup optionsToggle = new ToggleGroup();

        RadioMenuItem firstOpt = new RadioMenuItem("Pierwsza opcja");
        RadioMenuItem secondOpt = new RadioMenuItem("Druga opcja");
        RadioMenuItem thirdOpt = new RadioMenuItem("Trzecia opcja");

        firstOpt.setToggleGroup(optionsToggle);
        secondOpt.setToggleGroup(optionsToggle);
        thirdOpt.setToggleGroup(optionsToggle);

        someMenu.getItems().addAll(firstOpt, secondOpt, thirdOpt);

        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(gamesInfoMenu, helpMenu, someMenu);


        //CENTER
        //Label player1 name:
        Label playerNameLabel1 = new Label("Nick gracza:");

        //Player name input:
        TextField playerNameInput1 = new TextField("Edzio_Niszczyciel");

        //Label player1 id:
        Label playerIdLabel1 = new Label("ID gracza:");

        //Player id input:
        TextField playerIdInput1 = new TextField();
        playerIdInput1.setPromptText("Jakieś ID");

        //Przycisk przesyłający ID z boxa do programu
        Button newButton = new Button("Prześlij ID do programu");
        newButton.setOnAction(e -> {
            isInt(playerIdInput1, playerIdInput1.getText());
        });


        //Layout
        VBox centerLayout = new VBox(10);
        centerLayout.setPadding(new Insets(20, 20, 20, 20));
        centerLayout.getChildren().addAll(playerNameLabel1, playerNameInput1, playerIdLabel1, playerIdInput1, newButton);


        layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(centerLayout);
        Scene scene = new Scene(layout, 600, 500);
        window.setScene(scene);
        window.show();
    }

    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Title of confirm box", "Sure you want to exit?");
        if(answer){
            window.close();
        }
    }

    private boolean isInt(TextField input, String message){
        try{
            int id = Integer.parseInt(input.getText());
            System.out.println("The ID is: " + id);
            return true;
        }catch(NumberFormatException e){
            System.out.println("Error: " + message + e);
            return false;

        }

    }

}