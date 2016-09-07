package pl.model;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

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
public class OneGameView {
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

    public OneGameView(TextField playerKillsOutput, TextField playerLossesOutput, TextField playerWinsOutput, TextField playerNameOutput) {
        this.playerKillsOutput = playerKillsOutput;
        this.playerLossesOutput = playerLossesOutput;
        this.playerWinsOutput = playerWinsOutput;
        this.playerNameOutput = playerNameOutput;
    }
}