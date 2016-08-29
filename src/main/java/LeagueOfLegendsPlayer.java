/**
 * Created by kaima_000 on 2016-08-29.
 */
public class LeagueOfLegendsPlayer implements PlayerInterface{
    Integer id = 0;
    String name = "";
    Integer kills = 0;
    Integer wins = 0;
    Integer losses = 0;

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getWins() {

        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public LeagueOfLegendsPlayer(String name){
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
