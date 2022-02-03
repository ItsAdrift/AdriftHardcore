package me.itsadrift.adrifthardcore.drops;

import me.itsadrift.adrifthardcore.AdriftHardcore;
import net.minecraft.world.entity.EntityTypes;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class DropManager {

    private AdriftHardcore main;
    public DropManager(AdriftHardcore main) {
        this.main = main;
    }

    public HashMap<EntityType, Integer> dropChances = new HashMap<>();

    public DropManager loadSettings() {
        for (String key : main.getConfig().getConfigurationSection("drops").getKeys(false)) {
            dropChances.put(EntityType.valueOf(key), main.getConfig().getInt("drops." + key));
        }

        return this;
    }

}
