package pl.View;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationListener;
import pl.Control.Controller;
import pl.model.EventPlayerNotFound;
import pl.model.GamesEnum;
import pl.model.OneGameView;

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
public enum View {

    viewInstance;

    private final static Logger logger = Logger.getLogger(View.class);

    @FXML
    PieChart pieChart;

    Stage window;
    HBox layout;
    MenuBar menuBar;

    public void prepareView(Stage stage){
        window = stage;
        window.setTitle("Java api do gier");

        VBox vBoxWoT = prepareLayout(GamesEnum.Game.WoT,"Edzio_Niszczyciel");
        VBox vBoxLoL = prepareLayout(GamesEnum.Game.LoL,"jagle13");
        VBox vBoxCS = prepareLayout(GamesEnum.Game.CSGO,"76561197990828076");
        VBox vBoxL4D2 = prepareLayout(GamesEnum.Game.L4D2,"76561197990828076");

        preparePieChart();
        prepareMenuBar();

        layout = new HBox(vBoxWoT,vBoxLoL,vBoxCS,vBoxL4D2,menuBar,pieChart);
        menuBar.setMinWidth(100);
        Scene scene = new Scene(layout, 1200, 500);
        window.setScene(scene);
        window.show();
    }

    private void preparePieChart(){
        pieChart = new PieChart();
        setDefaultPieChartData();
    }

    public void setDefaultPieChartData(){
        pieChart.setTitle("In game hours");
        pieChart.setData(Controller.controllerInstance.getDefaultData());
    }

    private MenuBar prepareMenuBar(){
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(prepareRadioMenu());
        return menuBar;
    }

    private Menu prepareRadioMenu(){
        Menu pieChartMenu = new Menu("Chart options");
        ToggleGroup optionsToggle = new ToggleGroup();

        RadioMenuItem firstOpt = new RadioMenuItem("Ingame hours");
        firstOpt.setOnAction(e -> {
            changePieData(firstOpt);

        });
        RadioMenuItem secondOpt = new RadioMenuItem("Matches played");
        secondOpt.setOnAction(e -> {
            changePieData(secondOpt);
        });

        firstOpt.setToggleGroup(optionsToggle);
        secondOpt.setToggleGroup(optionsToggle);

        pieChartMenu.getItems().addAll(firstOpt, secondOpt);
        return pieChartMenu;
    }

    private void changePieData(RadioMenuItem radioMenuItem){
        pieChart.setData(Controller.controllerInstance.pieChartChanged(radioMenuItem.getText()));
        pieChart.setTitle(radioMenuItem.getText());
    }

    @Pointcut("execution(* pl.View.View.prepareLayout(..))")
    public VBox prepareLayout(GamesEnum.Game game, String customInput){
        Label playerInputLabel = new Label(GamesEnum.Game.getFullName(game));
        TextField playerInput = new TextField(customInput);

        Label playerKillsLabel = new Label("Kills");
        TextField playerKillsOutput = new TextField();
        playerKillsOutput.setDisable(true);

        Label playerWinsLabel = new Label("Wins");
        TextField playerWinsOutput = new TextField();
        playerWinsOutput.setDisable(true);

        Label playerLossesLabel = new Label("Losses");
        TextField playerLossesOutput = new TextField();
        playerLossesOutput.setDisable(true);

        Label playerNameLabel = new Label("Name");
        TextField playerNameOutput = new TextField();
        playerNameOutput.setDisable(true);

        OneGameView gameView = new OneGameView(playerKillsOutput,playerLossesOutput,playerWinsOutput,playerNameOutput);

        Button buttonCalculatePlayer = new Button("Calculate");
        buttonCalculatePlayer.setOnAction(e -> {
            try {
                Controller.controllerInstance.calculatePlayerData(game, playerInput.getText(),gameView);
            } catch (Exception e1) {
                logger.error("Exception at prepareLayout(GamesEnum.Game, String): " + e1);
                createPopUp(e.toString());
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(playerInputLabel, playerInput, playerNameLabel, playerNameOutput,
                playerKillsLabel, playerKillsOutput, playerWinsLabel, playerWinsOutput, playerLossesLabel, playerLossesOutput, buttonCalculatePlayer);

        return layout;
    }

    public void createPopUp(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exception occured");
        alert.setHeaderText(null);
        alert.setContentText("Player not found");

        alert.showAndWait();
    }
}
