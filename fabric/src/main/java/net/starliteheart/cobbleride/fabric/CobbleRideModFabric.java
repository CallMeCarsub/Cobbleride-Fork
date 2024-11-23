package net.starliteheart.cobbleride.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.player.Player;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.config.CobbleRideConfig.SERVER;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import net.starliteheart.cobbleride.fabric.config.ServerConfigFabric;

public class CobbleRideModFabric implements ModInitializer {
    public static final ServerConfigFabric CONFIG = ServerConfigFabric.createAndLoad();

    @Override
    public void onInitialize() {
        CobbleRideMod.init();
        subscribeToConfigHooks();

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

    public void subscribeToConfigHooks() {
        CONFIG.subscribeToGlobalBaseSpeedModifier((value) -> SERVER.globalBaseSpeedModifier = value);
        CONFIG.subscribeToGlobalLandSpeedModifier((value) -> SERVER.globalLandSpeedModifier = value);
        CONFIG.subscribeToGlobalWaterSpeedModifier((value) -> SERVER.globalWaterSpeedModifier = value);
        CONFIG.subscribeToGlobalAirSpeedModifier((value) -> SERVER.globalAirSpeedModifier = value);
        CONFIG.subscribeToWaterVerticalClimbSpeed((value) -> SERVER.waterVerticalClimbSpeed = value);
        CONFIG.subscribeToAirVerticalClimbSpeed((value) -> SERVER.airVerticalClimbSpeed = value);
        CONFIG.subscribeToRideSpeedLimit((value) -> SERVER.rideSpeedLimit = value);

        CONFIG.subscribeToIsWaterBreathingShared((value) -> SERVER.isWaterBreathingShared = value);
        CONFIG.subscribeToCanDismountInMidair((value) -> SERVER.canDismountInMidair = value);

        CONFIG.subscribeToDoesSpeedStatAffectSpeed((value) -> SERVER.doesSpeedStatAffectSpeed = value);
        CONFIG.subscribeToMinSpeedStatThreshold((value) -> {
            SERVER.minSpeedStatThreshold = value;
            SERVER.maxSpeedStatThreshold = Math.max(value, CONFIG.maxSpeedStatThreshold());
        });
        CONFIG.subscribeToMaxSpeedStatThreshold((value) -> SERVER.maxSpeedStatThreshold = Math.max(value, CONFIG.minSpeedStatThreshold()));
        CONFIG.subscribeToMinSpeedStatModifier((value) -> {
            SERVER.minSpeedStatModifier = value;
            SERVER.maxSpeedStatModifier = Math.max(value, CONFIG.maxSpeedStatModifier());
        });
        CONFIG.subscribeToMaxSpeedStatModifier((value) -> SERVER.maxSpeedStatModifier = Math.max(value, CONFIG.minSpeedStatModifier()));

        CONFIG.subscribeToCanRidePokemonSprint((value) -> SERVER.canRidePokemonSprint = value);
        CONFIG.subscribeToRideSprintingSpeed((value) -> SERVER.rideSprintingSpeed = value);
        CONFIG.subscribeToCanSprintInWater((value) -> SERVER.canSprintInWater = value);
        CONFIG.subscribeToCanSprintInAir((value) -> SERVER.canSprintInAir = value);
        CONFIG.subscribeToCanRidePokemonExhaust((value) -> SERVER.canRidePokemonExhaust = value);
        CONFIG.subscribeToRideSprintMaxStamina((value) -> SERVER.rideSprintMaxStamina = value);
        CONFIG.subscribeToRideSprintRecoveryTime((value) -> SERVER.rideSprintRecoveryTime = value);
        CONFIG.subscribeToRideSprintRecoveryDelay((value) -> SERVER.rideSprintRecoveryDelay = value);
        CONFIG.subscribeToRideSprintExhaustionSpeed((value) -> SERVER.rideSprintExhaustionSpeed = value);
    }
}
