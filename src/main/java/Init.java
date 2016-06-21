/**
 * Created by kaima_000 on 2016-06-09.
 */
public class Init {
    public static void main(String[] args) throws Exception {
        HttpWargamingClient clientWargaming = new HttpWargamingClient();

        System.out.println("Testing 1 - Send Http GET request");
        WorldOfTanksPlayer player = clientWargaming.findPlayerByName("kaimada");
        System.out.println(player.getId());
        System.out.println(player.getName());
        System.out.println(player.getKills());
        System.out.println(player.getWins());
        System.out.println(player.getLosses());

    }
}
