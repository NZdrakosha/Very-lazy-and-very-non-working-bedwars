package me.drakosha.bedwars;

import lombok.Getter;
import me.drakosha.bedwars.commands.GameModeCommand;
import me.drakosha.bedwars.commands.SupCommand;
import me.drakosha.bedwars.player.PlayerBlockListener;
import me.drakosha.bedwars.player.PlayerGameListener;
import me.drakosha.bedwars.shop.VillagerListener;
import me.drakosha.locationfromsignapi.LocationFromSignAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Bedwars extends JavaPlugin {
    @Getter
    private static Bedwars instance;

    @Override
    public void onEnable() {
        instance = this;

        LocationFromSignAPI.init(this);

        saveResource("config.yml", true);
        getCommand("s").setExecutor(new SupCommand());
        getCommand("gm").setExecutor(new GameModeCommand());


        Bukkit.getPluginManager().registerEvents(new PlayerBlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerGameListener(), this);
        Bukkit.getPluginManager().registerEvents(new VillagerListener(), this);
    }

    @Override
    public void onDisable() {
    }

}
