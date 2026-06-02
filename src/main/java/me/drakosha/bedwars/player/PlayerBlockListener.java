package me.drakosha.bedwars.player;

import me.drakosha.bedwars.game.Team;
import org.bukkit.Material;
import org.bukkit.block.Bed;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

public class PlayerBlockListener implements EventListener, Listener {
    private final Set<Block> playerPlacesBlock;
    public PlayerBlockListener(){
        this.playerPlacesBlock = new HashSet<>();
    }

    @EventHandler
    public void playerPlaceBlockEvent(BlockPlaceEvent event){
        playerPlacesBlock.add(event.getBlockPlaced());
    }

    @EventHandler
    public void playerBlockBlockEvent(BlockBreakEvent event){
        GamePlayerManager gpm = GamePlayerManager.getInstance();
        GamePlayer gamePlayer = gpm.getGamePlayerMap().get(event.getPlayer().getUniqueId());
        if (event.getBlock().getType() == Material.BED_BLOCK
                &&  gamePlayer.getGame().getBedMap().get(event.getBlock()) != null){
            Block block = event.getBlock();
            Team team = gamePlayer.getGame().getBedMap().get(block);
            assert team != null;
                if (team == gamePlayer.getTeam()){
                    gamePlayer.getPlayer().sendMessage("you can`t break your bed");
                    event.setCancelled(true);
                    return;
                }
            team.setTeamBedAlive(false);
                event.setCancelled(false);
                return;
        }
        if (!playerPlacesBlock.contains(event.getBlock())){
            event.setCancelled(true);
        }
        playerPlacesBlock.remove(event.getBlock());
    }
    @EventHandler
    public void explosionPrimeEvent(EntityExplodeEvent event){
        if (event.getEntity() instanceof TNTPrimed){
            event.blockList().removeIf(block -> !playerPlacesBlock.contains(block));
        }
    }
}
