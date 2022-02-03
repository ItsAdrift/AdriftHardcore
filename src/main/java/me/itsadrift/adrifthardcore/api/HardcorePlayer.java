package me.itsadrift.adrifthardcore.api;

import me.itsadrift.adrifthardcore.AdriftHardcore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author RuthlessJailer
 */
public class HardcorePlayer {

    private static final Map<UUID, HardcorePlayer> CACHE_MAP = new HashMap<>();

    public static HardcorePlayer getPlayer(final OfflinePlayer player) {
        return CACHE_MAP.computeIfAbsent(player.getUniqueId(), HardcorePlayer::new);
    }

    public int lives;
    public int deaths;

    public boolean eliminated = false;
    public boolean reviving = false;

    private final UUID uuid;

    private HardcorePlayer(final UUID uuid) {
        this.uuid = uuid;
        this.lives = AdriftHardcore.getInstance().getConfig().getInt("userData." + uuid.toString() + ".lives");
        this.deaths = AdriftHardcore.getInstance().getConfig().getInt("userData." + uuid.toString() + ".deaths");
        this.eliminated = AdriftHardcore.getInstance().getConfig().getBoolean("userData." + uuid.toString() + ".eliminated");

    }

    public Player getOwner(){ return Bukkit.getPlayer(uuid); }
    public OfflinePlayer getOfflineOwner(){ return Bukkit.getOfflinePlayer(uuid); }

}
