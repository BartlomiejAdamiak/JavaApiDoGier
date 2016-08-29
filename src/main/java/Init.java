/**
 * Created by kaima_000 on 2016-06-09.
 */
public class Init {
    public static void main(String[] args) throws Exception {
        HttpWargamingClient wot = new HttpWargamingClient("kaimada");

        System.out.println("Testing 1 - Send Http GET request");

        PlayerInterface player = wot.player;
        System.out.println(player.getId());
        System.out.println(player.getName());
        System.out.println(player.getKills());
        System.out.println(player.getWins());
        System.out.println(player.getLosses());

        HttpRiotClient riot = new HttpRiotClient("kaimada");

        System.out.println("Testing 1 - Send Http GET request");

        PlayerInterface player2 = riot.player;
        System.out.println(player2.getId());
        System.out.println(player2.getName());
        System.out.println(player2.getKills());
        System.out.println(player2.getWins());
        System.out.println(player2.getLosses());

    }
}
