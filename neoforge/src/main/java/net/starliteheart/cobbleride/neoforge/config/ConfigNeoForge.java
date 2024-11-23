package net.starliteheart.cobbleride.neoforge.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.config.CobbleRideConfig.SERVER;
import net.starliteheart.cobbleride.common.config.ConfigConstants.*;

@EventBusSubscriber(modid = CobbleRideMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigNeoForge {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue GLOBAL_BASE_SPEED_MODIFIER = BUILDER
            .translation("cobbleride.configuration.general.globalBaseSpeedModifier")
            .defineInRange("general.globalBaseSpeedModifier", Speed.value, Speed.min, Speed.max);

    private static final ModConfigSpec.DoubleValue GLOBAL_LAND_SPEED_MODIFIER = BUILDER
            .translation("cobbleride.configuration.general.globalLandSpeedModifier")
            .defineInRange("general.globalLandSpeedModifier", Speed.value, Speed.min, Speed.max);

    private static final ModConfigSpec.DoubleValue GLOBAL_WATER_SPEED_MODIFIER = BUILDER
            .translation("cobbleride.configuration.general.globalWaterSpeedModifier")
            .defineInRange("general.globalWaterSpeedModifier", Speed.value, Speed.min, Speed.max);

    private static final ModConfigSpec.DoubleValue GLOBAL_AIR_SPEED_MODIFIER = BUILDER
            .translation("cobbleride.configuration.general.globalAirSpeedModifier")
            .defineInRange("general.globalAirSpeedModifier", Speed.value, Speed.min, Speed.max);

    private static final ModConfigSpec.DoubleValue WATER_VERTICAL_CLIMB_SPEED = BUILDER
            .translation("cobbleride.configuration.general.waterVerticalClimbSpeed")
            .defineInRange("general.waterVerticalClimbSpeed", Height.swim, Height.min, Height.max);

    private static final ModConfigSpec.DoubleValue AIR_VERTICAL_CLIMB_SPEED = BUILDER
            .translation("cobbleride.configuration.general.airVerticalClimbSpeed")
            .defineInRange("general.airVerticalClimbSpeed", Height.fly, Height.min, Height.max);

    private static final ModConfigSpec.DoubleValue RIDE_SPEED_LIMIT = BUILDER
            .translation("cobbleride.configuration.general.rideSpeedLimit")
            .defineInRange("general.rideSpeedLimit", SpeedLimit.value, SpeedLimit.min, SpeedLimit.max);

    private static final ModConfigSpec.BooleanValue IS_WATER_BREATHING_SHARED = BUILDER
            .translation("cobbleride.configuration.general.isWaterBreathingShared")
            .define("general.isWaterBreathingShared", Feature.sharesWaterBreathing);

    private static final ModConfigSpec.BooleanValue CAN_DISMOUNT_IN_MIDAIR = BUILDER
            .translation("cobbleride.configuration.general.canDismountInMidair")
            .define("general.canDismountInMidair", Feature.canDismountInAir);

    private static final ModConfigSpec.BooleanValue DOES_SPEED_STAT_AFFECT_SPEED = BUILDER
            .translation("cobbleride.configuration.speedStat.doesSpeedStatAffectSpeed")
            .define("speedStat.doesSpeedStatAffectSpeed", SpeedStat.active);

    private static final ModConfigSpec.IntValue MIN_SPEED_STAT_THRESHOLD = BUILDER
            .translation("cobbleride.configuration.speedStat.minSpeedStatThreshold")
            .defineInRange("speedStat.minSpeedStatThreshold", SpeedStat.Stat.minStat, SpeedStat.Stat.min, SpeedStat.Stat.max);

    private static final ModConfigSpec.IntValue MAX_SPEED_STAT_THRESHOLD = BUILDER
            .translation("cobbleride.configuration.speedStat.maxSpeedStatThreshold")
            .defineInRange("speedStat.maxSpeedStatThreshold", SpeedStat.Stat.maxStat, SpeedStat.Stat.min, SpeedStat.Stat.max);

    private static final ModConfigSpec.DoubleValue MIN_SPEED_STAT_MODIFIER = BUILDER
            .translation("cobbleride.configuration.speedStat.minSpeedStatModifier")
            .defineInRange("speedStat.minSpeedStatModifier", SpeedStat.Speed.minSpeed, SpeedStat.Speed.min, SpeedStat.Speed.max);

    private static final ModConfigSpec.DoubleValue MAX_SPEED_STAT_MODIFIER = BUILDER
            .translation("cobbleride.configuration.speedStat.maxSpeedStatModifier")
            .defineInRange("speedStat.maxSpeedStatModifier", SpeedStat.Speed.maxSpeed, SpeedStat.Speed.min, SpeedStat.Speed.max);

    private static final ModConfigSpec.BooleanValue CAN_RIDE_POKEMON_SPRINT = BUILDER
            .translation("cobbleride.configuration.sprinting.canRidePokemonSprint")
            .define("sprinting.canRidePokemonSprint", Sprint.active);

    private static final ModConfigSpec.DoubleValue RIDE_SPRINTING_SPEED = BUILDER
            .translation("cobbleride.configuration.sprinting.rideSprintingSpeed")
            .defineInRange("sprinting.rideSprintingSpeed", Sprint.Speed.value, Sprint.Speed.min, Sprint.Speed.max);

    private static final ModConfigSpec.BooleanValue CAN_SPRINT_IN_WATER = BUILDER
            .translation("cobbleride.configuration.sprinting.canSprintInWater")
            .define("sprinting.canSprintInWater", Sprint.inWater);

    private static final ModConfigSpec.BooleanValue CAN_SPRINT_IN_AIR = BUILDER
            .translation("cobbleride.configuration.sprinting.canSprintInAir")
            .define("sprinting.canSprintInAir", Sprint.inAir);

    private static final ModConfigSpec.BooleanValue CAN_RIDE_POKEMON_EXHAUST = BUILDER
            .translation("cobbleride.configuration.sprinting.canRidePokemonExhaust")
            .define("sprinting.canRidePokemonExhaust", Sprint.Exhaust.active);

    private static final ModConfigSpec.IntValue RIDE_SPRINT_MAX_STAMINA = BUILDER
            .translation("cobbleride.configuration.sprinting.rideSprintMaxStamina")
            .defineInRange("sprinting.rideSprintMaxStamina", Sprint.Stamina.value, Sprint.Stamina.min, Sprint.Stamina.max);

    private static final ModConfigSpec.IntValue RIDE_SPRINT_RECOVERY_TIME = BUILDER
            .translation("cobbleride.configuration.sprinting.rideSprintRecoveryTime")
            .defineInRange("sprinting.rideSprintRecoveryTime", Sprint.Recovery.value, Sprint.Recovery.min, Sprint.Recovery.max);

    private static final ModConfigSpec.IntValue RIDE_SPRINT_RECOVERY_DELAY = BUILDER
            .translation("cobbleride.configuration.sprinting.rideSprintRecoveryDelay")
            .defineInRange("sprinting.rideSprintRecoveryDelay", Sprint.Delay.value, Sprint.Delay.min, Sprint.Delay.max);

    private static final ModConfigSpec.DoubleValue RIDE_SPRINT_EXHAUSTION_SPEED = BUILDER
            .translation("cobbleride.configuration.sprinting.rideSprintExhaustionSpeed")
            .defineInRange("sprinting.rideSprintExhaustionSpeed", Sprint.Exhaust.value, Sprint.Exhaust.min, Sprint.Exhaust.max);

    public static final ModConfigSpec CONFIG_SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Aborts load if config is not loaded
        if(event.getConfig().getLoadedConfig() == null) return;

        SERVER.globalBaseSpeedModifier = GLOBAL_BASE_SPEED_MODIFIER.get();
        SERVER.globalLandSpeedModifier = GLOBAL_LAND_SPEED_MODIFIER.get();
        SERVER.globalWaterSpeedModifier = GLOBAL_WATER_SPEED_MODIFIER.get();
        SERVER.globalAirSpeedModifier = GLOBAL_AIR_SPEED_MODIFIER.get();
        SERVER.waterVerticalClimbSpeed = WATER_VERTICAL_CLIMB_SPEED.get();
        SERVER.airVerticalClimbSpeed = AIR_VERTICAL_CLIMB_SPEED.get();
        SERVER.rideSpeedLimit = RIDE_SPEED_LIMIT.get();
        SERVER.isWaterBreathingShared = IS_WATER_BREATHING_SHARED.get();
        SERVER.canDismountInMidair = CAN_DISMOUNT_IN_MIDAIR.get();

        SERVER.doesSpeedStatAffectSpeed = DOES_SPEED_STAT_AFFECT_SPEED.get();
        SERVER.minSpeedStatThreshold = MIN_SPEED_STAT_THRESHOLD.get();
        SERVER.maxSpeedStatThreshold = Math.max(MIN_SPEED_STAT_THRESHOLD.get(), MAX_SPEED_STAT_THRESHOLD.get());
        SERVER.minSpeedStatModifier = MIN_SPEED_STAT_MODIFIER.get();
        SERVER.maxSpeedStatModifier = Math.max(MIN_SPEED_STAT_MODIFIER.get(), MAX_SPEED_STAT_MODIFIER.get());

        SERVER.canRidePokemonSprint = CAN_RIDE_POKEMON_SPRINT.get();
        SERVER.rideSprintingSpeed = RIDE_SPRINTING_SPEED.get();
        SERVER.canSprintInWater = CAN_SPRINT_IN_WATER.get();
        SERVER.canSprintInAir = CAN_SPRINT_IN_AIR.get();
        SERVER.canRidePokemonExhaust = CAN_RIDE_POKEMON_EXHAUST.get();
        SERVER.rideSprintMaxStamina = RIDE_SPRINT_MAX_STAMINA.get();
        SERVER.rideSprintRecoveryTime = RIDE_SPRINT_RECOVERY_TIME.get();
        SERVER.rideSprintRecoveryDelay = RIDE_SPRINT_RECOVERY_DELAY.get();
        SERVER.rideSprintExhaustionSpeed = RIDE_SPRINT_EXHAUSTION_SPEED.get();
    }
}
