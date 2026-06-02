package me.drakosha.bedwars.gui;

import me.drakosha.bedwars.game.Game;
import me.drakosha.bedwars.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CreateJoinGui {
    static GameManager gm = GameManager.getInstance();

    public static Inventory createInventory(Player player) {

        if (gm.getActiveGame().isEmpty()) {
            return Bukkit.createInventory(player, 54);
        }
        Inventory inventory = Bukkit.createInventory(player, 54, "select game");
        for (int i = 0; i <= inventory.getContents().length; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, setItem(gm.getActiveGame().get(i), i + 1) );
            }
            if (i + 1 >= gm.getActiveGame().size()) break;
        }
        return inventory;
    }

    private static ItemStack setItem(Game game, int numberGame){
        ItemStack itemStack = new ItemStack(Material.GLASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        itemMeta.setDisplayName("game N" + numberGame);
        lore.add("player in game " + game.getPlayerInGame().size());
        lore.add("status game " + game.getGameState().toString());

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        itemStack.setType(Material.GLASS);
        return itemStack;
    }

}
