package me.itsadrift.adrifthardcore.lives;

import me.itsadrift.adrifthardcore.api.HardcorePlayer;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TablistFormatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class TabHandler {

    private LifeManager lifeManager;
    private TablistFormatManager tablistFormatManager;

    boolean a = false;
    public TabHandler(LifeManager lifeManager) {
        this.lifeManager = lifeManager;
        if (TabAPI.getInstance() == null) {
            Bukkit.getLogger().log(Level.SEVERE, "TABAPI Instance is null");
            a = true;
            return;
        }
        this.tablistFormatManager = TabAPI.getInstance().getTablistFormatManager();
    }

    public void updateScoreboard() {
        if (a)
            return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            HardcorePlayer hc = HardcorePlayer.getPlayer(player);
            tablistFormatManager.setPrefix(TabAPI.getInstance().getPlayer(player.getUniqueId()), hc.lives >= 5 ? "&2" : lifeManager.colors[hc.lives]);
        }
    }

}
