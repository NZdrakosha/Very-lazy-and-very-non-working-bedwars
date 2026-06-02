package me.drakosha.bedwars.game;

import lombok.Getter;
import lombok.var;
import me.drakosha.bedwars.Bedwars;
import me.drakosha.bedwars.generator.Generator;
import me.drakosha.bedwars.map.LocalMap;
import me.drakosha.bedwars.player.GamePlayerManager;
import me.drakosha.locationfromsignapi.ymlutil.YmlUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

@Getter
public class Game {
    private World mapWorld;
    private final int numberMap;
    private final Map<String, Team> activeTeam;
    private final List<Player> playerInGame;
    private final List<Generator> generatorList;
    private final LocalMap localMap;
    private final GameState gameState;
    private final List<String> availableTeamColor;
    private final Map<Block, Team> bedMap;
    GamePlayerManager gPM = GamePlayerManager.getInstance();


    public Game(int numberMap){
        this.numberMap = numberMap;
        this.playerInGame = new ArrayList<>();
        activeTeam = new HashMap<>();
        generatorList = new ArrayList<>();
        bedMap = new HashMap<>();
        localMap = new LocalMap(new File(Bedwars.getInstance().getDataFolder() + "/" + numberMap), UUID.randomUUID());
        availableTeamColor = TeamColor.listAllAvailableColor();
        localMap.load();


        Bukkit.getScheduler().runTaskLater(Bedwars.getInstance(),()->{
            mapWorld = localMap.getWorld();


            availableTeamColor.removeIf(color ->
                    YmlUtil.getLocation(numberMap + ".spawn." + color, mapWorld) == null);


            for (String c : availableTeamColor){

                createTeam(c);

                Location bedLocation = YmlUtil.getLocation(numberMap + ".bed." + c, mapWorld);

                bedMap.put(bedLocation.getBlock(), activeTeam.get(c));
            }
            }, 70L);
        gameState = GameState.STARTING;


    }


    public void start(){
        new BukkitRunnable() {
            int timer = 10;
            @Override
            public void run() {
                if (playerInGame.isEmpty()){
                    this.cancel();
                }
                timer--;
                for (Player player : mapWorld.getPlayers()){
                    player.sendMessage("start game in " + timer);
                }
                if (timer == 0){
                    for (Player p : playerInGame){
                        p.setInvulnerable(false);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
                    }
                    teleportPlayer();
                    fillGeneratorList();
                    startGeneratorTimer();
                    cancel();
                }


            }
        }.runTaskTimer(Bedwars.getInstance(), 0L, 20L);
    }

    public void playerJoin(Player player){
        player.teleport(YmlUtil.getLocation( numberMap + ".spawn.start", mapWorld));
        playerInGame.add(player);
        player.setInvulnerable(true);
        player.getInventory().clear();
        for (var t : activeTeam.entrySet()){
            if (!t.getValue().getAllPlayers().isEmpty()) continue;
            playerSelectTeam(player, t.getKey());
            break;
        }


        if (playerInGame.size() == 3) {
            start();
        }
    }
    public void winner(){
        if (playerInGame.size() == 1){
            Player player = playerInGame.get(0);
                player.sendMessage("you win");

                if (!mapWorld.getPlayers().isEmpty()){
                    for (Player p : mapWorld.getPlayers()){
                        p.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    }
            }
                mapWorld = null;
                Bukkit.getScheduler().runTaskLater(Bedwars.getInstance(), localMap::unload, 30L);
        }
    }
    private void createTeam(String color){
        if (activeTeam.get(color) == null) {
            Team team = new Team(color, true, numberMap, mapWorld, 1);
            activeTeam.put(color, team);
        }
    }
    private void playerSelectTeam(Player player, String color){
        if (activeTeam.get(color) == null) createTeam(color);
        if (activeTeam.get(color).getAllPlayers().size() == activeTeam.get(color).getMaxPlayerInTeam()){
            player.sendMessage("team is fulling");
            return;
        }
        gPM.addTeamPlayer(player, activeTeam.get(color));
    }
    private void teleportPlayer(){
        for (var p : playerInGame){
            p.teleport(gPM.getGamePlayerMap().get(p.getUniqueId()).getTeam().getSpawnLocation());
        }
    }


    private void startGeneratorTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (gameState == GameState.ENDING){
                    cancel();
                }
                for (var g : generatorList){
                    g.tick();
                }
            }
        }.runTaskTimer(Bedwars.getInstance(), 0L, 10L);
    }

    private void fillGeneratorList(){
        for (int i = 1; i <= 4; i++){
            if (YmlUtil.getLocation(numberMap + ".generator.emerald." + i, mapWorld) != null){
                System.out.println(YmlUtil.getLocation(numberMap + ".generator.emerald." + i, mapWorld));
               Generator generator = new Generator(YmlUtil.getLocation(numberMap + ".generator.emerald." + i, mapWorld), new ItemStack(Material.EMERALD), 30);
               generatorList.add(generator);
            }
            if (YmlUtil.getLocation(numberMap + ".generator.diamond." + i, mapWorld) != null){
                Generator generator = new Generator(YmlUtil.getLocation(numberMap + ".generator.diamond." + i, mapWorld), new ItemStack(Material.DIAMOND), 20);
                generatorList.add(generator);
            }
        }
        for (var t : activeTeam.entrySet()){
            generatorList.add(t.getValue().getGeneratorTeamIron());
            generatorList.add(t.getValue().getGeneratorTeamGold());
        }
    }
    public void unloadGame(){
        localMap.unload();
    }
}
