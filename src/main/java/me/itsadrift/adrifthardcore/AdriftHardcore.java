package me.itsadrift.adrifthardcore;

import de.tr7zw.nbtapi.NBTItem;
import me.itsadrift.adrifthardcore.drops.DropListener;
import me.itsadrift.adrifthardcore.drops.DropManager;
import me.itsadrift.adrifthardcore.lives.LifeCommand;
import me.itsadrift.adrifthardcore.lives.LifeListener;
import me.itsadrift.adrifthardcore.lives.LifeManager;
import me.itsadrift.adrifthardcore.utils.ModelData;
import net.minecraft.nbt.NBTTagInt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class AdriftHardcore extends JavaPlugin {

    private static AdriftHardcore instance;

    private LifeManager lifeManager;
    private DropManager dropManager;

    public ItemStack lifeItem;
    public ItemStack fragmentItem;

    public NamespacedKey recipeKey;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        lifeManager = new LifeManager(this);
        dropManager = new DropManager(this).loadSettings();

        getCommand("adrifthardcore").setExecutor(new AdriftHardcoreCommand(this));
        getCommand("life").setExecutor(new LifeCommand(this));

        Bukkit.getPluginManager().registerEvents(new LifeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DropListener(this), this);

        // Items & Recipes
        recipeKey = new NamespacedKey(this, "ahc_life");

        lifeItem = getLifeItem();
        fragmentItem = getFragmentItem();

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, lifeItem);
        recipe.shape("FFF", "FFF", "FFF");
        recipe.setIngredient('F', Material.DIAMOND_HOE);
        Bukkit.addRecipe(recipe);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.removeRecipe(recipeKey);

        lifeManager.eliminatedActionBarTask.cancel();

        lifeManager = null;
        instance = null;
    }

    private ItemStack getLifeItem() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(ModelData.LIFE.data);
        itemMeta.setDisplayName(colour("&c&lLife"));
        List<String> lore = new ArrayList<>();
        lore.add(colour("&7Right click to claim this life!"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("ahc", "life");
        return nbtItem.getItem();
    }

    private ItemStack getFragmentItem() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(ModelData.LIFE_FRAGMENT.data);
        itemMeta.setDisplayName(colour("&cLife Fragment"));
        List<String> lore = new ArrayList<>();
        lore.add(colour("&7Craft 9 of these into a whole life!"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("ahc", "fragment");
        return nbtItem.getItem();
    }

    public LifeManager getLifeManager() {
        return lifeManager;
    }

    public DropManager getDropManager() {
        return dropManager;
    }

    public static AdriftHardcore getInstance() {
        return instance;
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
