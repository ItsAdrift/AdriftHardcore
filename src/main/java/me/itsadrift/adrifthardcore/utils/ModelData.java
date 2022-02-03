package me.itsadrift.adrifthardcore.utils;

import org.bukkit.Material;

public enum ModelData {

    MINUS_ONE_LIFE(Material.TOTEM_OF_UNDYING, 1),
    PLUS_ONE_LIFE(Material.TOTEM_OF_UNDYING,2),
    LIFE(Material.DIAMOND_HOE, 1),
    LIFE_FRAGMENT(Material.DIAMOND_HOE, 2);


    public Material material;
    public int data;
    ModelData(Material mat, int data) {
        this.material = mat;
        this.data = data;
    }
}
