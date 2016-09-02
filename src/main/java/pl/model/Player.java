package pl.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * Created by Adam on 2016-08-29.
 */
@Service
public class Player {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer kills;

    @Getter
    @Setter
    private Integer wins;

    @Getter
    @Setter
    private Integer losses;


    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }



    /*public Player(Integer id, String name, Integer kills, Integer wins, Integer losses) {
        this.id = id;
        this.name = name;
        this.kills = kills;
        this.wins = wins;
        this.losses = losses;
    }*/
}
