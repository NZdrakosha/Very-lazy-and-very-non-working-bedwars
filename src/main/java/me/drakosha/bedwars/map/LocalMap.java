package me.drakosha.bedwars.map;

import me.drakosha.bedwars.Bedwars;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class LocalMap implements GameMap {
    private final File mapSource;
    private final File activeMap;
    private World world;
    private final UUID gameUUID;
    private boolean isLoaded;

    public LocalMap(File mapSource, UUID gameUUID){
        this.mapSource = mapSource;
        this.gameUUID = gameUUID;
        activeMap = new File(Bukkit.getWorldContainer().getPath(), gameUUID.toString());
        isLoaded = false;
    }

    @Override
    public void load() {
        Bukkit.getScheduler().runTaskAsynchronously(Bedwars.getInstance(), ()-> {
        try {
            FileUtils.copyDirectory(mapSource, activeMap);
            Bukkit.getScheduler().runTask(Bedwars.getInstance(), () -> {
                world = new WorldCreator(gameUUID.toString()).generateStructures(false).createWorld();
                world.setTime(6000L);
                world.setGameRuleValue("doDaylightCycle", "false");
                world.setGameRuleValue("doWeatherCycle", "false");
                world.setStorm(false);
                world.setThundering(false);

                world.setSpawnFlags(false, false);
                world.setGameRuleValue("doMobSpawning", "false");
                world.getLivingEntities().forEach(e -> {
                    if (!(e instanceof org.bukkit.entity.Player)) e.remove();
                });

                world.setGameRuleValue("doFireTick", "false");
                world.setGameRuleValue("mobGriefing", "false");
                world.setGameRuleValue("randomTickSpeed", "0");
                world.setGameRuleValue("doTileDrops", "false");

                world.setAutoSave(false);
                isLoaded = true;
            });
        }catch (IOException e){
            Bukkit.getLogger().severe(e.toString());
            }
        });
    }

    @Override
    public void unload() {
        if (isLoaded) {
            if (!world.getPlayers().isEmpty())
                for (Player p : world.getPlayers())
                    p.teleport(Bukkit.getWorld("world").getSpawnLocation());

            Bukkit.getScheduler().runTaskLater(Bedwars.getInstance(), () -> {
                if (Bukkit.unloadWorld(getUUID().toString(), false)) {
                    world = null;
                    Bukkit.getScheduler().runTaskAsynchronously(Bedwars.getInstance(), () -> {
                        try {
                            FileUtils.deleteDirectory(activeMap);
                        } catch (IOException e) {
                            Bukkit.getLogger().severe(e.toString());
                        }
                    });
                }
            }, 60L);
        }

    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public UUID getUUID() {
        return gameUUID;
    }
    @Override
    public boolean isLoaded(){
        return isLoaded;
    }
}
