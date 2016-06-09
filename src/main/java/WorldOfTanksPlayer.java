/**
 * Created by kaima_000 on 2016-06-09.
 */
public class WorldOfTanksPlayer implements PlayerInterface {
    private Integer id = 0;
    private String name = "";
    private Integer kills = 0;

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
