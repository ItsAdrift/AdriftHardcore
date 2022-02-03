package me.itsadrift.adrifthardcore.lives;

import me.itsadrift.adrifthardcore.AdriftHardcore;
import me.itsadrift.adrifthardcore.utils.RandArray;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class LifeRollAnimation {

    public String[] displays = new String[]{"&c1", "&e2", "&e3", "&a4", "&25"};

    private AdriftHardcore main;
    private Player player;
    private Consumer<Integer> livesCallback;

    private int lives;

    public LifeRollAnimation(AdriftHardcore main, Player player, Consumer<Integer> livesCallback) {
        this.main = main;
        this.player = player;
        this.livesCallback = livesCallback;

        lives = ThreadLocalRandom.current().nextInt(2, 5 + 1);

        new BukkitRunnable() {
            int runs = 0;
            @Override
            public void run() {
                if (runs == 7) {
                    cancel();
                    slow();
                    return;
                }
                player.sendTitle(colour(RandArray.randomFrom(displays)), "", 0, 6, 0);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5f, 1);

                runs++;
            }
        }.runTaskTimer(main, 0, 5);
    }

    private void slow() {
        new BukkitRunnable() {
            int runs = 0;
            @Override
            public void run() {
                if (runs == 3) {
                    cancel();
                    superSlow();
                    return;
                }
                player.sendTitle(colour(RandArray.randomFrom(displays)), "", 0, 11, 0);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5f, 1);

                runs++;
            }
        }.runTaskTimer(main, 0, 10);
    }

    private void superSlow() {
        new BukkitRunnable() {
            int runs = 0;
            @Override
            public void run() {
                if (runs == 1) {
                    cancel();
                    player.sendTitle(colour(displays[lives - 1]), "", 0, 25, 5);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5f, 1);
                    livesCallback.accept(lives);
                    return;
                }
                player.sendTitle(colour(RandArray.randomFrom(displays)), "", 0, 21, 0);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5f, 1);

                runs++;
            }
        }.runTaskTimer(main, 0, 15);
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
