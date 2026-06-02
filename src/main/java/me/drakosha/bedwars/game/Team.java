package me.drakosha.bedwars.game;

import lombok.Getter;
import lombok.Setter;
import me.drakosha.bedwars.generator.Generator;
import me.drakosha.bedwars.shop.ShopAndUpdate;
import me.drakosha.locationfromsignapi.ymlutil.YmlUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Team {
    private final String color;
    private final Location spawnLocation;
    private final Generator generatorTeamIron;
    private final Generator generatorTeamGold;
    @Setter
    private boolean teamBedAlive;
    private final Set<UUID> allPlayers;
    private final int maxPlayerInTeam;

    public Team(String color, boolean teamBedAlive, int numberMap, World world, int maxPlayerInTeam){
        ShopAndUpdate.createVillagerShop(YmlUtil.getLocation(numberMap + ".shop." + color, world));
        ShopAndUpdate.createVillagerUpdate(YmlUtil.getLocation(numberMap + ".update." + color, world));
        this.color = color;
        this.maxPlayerInTeam = maxPlayerInTeam;
        this.spawnLocation = YmlUtil.getLocation(numberMap + ".spawn." + color, world);
        this.generatorTeamIron = new Generator(YmlUtil.getLocation(numberMap + ".generator." + color, world), new ItemStack(Material.IRON_INGOT), 5);
        this.generatorTeamGold = new Generator(YmlUtil.getLocation(numberMap + ".generator." + color, world), new ItemStack(Material.GOLD_INGOT), 14);
        this.teamBedAlive = teamBedAlive;
        this.allPlayers = new HashSet<>();
    }

    public void addPlayerTeam(Player player){
        if (allPlayers.size() == maxPlayerInTeam){
            player.sendMessage("team already filled");
            return;
        }
        allPlayers.add(player.getUniqueId());
        player.setBedSpawnLocation(getSpawnLocation());
    }
    public void removePlayerTeam(Player player){
        allPlayers.remove(player.getUniqueId());
    }
}
