package me.drakosha.bedwars.generator;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class Generator {
    private final ItemStack item;
    @Setter
    private int generatorCooldown;
    private int currentCooldown;
    private final Location generatorLocation;

    public Generator(Location generatorLocation, ItemStack item, int cooldown){
        this.generatorCooldown = cooldown;
        currentCooldown = cooldown;
        this.item = item;
        this.generatorLocation = generatorLocation;
    }
    public void tick(){
        currentCooldown--;
        if (currentCooldown == 0){
            spawnRes();
            currentCooldown = generatorCooldown;
        }

    }
    public void spawnRes(){
       generatorLocation.getWorld().dropItem(generatorLocation.clone(), item);
    }

    public void updateGenerator(){
        if (generatorCooldown - 2 > 1 ){
            generatorCooldown -= 2;
        }else {
            generatorCooldown = 1;
        }
    }


}
