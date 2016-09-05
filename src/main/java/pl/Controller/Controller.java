package pl.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.model.GamesEnum;
import pl.model.Player;
import pl.service.HttpWotLolInterface;
import pl.service.riot.HttpRiotClient;
import pl.service.valve.HttpCSGOClient;
import pl.service.valve.HttpL4D2Client;
import pl.service.valve.HttpValveInterface;
import pl.service.wargaming.HttpWargamingClient;

import java.util.Objects;

/**
 * Created by Adam on 2016-09-04.
 */
public class Controller {
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

    ObservableList<PieChart.Data> inGameHours = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> matchesPlayed = FXCollections.observableArrayList();

    public void setDefaultPieChartData(PieChart pieChart){
        pieChart.setTitle("In game hours");
        pieChart.setData(inGameHours);
    }
    public void pieChartChanged(RadioMenuItem item, PieChart pieChart){
        pieChart.setTitle(item.getText());
        switch (item.getText()){
            case("Matches played"):{
                pieChart.setData(matchesPlayed);
                break;
            }
            case("Ingame hours"):{
                pieChart.setData(inGameHours);
                break;
            }
        }
    }

    private void updatePieChart(GamesEnum.Game game, Player player){
        updateOneDataOfChart(game,player,inGameHours);
        updateOneDataOfChart(game,player,matchesPlayed);
    }

    private void updateOneDataOfChart(GamesEnum.Game game, Player player, ObservableList<PieChart.Data> list){
        double value = 0;
        if(list == inGameHours) value = calculateInGameHours(game,player);
        if(list == matchesPlayed) value = calculateMatchesPlayed(player);

        boolean found = false;
        for(PieChart.Data data:list){
            if(Objects.equals(data.getName(), game.toString())){
                data.setPieValue(value);
                found = true;
            }
        }
        if(!found) list.add(new PieChart.Data(game.toString(), value));
    }

    private double calculateMatchesPlayed(Player player){
        return player.getLosses()+player.getWins();
    }

    private double calculateInGameHours(GamesEnum.Game game, Player player){
        double time = 0;
        switch (game){
            case WoT: {
                time = (player.getWins()+player.getLosses())*7/60;
                break;
            }
            case LoL: {
                time = (player.getWins()+player.getLosses())*34/60;
                break;
            }
            case CSGO: {
                time = (player.getWins()+player.getLosses())*42/60;
                break;
            }
            case L4D2:  {
                time = (player.getWins()+player.getLosses())*128/60;
                break;
            }
        }
        return time;
    }

    public boolean calculatePlayerData(GamesEnum.Game game, String message, TextField killsField, TextField winsField, TextField lossesField, TextField nameField){
        Player player = null;
        switch (game){
            case WoT: {
                httpWargamingClient = new HttpWargamingClient();
                player = httpWargamingClient.findPlayerByName(message);
                break;
            }
            case LoL: {
                httpRiotClient = new HttpRiotClient();
                player = httpRiotClient.findPlayerByName(message);
                break;
            }
            case CSGO: {
                httpCSGOClient = new HttpCSGOClient();
                player = httpCSGOClient.findPlayerById(message);
                break;
            }
            case L4D2:  {
                httpL4D2Client = new HttpL4D2Client();
                player = httpL4D2Client.findPlayerById(message);
                break;
            }
        }
        killsField.setText(player.getKills().toString());
        lossesField.setText(player.getLosses().toString());
        winsField.setText(player.getWins().toString());
        nameField.setText(player.getName());
        updatePieChart(game,player);
        return true;
    }
}
