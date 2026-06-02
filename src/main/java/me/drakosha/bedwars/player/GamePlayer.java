package me.drakosha.bedwars.player;

import lombok.Getter;
import lombok.Setter;
import me.drakosha.bedwars.game.Game;
import me.drakosha.bedwars.game.Team;
import org.bukkit.entity.Player;

@Getter
public class GamePlayer {
    private final Player player;
    @Setter
    private Team team;
    @Setter
    private Game game;

    public GamePlayer(Player player, Game game){
        this.player = player;
        this.team = null;
        this.game = game;
    }


}
