package me.itsadrift.adrifthardcore.lives;

import me.itsadrift.adrifthardcore.AdriftHardcore;
import me.itsadrift.adrifthardcore.api.HardcorePlayer;
import me.itsadrift.adrifthardcore.api.events.HardcorePlayerRevivedEvent;
import me.itsadrift.adrifthardcore.api.events.LifeAddedEvent;
import me.itsadrift.adrifthardcore.api.events.HardcorePlayerEliminatedEvent;
import me.itsadrift.adrifthardcore.api.events.LifeRemovedEvent;
import me.itsadrift.adrifthardcore.utils.runnables.EliminatedActionBarTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class LifeManager {

    public String[] displays = new String[]{"&40", "&c1", "&e2", "&e3", "&a4", "&25"};
    public String[] colors = new String[]{"&4","&c", "&e", "&e", "&a", "&2"};

    private AdriftHardcore main;
    public TabHandler tabHandler;

    public BukkitTask eliminatedActionBarTask;

    public LifeManager(AdriftHardcore main) {
        this.main = main;
        this.tabHandler = new TabHandler(this);

        eliminatedActionBarTask = new EliminatedActionBarTask().runTaskTimer(main, 0, 20);
    }

    public void save(HardcorePlayer cache) {
        main.getConfig().set("userData." + cache.getOfflineOwner().getUniqueId() + ".lives", cache.lives);
        main.getConfig().set("userData." + cache.getOfflineOwner().getUniqueId() + ".deaths", cache.deaths);
        main.getConfig().set("userData." + cache.getOfflineOwner().getUniqueId() + ".eliminated", cache.eliminated);
        main.saveConfig();
    }

    public void rollForPlayer(Player player) {
        new LifeRollAnimation(main, player, lives -> {
            HardcorePlayer cache = HardcorePlayer.getPlayer(player);
            cache.lives = lives;

            save(cache);

            Bukkit.broadcastMessage(colour("&c&l[AdriftHardcore] &fThe player &c" + player.getName() + "&f has " + displays[lives] + "&f lives!"));
        });
    }

    public boolean addLife(HardcorePlayer player, int amount) {
        LifeAddedEvent event = new LifeAddedEvent(player, amount);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            player.lives += event.getAmount();
        }

        save(player);
        tabHandler.updateScoreboard();

        return !event.isCancelled();
    }

    public boolean removeLife(HardcorePlayer player, int amount, boolean death) {
        LifeRemovedEvent event = new LifeRemovedEvent(player, amount, death);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            if (player.lives - event.getAmount() <= 0) {
                if (eliminate(player)) { // Make sure that the event isn't cancelled & that we should eliminate the player (returns false if cancelled)
                    player.lives = 0; // Just set their lives to 0 to avoid negative lives
                    player.eliminated = true;
                }

            } else {
                player.lives -= event.getAmount();
            }
            save(player);
            tabHandler.updateScoreboard();


        }

        return !event.isCancelled();
    }

    public boolean eliminate(HardcorePlayer player) {
        HardcorePlayerEliminatedEvent event = new HardcorePlayerEliminatedEvent(player);
        if (!event.isCancelled()) {
            player.getOwner().setGameMode(GameMode.SPECTATOR);
            player.getOwner().sendTitle(colour("&4&lEliminated!"), colour("&fYou can still be resurrected!"), 5, 40, 5);
        }
        return !event.isCancelled();
    }

    public boolean revive(HardcorePlayer player, HardcorePlayer reviver) {
        HardcorePlayerRevivedEvent event = new HardcorePlayerRevivedEvent(player, reviver);
        if (!event.isCancelled()) {
            player.lives = 1;
            save(player);

            Player p = player.getOwner();
            Player r = reviver.getOwner();

            p.teleport(r);
            player.reviving = true;

            new BukkitRunnable() {
                int c = 5;
                @Override
                public void run() {
                    if (c == 0) {
                        p.setHealth(20);
                        p.setFoodLevel(20);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendTitle(colour("&c&lRevived!"), colour("&fby &c" + r.getName()), 5, 20, 5);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5f, 1f);

                        player.eliminated = false;
                        player.reviving = false;

                        save(player);

                        cancel();
                        return;
                    }
                    p.sendTitle(colour("&c&lReviving in " + c + "s..."), "", 3, 21, 0);
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5f, 1f);
                    c--;
                }
            }.runTaskTimer(main, 0, 20);

        }

        return !event.isCancelled();
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
