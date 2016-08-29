/**
 * Created by kaima_000 on 2016-06-09.
 */
public interface PlayerInterface {
    Integer id = 0;
    String name = "";
    Integer kills = 0;
    Integer wins = 0;
    Integer losses = 0;

    String getName();

    void setName(String name);

    Integer getKills();

    void setKills(Integer kills);

    Integer getId();

    void setId(Integer id);

    Integer getLosses();

    void setLosses(Integer losses);

    Integer getWins();

    void setWins(Integer wins);
}
