package me.itsadrift.adrifthardcore.utils.runnables;

import me.itsadrift.adrifthardcore.api.HardcorePlayer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EliminatedActionBarTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            HardcorePlayer hp = HardcorePlayer.getPlayer(player);
            if (hp.eliminated) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(colour("&c&lEliminated!")));
            }
        }
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
