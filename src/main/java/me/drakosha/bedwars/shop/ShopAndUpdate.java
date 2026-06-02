package me.drakosha.bedwars.shop;

import me.drakosha.bedwars.Bedwars;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;

public class ShopAndUpdate {

    public static void createVillagerShop(Location shopLocation){
        setVillagerProperties(shopLocation, "shop", "shop_npc");
    }

    public static void createVillagerUpdate(Location shopLocation){
        setVillagerProperties(shopLocation, "Update", "update_npc");
    }

    private static void setVillagerProperties(Location shopLocation, String customName, String meta){
        Villager villager = (Villager) shopLocation.getWorld().spawnEntity(shopLocation, EntityType.VILLAGER);
        villager.setCustomName(customName);
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setGravity(false);
        villager.setCanPickupItems(false);
        villager.setMetadata(meta, new FixedMetadataValue(Bedwars.getInstance(), true));
    }
}
