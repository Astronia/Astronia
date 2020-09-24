package com.flash.astroniatimezone.game.player.data;

import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;

public enum StatisticType {

    CANE_MINED("caneMined", Material.SUGAR_CANE_BLOCK);

    @Getter private final String key;
    @Getter private final Material material;

    StatisticType(String key, Material material) {
        this.key = key;
        this.material = material;
    }

    public static StatisticType getByKey(String key) {
        return Arrays.stream(values())
                .filter((type) -> type.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    public static StatisticType getByMaterial(Material mat) {
        return Arrays.stream(values())
                .filter((type) -> type.getMaterial() == mat)
                .findFirst()
                .orElse(null);
    }
}
