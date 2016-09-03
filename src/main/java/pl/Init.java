package pl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
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
        window.setTitle("Title of the MAIN WINDOW");
        window.setOnCloseRequest(e -> {
            e.consume(); //oznacza, że zajmiemy się tym -> zwykły X nie zadziała, jeśli go nie ogarniemy ;)
            closeProgram();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        //przerwy miedzy elementami
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        //Label player1 name:
        Label playerNameLabel1 = new Label("Nick gracza:");
        GridPane.setConstraints(playerNameLabel1, 0, 0);

        //Player name input:
        TextField playerNameInput1 = new TextField("Edzio_Niszczyciel");
        GridPane.setConstraints(playerNameInput1, 1, 0);

        //Label player1 id:
        Label playerIdLabel1 = new Label("ID gracza:");
        GridPane.setConstraints(playerNameLabel1, 0, 1);

        //Player id input:
        TextField playerIdInput1 = new TextField();
        playerIdInput1.setPromptText("Jakieś ID");
        GridPane.setConstraints(playerNameInput1, 1, 1);

        //Przycisk przesyłający ID z boxa do programu
        Button newButton = new Button("Prześlij ID do programu");
        newButton.setOnAction(e -> {
            isInt(playerIdInput1, playerIdInput1.getText());
        });
        GridPane.setConstraints(newButton, 1, 2);

        gridPane.getChildren().addAll(playerNameLabel1, playerNameInput1, playerIdLabel1, playerIdInput1, newButton);

        Scene scene3 = new Scene(gridPane, 400, 400);

        HBox topMenu = new HBox();
        Button buttonA = new Button("File");
        Button buttonB = new Button("Edit");
        Button buttonC = new Button("View");
        topMenu.getChildren().addAll(buttonA, buttonB, buttonC);

        VBox leftMenu = new VBox();
        Button buttonLA = new Button("Left one");
        Button buttonLB = new Button("Left two");
        Button buttonLC = new Button("Left three");
        leftMenu.getChildren().addAll(buttonLA, buttonLB, buttonLC);

        //Dzięki temu mamy ładny podział na kawałki (TOP, LEFT, CENTER, RIGHT, BUTTOM)
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenu);


        primaryStage.setTitle("Java Api do Gier");
        Label label1 = new Label("Welcome!");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));
        Button buttonNew = new Button("Alert!");
        buttonNew.setOnAction(e -> AlertBox.display("Title of the window!", "Wow alert box rocks"));

        Button confirmButton = new Button("Confirm button!");
        confirmButton.setOnAction(e -> {
            boolean result = ConfirmBox.display("Title of the confirm Button!", "Do u want to do it?!");
            if(result==true){
                label1.setText("Nowa nazwa label1.");
            }else label1.setText("Hahahaha!");
        });

        Button buttonGoToPlayers = new Button("Got to Players! WOW");
        buttonGoToPlayers.setOnAction(e -> window.setScene(scene3));

        Button closeProgramButton = new Button("Close program!");
        closeProgramButton.setOnAction(e ->{
            closeProgram();
        });

        //Layout 1 - children are laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1, buttonNew, confirmButton, buttonGoToPlayers, closeProgramButton);
        borderPane.setCenter(layout1);
        scene1 = new Scene(borderPane, 600, 350);

        //Button 2
        Button button2 = new Button();
        button2.setText("Go back to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));



        //Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 600, 300);

        window.setScene(scene1);
        window.setTitle("Title of the window!");
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