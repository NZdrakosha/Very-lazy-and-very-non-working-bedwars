package me.drakosha.bedwars.shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuyItem {
    public static void playerBuyItem(Player player, ItemStack itemWitchBuy , ItemStack itemInPrice, int price){
        if (getAmountItem(player, itemInPrice, price) >= price){
            player.getInventory().removeItem(new ItemStack(itemInPrice.getType(), price));
            player.getInventory().addItem(itemWitchBuy);
        }
    }
    private static int getAmountItem(Player player, ItemStack itemStack, int needAmount){
        if (!player.getInventory().contains(itemStack.getType())) return 0;
        int amount = 0;
        for (ItemStack i : player.getInventory().getContents()){
            if (i.getType() == null || i.getType() != itemStack.getType()) continue;

            amount += i.getAmount();
            if (amount >= needAmount) return amount;
        }
        return amount;
    }
}
