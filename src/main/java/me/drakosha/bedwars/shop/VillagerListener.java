package me.drakosha.bedwars.shop;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class VillagerListener implements Listener {

    @EventHandler
    public void playerInteractEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getTitle().equals("shop")){
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.WOOL){
                Player player = (Player) event.getWhoClicked();
                BuyItem.playerBuyItem(player, new ItemStack(event.getCurrentItem().getType(), 16), new ItemStack(Material.IRON_INGOT), 1);

            }
        }

    }


    @EventHandler
    public void inventoryClickEvent(PlayerInteractEntityEvent event){
        if (event.getRightClicked().hasMetadata("shop_npc")){
            Inventory i = Bukkit.createInventory(event.getPlayer(), 9   , "shop" );
            i.setItem(0, new ItemStack(Material.WOOL, 16));
            event.getPlayer().openInventory(i);
        }
        if (event.getRightClicked().hasMetadata("update_npc")){
            Inventory i = Bukkit.createInventory(event.getPlayer(), 9   , "Test" );
            event.getPlayer().openInventory(i);
        }
    }

    @EventHandler
    public void openInventoryEvent(InventoryOpenEvent event){
        if(event.getInventory().getType() == InventoryType.MERCHANT) event.setCancelled(true);
    }
}
