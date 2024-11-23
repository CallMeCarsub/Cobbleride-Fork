package net.starliteheart.cobbleride.neoforge;

import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.config.CobbleRideConfig;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import net.starliteheart.cobbleride.neoforge.config.ConfigNeoForge;

@Mod(CobbleRideMod.MOD_ID)
public class CobbleRideModNeoForge {
    public CobbleRideModNeoForge(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ConfigNeoForge.CONFIG_SPEC);
        NeoForge.EVENT_BUS.addListener(this::handleMidairDismounts);
        NeoForge.EVENT_BUS.addListener(this::handleRidePlayerLogouts);

        CobbleRideMod.init();
    }

    public void handleMidairDismounts(EntityMountEvent event) {
        if (event.isMounting()) return;
        if (!event.getEntityBeingMounted().isAlive()) return;
        if (!(event.getEntityBeingMounted() instanceof RideablePokemonEntity pokemon)) return;

        // All trainers are encouraged to buckle up when flying for their own safety!
        if (!pokemon.onGround() && pokemon.isFlying() && !CobbleRideConfig.SERVER.canDismountInMidair)
            event.setCanceled(true);
    }

    private void handleRidePlayerLogouts(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player.getVehicle() instanceof RideablePokemonEntity)
            player.removeVehicle();
    }
}
