package pl.model;

/**
 * Created by kaima_000 on 2016-09-05.
 */
public class GamesEnum{
    public enum Game{
        WoT("World of Tanks"),
        LoL("League of Legends"),
        CSGO("Counter Strike Global Offensive"),
        L4D2("Left 4 Dead 2");

        String fullName;

        Game(String getFullName){
            fullName = getFullName;
        }

        public static String getFullName(Game game){
            return game.fullName;
        }
    }
}
