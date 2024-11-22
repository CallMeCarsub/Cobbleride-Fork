package net.starliteheart.cobbleride.mod.neoforge.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.starliteheart.cobbleride.mod.common.CobbleRideMod;
import net.starliteheart.cobbleride.mod.common.config.CommonServerConfig.SERVER;

@EventBusSubscriber(modid = CobbleRideMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue GLOBAL_BASE_SPEED_MODIFIER = BUILDER
            .comment("Base speed multiplier for all mounted Pokemon.")
            .defineInRange("general.globalBaseSpeedModifier", 1.0, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue GLOBAL_LAND_SPEED_MODIFIER = BUILDER
            .comment("Land speed multiplier for all mounted Pokemon.")
            .defineInRange("general.globalLandSpeedModifier", 1.0, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue GLOBAL_WATER_SPEED_MODIFIER = BUILDER
            .comment("Water speed multiplier for all mounted Pokemon.")
            .defineInRange("general.globalWaterSpeedModifier", 1.0, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue GLOBAL_AIR_SPEED_MODIFIER = BUILDER
            .comment("Air speed multiplier for all mounted Pokemon.")
            .defineInRange("general.globalAirSpeedModifier", 1.0, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue WATER_VERTICAL_CLIMB_SPEED = BUILDER
            .comment("Speed at which qualified Ride Pokemon will ascend and descend while diving.")
            .defineInRange("general.waterVerticalClimbSpeed", 2.0, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue AIR_VERTICAL_CLIMB_SPEED = BUILDER
            .comment("Speed at which qualified Ride Pokemon will ascend and descend while flying.")
            .defineInRange("general.airVerticalClimbSpeed", 0.5, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue RIDE_SPEED_LIMIT = BUILDER
            .comment("Max speed attribute allowed for Ride Pokemon. This option may be useful for server owners, who may wish to restrict how quickly players can move around the server to minimize chunk loading and server load. A value of 2.1 restricts Ride speed to ~20 blocks/sec. This option is disabled if set to 0.")
            .defineInRange("general.rideSpeedLimit", 0.0, 0, 4.0);

    private static final ModConfigSpec.BooleanValue IS_WATER_BREATHING_SHARED = BUILDER
            .comment("Should Ride Pokemon share their water breathing with the rider?")
            .define("general.isWaterBreathingShared", true);

    private static final ModConfigSpec.BooleanValue CAN_DISMOUNT_IN_MIDAIR = BUILDER
            .comment("Can Riders dismount in midair? Setting this to false will not prevent manual dismount via recall, but riders are reminded that they are still suspect to the effects of gravity.")
            .define("general.canDismountInMidair", false);

    private static final ModConfigSpec.BooleanValue HAS_ROTATION_WEIGHT = BUILDER
            .comment("When enabled, Ride Pokemon will turn more slowly when turning to match the player's rotation, adding a bit of realistic weight to how they move. Disabling this will instead have Ride Pokemon snap immediately to the player's looking direction, similar to horses.")
            .define("general.hasRotationWeight", true);

    private static final ModConfigSpec.BooleanValue DOES_SPEED_STAT_AFFECT_SPEED = BUILDER
            .comment("Should a Pokemon's Speed stat influence its ride speed?")
            .define("speedStat.doesSpeedStatAffectSpeed", true);

    private static final ModConfigSpec.IntValue MIN_SPEED_STAT_THRESHOLD = BUILDER
            .comment("At and below this Speed stat value, Pokemon will move at the slowest speed.")
            .defineInRange("speedStat.minSpeedStatThreshold", 20, 0, 500);

    private static final ModConfigSpec.IntValue MAX_SPEED_STAT_THRESHOLD = BUILDER
            .comment("At and above this Speed stat value, Pokemon will move at the fastest speed. NOTE: If this value is set below the minimum, the value will be handled as if equal to the minimum.")
            .defineInRange("speedStat.maxSpeedStatThreshold", 400, 0, 500);

    private static final ModConfigSpec.DoubleValue MIN_SPEED_STAT_MODIFIER = BUILDER
            .comment("The speed multiplier for Ride Pokemon at the minimum Speed stat threshold.")
            .defineInRange("speedStat.minSpeedStatModifier", 0.5, 0.0, 5.0);

    private static final ModConfigSpec.DoubleValue MAX_SPEED_STAT_MODIFIER = BUILDER
            .comment("The speed multiplier for Ride Pokemon at the maximum Speed stat threshold. NOTE: If this value is set below the minimum, the value will be handled as if equal to the minimum.")
            .defineInRange("speedStat.maxSpeedStatModifier", 4.0, 0.0, 5.0);

    private static final ModConfigSpec.BooleanValue CAN_RIDE_POKEMON_SPRINT = BUILDER
            .comment("Is sprinting enabled for Ride Pokemon?")
            .define("sprinting.canRidePokemonSprint", true);

    private static final ModConfigSpec.DoubleValue RIDE_SPRINT_SPEED = BUILDER
            .comment("Speed multiplier for Ride Pokemon while sprinting.")
            .defineInRange("sprinting.rideSprintSpeed", 1.5, 1, Double.MAX_VALUE);

    private static final ModConfigSpec.BooleanValue CAN_SPRINT_IN_WATER = BUILDER
            .comment("Should Ride Pokemon be allowed to sprint in water?")
            .define("sprinting.canSprintInWater", true);

    private static final ModConfigSpec.BooleanValue CAN_SPRINT_IN_AIR = BUILDER
            .comment("Should Ride Pokemon be allowed to sprint while flying?")
            .define("sprinting.canSprintInAir", false);

    private static final ModConfigSpec.BooleanValue RIDE_SPRINT_EXHAUSTION = BUILDER
            .comment("Should Ride Pokemon become exhausted if they sprint for too long?")
            .define("sprinting.rideSprintExhaustion", true);

    private static final ModConfigSpec.IntValue RIDE_SPRINT_MAX_STAMINA = BUILDER
            .comment("How many ticks does it take to fully deplete a Ride Pokemon's stamina?")
            .defineInRange("sprinting.rideSprintMaxStamina", 200, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue RIDE_SPRINT_RECOVERY_TIME = BUILDER
            .comment("How many ticks does it take for a Ride Pokemon to fully recover its stamina from zero?")
            .defineInRange("sprinting.rideSprintRecoveryTime", 300, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue RIDE_SPRINT_RECOVERY_DELAY = BUILDER
            .comment("How many ticks must pass before stamina begins to recover?")
            .defineInRange("sprinting.rideSprintRecoveryDelay", 20, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue RIDE_SPRINT_EXHAUSTION_SPEED = BUILDER
            .comment("Speed multiplier for exhausted Ride Pokemon.")
            .defineInRange("sprinting.rideSprintExhaustedSpeed", 0.5, 0.0, 1.0);

    public static final ModConfigSpec CONFIG_SPEC = BUILDER.build();

//    public static double globalBaseSpeedModifier;
//    public static double globalLandSpeedModifier;
//    public static double globalWaterSpeedModifier;
//    public static double globalAirSpeedModifier;
//    public static double waterVerticalClimbSpeed;
//    public static double airVerticalClimbSpeed;
//    public static double rideSpeedLimit;
//
//    public static boolean isWaterBreathingShared;
//    public static boolean canDismountInMidair;
//    public static boolean hasRotationWeight;
//
//    public static boolean doesSpeedStatAffectSpeed;
//    public static int minSpeedStatThreshold;
//    public static int maxSpeedStatThreshold;
//    public static double minSpeedStatModifier;
//    public static double maxSpeedStatModifier;
//
//    public static boolean canRidePokemonSprint;
//    public static double rideSprintingSpeed;
//    public static boolean canSprintInWater;
//    public static boolean canSprintInAir;
//    public static boolean canRidePokemonExhaust;
//    public static int rideSprintMaxStamina;
//    public static int rideSprintRecoveryTime;
//    public static int rideSprintRecoveryDelay;
//    public static double rideSprintExhaustionSpeed;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        SERVER.globalBaseSpeedModifier = GLOBAL_BASE_SPEED_MODIFIER.get();
        SERVER.globalLandSpeedModifier = GLOBAL_LAND_SPEED_MODIFIER.get();
        SERVER.globalWaterSpeedModifier = GLOBAL_WATER_SPEED_MODIFIER.get();
        SERVER.globalAirSpeedModifier = GLOBAL_AIR_SPEED_MODIFIER.get();
        SERVER.waterVerticalClimbSpeed = WATER_VERTICAL_CLIMB_SPEED.get();
        SERVER.airVerticalClimbSpeed = AIR_VERTICAL_CLIMB_SPEED.get();
        SERVER.rideSpeedLimit = RIDE_SPEED_LIMIT.get();

        SERVER.isWaterBreathingShared = IS_WATER_BREATHING_SHARED.get();
        SERVER.canDismountInMidair = CAN_DISMOUNT_IN_MIDAIR.get();
        SERVER.hasRotationWeight = HAS_ROTATION_WEIGHT.get();

        SERVER.doesSpeedStatAffectSpeed = DOES_SPEED_STAT_AFFECT_SPEED.get();
        SERVER.minSpeedStatThreshold = MIN_SPEED_STAT_THRESHOLD.get();
        SERVER.maxSpeedStatThreshold = Math.max(MIN_SPEED_STAT_THRESHOLD.get(), MAX_SPEED_STAT_THRESHOLD.get());
        SERVER.minSpeedStatModifier = MIN_SPEED_STAT_MODIFIER.get();
        SERVER.maxSpeedStatModifier = Math.max(MIN_SPEED_STAT_MODIFIER.get(), MAX_SPEED_STAT_MODIFIER.get());

        SERVER.canRidePokemonSprint = CAN_RIDE_POKEMON_SPRINT.get();
        SERVER.rideSprintingSpeed = RIDE_SPRINT_SPEED.get();
        SERVER.canSprintInWater = CAN_SPRINT_IN_WATER.get();
        SERVER.canSprintInAir = CAN_SPRINT_IN_AIR.get();
        SERVER.canRidePokemonExhaust = RIDE_SPRINT_EXHAUSTION.get();
        SERVER.rideSprintMaxStamina = RIDE_SPRINT_MAX_STAMINA.get();
        SERVER.rideSprintRecoveryTime = RIDE_SPRINT_RECOVERY_TIME.get();
        SERVER.rideSprintRecoveryDelay = RIDE_SPRINT_RECOVERY_DELAY.get();
        SERVER.rideSprintExhaustionSpeed = RIDE_SPRINT_EXHAUSTION_SPEED.get();
    }
}
