package pl.model;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kaima_000 on 2016-09-06.
 */
public class oneGameView {
    @Getter
    @Setter
    GamesEnum.Game game;
    @Getter
    @Setter
    TextField playerKillsOutput;
    @Getter
    @Setter
    TextField playerLossesOutput;
    @Getter
    @Setter
    TextField playerWinsOutput;
    @Getter
    @Setter
    TextField playerNameOutput;

    public oneGameView(TextField playerKillsOutput,TextField playerLossesOutput,TextField playerWinsOutput,TextField playerNameOutput) {
        this.playerKillsOutput = playerKillsOutput;
        this.playerLossesOutput = playerLossesOutput;
        this.playerWinsOutput = playerWinsOutput;
        this.playerNameOutput = playerNameOutput;
    }
}
