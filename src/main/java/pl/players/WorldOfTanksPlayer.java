package pl.players;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Created by kaima_000 on 2016-06-09.
 */
@Component
public class WorldOfTanksPlayer implements PlayerInterface {

    @Setter
    @Getter
    Integer id = 0;

    @Setter
    @Getter
    String name = "";

    @Setter
    @Getter
    Integer kills = 0;

    @Setter
    @Getter
    Integer wins = 0;

    @Setter
    @Getter
    Integer losses = 0;

    public WorldOfTanksPlayer(String name){
        this.setName(name);
    }

}
