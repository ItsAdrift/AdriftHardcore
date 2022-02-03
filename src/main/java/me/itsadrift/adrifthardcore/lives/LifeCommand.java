package me.itsadrift.adrifthardcore.lives;

import me.itsadrift.adrifthardcore.AdriftHardcore;
import me.itsadrift.adrifthardcore.api.HardcorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LifeCommand implements CommandExecutor {

    private AdriftHardcore main;
    public LifeCommand(AdriftHardcore main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        HardcorePlayer hardcorePlayer = HardcorePlayer.getPlayer(player);

        if (args.length == 0) {
            String livesPlu = "lives";
            if (hardcorePlayer.lives == 1) {
                livesPlu = "life";
            }
            player.sendMessage(colour("&c&l[AdriftHardcore] &fYou have " + (hardcorePlayer.lives >= 5 ? "&2" : main.getLifeManager().colors[hardcorePlayer.lives]) + hardcorePlayer.lives + " &f" + livesPlu + "."));
            return false;
        } else if (args.length == 1) {
            if (Bukkit.getOfflinePlayer(args[0]) != null) {
                HardcorePlayer target = HardcorePlayer.getPlayer(Bukkit.getOfflinePlayer(args[0]));

                String livesPlu = "lives";
                if (target.lives == 1) {
                    livesPlu = "life";
                }
                player.sendMessage(colour("&c&l[AdriftHardcore] &c" + target.getOfflineOwner().getName() + "&f has " + (target.lives >= 5 ? "&2" : main.getLifeManager().colors[target.lives]) + target.lives + " &f" + livesPlu + "."));
            } else {
                player.sendMessage(colour("&c&l[AdriftHardcore] &fInvalid user. Either user does not exist or isn't online."));
            }

            return false;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("revive")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    HardcorePlayer target = HardcorePlayer.getPlayer(Bukkit.getPlayer(args[1]));
                    if (target.eliminated) {
                        if (hardcorePlayer.lives <= 1) {
                            player.sendMessage(colour("&c&l[AdriftHardcore] &fYou are on your last life and are unable to revive &c" + target.getOwner().getName() + "&f!"));
                            return false;
                        }

                        if (main.getLifeManager().revive(target, hardcorePlayer)) {
                            main.getLifeManager().removeLife(HardcorePlayer.getPlayer(player), 1, false);

                            player.sendMessage(colour("&c&l[AdriftHardcore] &fYou have revived &c" + target.getOwner().getName() + "&f for &c1 &flife!"));
                            target.getOwner().sendMessage(colour("&c&l[AdriftHardcore] &c" + target.getOwner().getName() + "&f, is &creviving &fyou!"));
                        }


                    } else {
                        player.sendMessage(colour("&c&l[AdriftHardcore] &fThis user has not been eliminated."));
                    }
                } else {
                    player.sendMessage(colour("&c&l[AdriftHardcore] &fInvalid user. Either user does not exist or isn't online."));
                }
                return false;
            } else if (args[0].equalsIgnoreCase("give")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    HardcorePlayer target = HardcorePlayer.getPlayer(Bukkit.getPlayer(args[1]));
                    if (target.getOwner().getUniqueId().equals(player.getUniqueId())) {
                        player.sendMessage(colour("&c&l[AdriftHardcore] &fWhy are you trying to give a life to yourself..."));
                        return false;
                    }
                    if (target.eliminated) {
                        player.sendMessage(colour("&c&l[AdriftHardcore] &fUse &c/life revive <name> &fto revive someone."));
                        return false;
                    }
                    if (hardcorePlayer.lives - 1 < 1) { // Make sure that the player actually has enough lives to give
                        player.sendMessage(colour("&c&l[AdriftHardcore] &fYou do not have enough lives to give &c1 life&f! You have &c" + hardcorePlayer.lives + "&f lives."));
                        return false;
                    }

                    if (main.getLifeManager().addLife(target, 1)) {
                        main.getLifeManager().removeLife(hardcorePlayer, 1, false);
                        String ammt = "life";
                        player.sendMessage(colour("&c&l[AdriftHardcore] &fYou have given &c" + target.getOwner().getName() + "&c 1 &f" + ammt + "."));
                        target.getOwner().sendMessage(colour("&c&l[AdriftHardcore] &c" + target.getOwner().getName() + "&f has given you &c1 &f" + ammt + "."));
                    }

                } else {
                    player.sendMessage(colour("&c&l[AdriftHardcore] &fInvalid user. Either user does not exist or isn't online."));
                }
                return false;
            }
        }

        player.sendMessage(colour("&c&l[AdriftHardcore] &fIncorrect usage. Use &c/life give <name> <amount> &for &c/live revive <name>&f."));

        return false;
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
