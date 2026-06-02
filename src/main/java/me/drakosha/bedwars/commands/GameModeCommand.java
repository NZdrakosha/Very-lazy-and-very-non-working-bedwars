package me.drakosha.bedwars.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;


        if (strings[0].length() == 1){
            switch (strings[0]){
                case "0": player.setGameMode(GameMode.SURVIVAL); break;
                case "1": player.setGameMode(GameMode.CREATIVE); break;
                case "2": player.setGameMode(GameMode.ADVENTURE); break;
                case "3": player.setGameMode(GameMode.SPECTATOR); break;
            }
        }
        return false;
    }
}
