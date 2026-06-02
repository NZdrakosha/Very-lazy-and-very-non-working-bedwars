package me.drakosha.bedwars.commands;

import me.drakosha.bedwars.game.GameManager;
import me.drakosha.bedwars.gui.CreateJoinGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class SupCommand implements CommandExecutor {
    Random random = new Random();
    GameManager gm = GameManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (strings[0].equals("j")){
            player.openInventory(CreateJoinGui.createInventory(player));
            return true;
        }
        if (strings[0].equals("create")){
            gm.createGame(random.nextInt(1) + 1);
        }
        return false;
    }
}
