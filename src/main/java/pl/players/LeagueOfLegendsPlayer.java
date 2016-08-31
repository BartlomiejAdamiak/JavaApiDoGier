package pl.players;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Created by kaima_000 on 2016-08-29.
 */
//@Component
public class LeagueOfLegendsPlayer implements PlayerInterface {

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

    public LeagueOfLegendsPlayer(String name){
        this.setName(name);
    }

}
