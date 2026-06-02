package me.drakosha.bedwars.player;

import lombok.Getter;
import me.drakosha.bedwars.game.Game;
import me.drakosha.bedwars.game.Team;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GamePlayerManager {
    private static GamePlayerManager instance;
    @Getter
    private final Map<UUID, GamePlayer> gamePlayerMap = new HashMap<>();
    private GamePlayerManager(){
    }
    public static GamePlayerManager getInstance(){
        if (instance == null){
            instance = new GamePlayerManager();
        }
        return instance;
    }

    public void addGamePlayer(Player player, Game game){
        if (gamePlayerMap.containsKey(player.getUniqueId())) return;
        gamePlayerMap.put(player.getUniqueId(), new GamePlayer(player, game));
    }
    public void addTeamPlayer(Player player, Team team){
        GamePlayer gp = gamePlayerMap.get(player.getUniqueId());
        if (gp.getTeam() != null){
            gp.getTeam().removePlayerTeam(player);
        }
        gp.setTeam(team);
        gp.getTeam().addPlayerTeam(player);
    }
    public void removePlayer(Player player){
        gamePlayerMap.remove(player.getUniqueId());
    }


}
