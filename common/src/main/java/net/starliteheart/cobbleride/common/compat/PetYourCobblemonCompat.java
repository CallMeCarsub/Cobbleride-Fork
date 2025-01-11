package net.starliteheart.cobbleride.common.compat;

import net.minecraft.world.entity.player.Player;
import petyourcobblemon.network.PetyourcobblemonModVariables.MapVariables;

public class PetYourCobblemonCompat {
    public static boolean isInteractionModeEnabled(Player player) {
        return MapVariables.get(player.level()).isInteractionmode;
    }
}
