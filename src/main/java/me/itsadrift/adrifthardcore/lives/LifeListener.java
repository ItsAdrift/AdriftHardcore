package me.itsadrift.adrifthardcore.lives;

import de.tr7zw.nbtapi.NBTItem;
import me.itsadrift.adrifthardcore.AdriftHardcore;
import me.itsadrift.adrifthardcore.api.HardcorePlayer;
import me.itsadrift.adrifthardcore.api.events.HardcorePlayerEliminatedEvent;
import me.itsadrift.adrifthardcore.api.events.LifeAddedEvent;
import me.itsadrift.adrifthardcore.api.events.LifeChanged;
import me.itsadrift.adrifthardcore.api.events.LifeRemovedEvent;
import me.itsadrift.adrifthardcore.utils.ModelData;
import me.itsadrift.adrifthardcore.utils.TotemAnimation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class LifeListener implements Listener {

    private AdriftHardcore main;
    public LifeListener(AdriftHardcore main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND)
            return;

        if (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) {
            if (new NBTItem(e.getPlayer().getInventory().getItemInMainHand()).getString("ahc").equalsIgnoreCase("life")) {
                main.getLifeManager().addLife(HardcorePlayer.getPlayer(e.getPlayer()), 1);
                e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        main.getLifeManager().tabHandler.updateScoreboard();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (HardcorePlayer.getPlayer(e.getPlayer()).reviving) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent e) {
        HardcorePlayer cache = HardcorePlayer.getPlayer(e.getEntity());
        main.getLifeManager().removeLife(cache, 1, true);

        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run() {
                e.getEntity().spigot().respawn();
                if (!cache.eliminated)
                    TotemAnimation.playTotemAnimation(e.getEntity(), ModelData.MINUS_ONE_LIFE.data);
            }
        }, 2);

    }

    @EventHandler
    public void onLifeAdded(LifeAddedEvent e) {
        // Play life received totem
        TotemAnimation.playTotemAnimation(e.getHardcorePlayer().getOwner(), ModelData.PLUS_ONE_LIFE.data);
    }

    @EventHandler
    public void onLifeRemoved(LifeRemovedEvent e) {
        if (e.getLifeChanged() == LifeChanged.DEATH_REMOVED)
            return;

        // Play life removed totem
        TotemAnimation.playTotemAnimation(e.getHardcorePlayer().getOwner(), ModelData.MINUS_ONE_LIFE.data);
    }

    @EventHandler
    public void onEliminated(HardcorePlayerEliminatedEvent e) {
        Bukkit.broadcastMessage(colour("&c&l[AdriftHardcore] &c" + e.getHardcorePlayer().getOfflineOwner().getName() + "&f has been &4&lELIMINATED!"));
    }


    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
