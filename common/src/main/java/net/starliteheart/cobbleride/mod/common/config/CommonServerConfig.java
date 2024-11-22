package net.starliteheart.cobbleride.mod.common.config;

public class CommonServerConfig {
    public static class SERVER {
        public static double globalBaseSpeedModifier;
        public static double globalLandSpeedModifier;
        public static double globalWaterSpeedModifier;
        public static double globalAirSpeedModifier;
        public static double waterVerticalClimbSpeed;
        public static double airVerticalClimbSpeed;
        public static double rideSpeedLimit;

        public static boolean isWaterBreathingShared;
        public static boolean canDismountInMidair;
        public static boolean hasRotationWeight;

        public static boolean doesSpeedStatAffectSpeed;
        public static int minSpeedStatThreshold;
        public static int maxSpeedStatThreshold;
        public static double minSpeedStatModifier;
        public static double maxSpeedStatModifier;

        public static boolean canRidePokemonSprint;
        public static double rideSprintingSpeed;
        public static boolean canSprintInWater;
        public static boolean canSprintInAir;
        public static boolean canRidePokemonExhaust;
        public static int rideSprintMaxStamina;
        public static int rideSprintRecoveryTime;
        public static int rideSprintRecoveryDelay;
        public static double rideSprintExhaustionSpeed;
    }
}
