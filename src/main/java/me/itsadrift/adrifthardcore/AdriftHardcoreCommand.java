package me.itsadrift.adrifthardcore;

import me.itsadrift.adrifthardcore.utils.TotemAnimation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdriftHardcoreCommand implements CommandExecutor {

    private AdriftHardcore main;
    public AdriftHardcoreCommand(AdriftHardcore main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args[0].equalsIgnoreCase("roll")) {
                if (args.length == 2) {
                    // has specified Player
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        main.getLifeManager().rollForPlayer(player);
                    }
                    sender.sendMessage(colour("&c&l[AdriftHardcore] &fYou have rolled lives for all players online!"));
                    main.getLifeManager().tabHandler.updateScoreboard();
                }
            }
        } else {
            Player player = (Player) sender;
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("test")) {
                    TotemAnimation.playTotemAnimation(player, Integer.parseInt(args[1]));
                } else if (args[0].equalsIgnoreCase("test1")) {
                    ItemStack i = new ItemStack(Material.DIAMOND_HOE);
                    i.getItemMeta().setCustomModelData(Integer.parseInt(args[1]));
                    player.getInventory().addItem(i);
                }
            }
        }

        return false;
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
