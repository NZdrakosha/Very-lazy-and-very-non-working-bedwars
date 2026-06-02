package me.drakosha.bedwars.map;

import org.bukkit.World;

import java.util.UUID;

public interface GameMap {
    void load();
    void unload();

    World getWorld();
    UUID getUUID();
    boolean isLoaded();


}
