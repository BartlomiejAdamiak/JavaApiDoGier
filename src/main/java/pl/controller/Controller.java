package pl.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.View.View;
import pl.model.GamesEnum;
import pl.model.OneGameView;
import pl.model.Player;
import pl.service.HttpWotLolInterface;
import pl.service.riotAndWargaming.HttpRiotClient;
import pl.service.riotAndWargaming.HttpWargamingClient;
import pl.service.valve.HttpCSGOClient;
import pl.service.valve.HttpL4D2Client;
import pl.service.valve.HttpValveInterface;

import java.util.Objects;

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
public enum Controller {

    controllerInstance;

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

    @Getter
    @Setter
    ObservableList<PieChart.Data> inGameHours = FXCollections.observableArrayList();

    @Getter
    @Setter
    ObservableList<PieChart.Data> matchesPlayed = FXCollections.observableArrayList();

    public ObservableList<PieChart.Data> getDefaultData() {
        return inGameHours;
    }

    public ObservableList<PieChart.Data> pieChartChanged(String string) {
        switch (string) {
            case ("Matches played"): {
                return matchesPlayed;
            }
            case ("Ingame hours"): {
                return inGameHours;
            }
        }
        return matchesPlayed;
    }

    private void updatePieChart(GamesEnum.Game game, Player player) {
        updateOneDataOfChart(game, player, inGameHours);
        updateOneDataOfChart(game, player, matchesPlayed);
    }

    private void updateOneDataOfChart(GamesEnum.Game game, Player player, ObservableList<PieChart.Data> list) {
        double value = 0;
        if (list == inGameHours) value = calculateInGameHours(game, player);
        if (list == matchesPlayed) value = calculateMatchesPlayed(player);

        boolean found = false;
        for (PieChart.Data data : list) {
            if (Objects.equals(data.getName(), game.toString())) {
                data.setPieValue(value);
                found = true;
            }
        }
        if (!found) list.add(new PieChart.Data(game.toString(), value));
    }

    private double calculateMatchesPlayed(Player player) {
        return player.getLosses() + player.getWins();
    }

    private double calculateInGameHours(GamesEnum.Game game, Player player) {
        double time = 0;
        switch (game) {
            case WoT: {
                time = (player.getWins() + player.getLosses()) * 7 / 60;
                break;
            }
            case LoL: {
                time = (player.getWins() + player.getLosses()) * 34 / 60;
                break;
            }
            case CSGO: {
                time = (player.getWins() + player.getLosses()) * 42 / 60;
                break;
            }
            case L4D2: {
                time = (player.getWins() + player.getLosses()) * 128 / 60;
                break;
            }
        }
        return time;
    }

    public void calculatePlayerData(GamesEnum.Game game, String message, OneGameView oneGame) throws Exception {
        Player player = null;
        switch (game) {
            case WoT: {
                httpWargamingClient = new HttpWargamingClient();
                httpWargamingClient.findPlayerByName(message);
                player = httpWargamingClient.getPlayer();
                break;
            }
            case LoL: {
                httpRiotClient = new HttpRiotClient();
                httpRiotClient.findPlayerByName(message);
                player = httpRiotClient.getPlayer();
                break;
            }
            case CSGO: {
                httpCSGOClient = new HttpCSGOClient();
                httpCSGOClient.findPlayerById(message);
                player = httpCSGOClient.getPlayer();
                break;
            }
            case L4D2: {
                httpL4D2Client = new HttpL4D2Client();
                httpL4D2Client.findPlayerById(message);
                player = httpL4D2Client.getPlayer();
                break;
            }
        }
        oneGame.getPlayerKillsOutput().setText(player.getKills().toString());
        oneGame.getPlayerLossesOutput().setText(player.getLosses().toString());
        oneGame.getPlayerWinsOutput().setText(player.getWins().toString());
        oneGame.getPlayerNameOutput().setText(player.getName());
        updatePieChart(game, player);
    }

    public void exceptionOccured(Exception e) {
        View.viewInstance.createPopUp(e.getMessage());
    }
}
