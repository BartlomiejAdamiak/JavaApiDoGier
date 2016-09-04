package pl.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * Created by Adam on 2016-09-04.
 */
@Component
public class MainGui extends Application {

    Stage window;
    Scene scene1, scene2;
    BorderPane layout;

    public MainGui() {
    }

    public MainGui(Stage window, Scene scene1, Scene scene2, BorderPane layout) {
        this.window = window;
        this.scene1 = scene1;
        this.scene2 = scene2;
        this.layout = layout;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));*/
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
        /*primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
    }

    /*public void btn(ActionEvent actionEvent){
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Kills", 2500),
                new PieChart.Data("Deaths", 200),
                new PieChart.Data("Shots", 7000),
                new PieChart.Data("HeadShots", 123)
        );
        pieChart.setData(list);
    }*/

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
