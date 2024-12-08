package net.starliteheart.cobbleride.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.player.Player;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;

public class CobbleRideModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CobbleRideMod.INSTANCE.initialize();

        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof Player player && player.getVehicle() instanceof RideablePokemonEntity) {
                player.removeVehicle();
            } else if (entity instanceof RideablePokemonEntity && !entity.getPassengers().isEmpty()) {
                entity.ejectPassengers();
            }
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            if (player.getVehicle() instanceof RideablePokemonEntity) {
                player.removeVehicle();
            }
        });
    }
}
