package pl.service.riotAndWargaming;

import org.springframework.stereotype.Component;
import pl.service.HttpWotLolInterface;

/**
 * Created by Adam on 2016-09-07.
 */
@Component
public class ConcreteCreatorWotRiot implements CreatorWotRiot{
    public HttpWotLolInterface create(String switcher){
        switch(switcher){
            case "WorldOfTanks":
                return new HttpWargamingClient();
            case "LeagueOfLegends":
                return new HttpRiotClient();
        }
        return null;
    }

}
