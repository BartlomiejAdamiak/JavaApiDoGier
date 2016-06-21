/**
 * Created by kaima_000 on 2016-06-09.
 */
public class WorldOfTanksPlayer implements PlayerInterface {
    private Integer id = 0;
    private String name = "";
    private Integer kills = 0;
    private Integer wins = 0;

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

    private Integer losses = 0;

    public WorldOfTanksPlayer(String name){
        this.setName(name);
    }

    public WorldOfTanksPlayer(){

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
