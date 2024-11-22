package net.starliteheart.cobbleride.mod.neoforge;

import com.cobblemon.mod.common.data.CobblemonDataProvider;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.starliteheart.cobbleride.mod.common.CobbleRideMod;
import net.starliteheart.cobbleride.mod.common.api.pokemon.RideablePokemonSpecies;
import net.starliteheart.cobbleride.mod.common.config.CommonServerConfig;
import net.starliteheart.cobbleride.mod.common.entity.pokemon.RideablePokemonEntity;
import net.starliteheart.cobbleride.mod.neoforge.config.NeoForgeServerConfig;

@Mod(CobbleRideMod.MOD_ID)
public class CobbleRideModNeoForge {
    public CobbleRideModNeoForge(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, NeoForgeServerConfig.CONFIG_SPEC);
        NeoForge.EVENT_BUS.addListener(this::handleMidairDismounts);
        NeoForge.EVENT_BUS.addListener(this::handleRidePlayerLogouts);

        CobblemonDataProvider.INSTANCE.register(RideablePokemonSpecies.INSTANCE);
    }

    public void handleMidairDismounts(EntityMountEvent event) {
        if (event.isMounting()) return;
        if (!event.getEntityBeingMounted().isAlive()) return;
        if (!(event.getEntityBeingMounted() instanceof RideablePokemonEntity pokemon)) return;

        // All trainers are encouraged to buckle up when flying for their own safety!
        if (!pokemon.onGround() && pokemon.isFlying() && !CommonServerConfig.SERVER.canDismountInMidair)
            event.setCanceled(true);
    }

    private void handleRidePlayerLogouts(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player.getVehicle() instanceof RideablePokemonEntity)
            player.removeVehicle();
    }
}
