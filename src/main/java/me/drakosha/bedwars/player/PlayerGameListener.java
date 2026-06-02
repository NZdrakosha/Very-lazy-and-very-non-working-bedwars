package me.drakosha.bedwars.player;

import me.drakosha.bedwars.Bedwars;
import me.drakosha.bedwars.game.Game;
import me.drakosha.bedwars.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerGameListener implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
    }
    @EventHandler
    public void playerInteractInGui(InventoryClickEvent event){
        if (event.getClickedInventory() == null ) return;
        if (event.getClickedInventory().getTitle().equals("select game")){
            event.setCancelled(true);
            if (event.getInventory().getItem(event.getSlot()) != null && event.getCurrentItem().getType() != Material.AIR){
                GameManager gm = GameManager.getInstance();
                Game game =  gm.getActiveGame().get(event.getSlot());
                if (game != null) {
                    GamePlayerManager gpm = GamePlayerManager.getInstance();
                    gpm.addGamePlayer((Player) event.getWhoClicked(), game);
                    game.playerJoin((Player) event.getWhoClicked());
                }
            }
        }
    }

    @EventHandler
    public void playerDamageEvent(EntityDamageEvent event){
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID){
            Player player = (Player) event.getEntity();
            player.setHealth(0.0);
        }

    }
    @EventHandler
    public void playerDied(PlayerDeathEvent event) {
        Player player = event.getEntity();
        GamePlayer gp = GamePlayerManager.getInstance().getGamePlayerMap().get(player.getUniqueId());
        if (gp.getTeam().isTeamBedAlive()) {
            event.getEntity().spigot().respawn();
            event.getEntity().setGameMode(GameMode.SPECTATOR);
            event.getEntity().teleport(gp.getGame().getMapWorld().getSpawnLocation());
            Bukkit.getScheduler().runTaskLater(Bedwars.getInstance(), () -> {
                player.teleport(gp.getTeam().getSpawnLocation());
                player.setGameMode(GameMode.SURVIVAL);
            }, 60L);
        }else {
            player.teleport(player.getWorld().getSpawnLocation());
            player.sendMessage("you lose");
            gp.getGame().getPlayerInGame().remove(player);
            gp.getGame().winner();
            player.setGameMode(GameMode.SPECTATOR);
        }

    }
}
