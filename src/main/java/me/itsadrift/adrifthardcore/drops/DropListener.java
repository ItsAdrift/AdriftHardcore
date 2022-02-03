package me.itsadrift.adrifthardcore.drops;

import me.itsadrift.adrifthardcore.AdriftHardcore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.common.value.qual.EnumVal;

import java.util.Random;

public class DropListener implements Listener {

    private AdriftHardcore main;
    public DropListener(AdriftHardcore main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            switch (e.getEntity().getType()) {
                case ENDER_DRAGON:
                    ItemStack a = main.fragmentItem.clone();
                    a.setAmount(9);
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), a);
                    break;
                case WITHER:
                    ItemStack b = main.fragmentItem.clone();
                    b.setAmount(5);
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), b);
                    break;
                case ELDER_GUARDIAN:
                    ItemStack i = main.fragmentItem.clone();
                    i.setAmount(3);
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), i);
                    break;
                default:
                    break;
            }

            if (main.getDropManager().dropChances.containsKey(e.getEntityType())) {
                Random random = new Random();
                int i = random.nextInt(100 + 1);
                System.out.println("I: " + i + "     Chance: " + main.getDropManager().dropChances.get(e.getEntityType()));
                if (i < main.getDropManager().dropChances.get(e.getEntityType())) {
                    // Drop Fragment
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), main.fragmentItem);
                }
            }
        }

    }

}
