package me.itsadrift.adrifthardcore.utils;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.Entity;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TotemAnimation {

    private static boolean playTotemAnimation(Player p) {
        try {
            EntityPlayer entityPlayer = ((CraftPlayer)p).getHandle();
            PacketPlayOutEntityStatus packetPlayOutEntityStatus = new PacketPlayOutEntityStatus((Entity)entityPlayer, (byte)35);
            PlayerConnection playerConnection = entityPlayer.b;
            playerConnection.a((Packet)packetPlayOutEntityStatus);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean playTotemAnimation(Player p, int customModelData) {
        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta meta = totem.getItemMeta();
        assert meta != null;
        meta.setCustomModelData(customModelData);
        totem.setItemMeta(meta);
        ItemStack hand = p.getInventory().getItemInMainHand();
        p.getInventory().setItemInMainHand(totem);
        playTotemAnimation(p);
        p.getInventory().setItemInMainHand(hand);
        return true;
    }

}
