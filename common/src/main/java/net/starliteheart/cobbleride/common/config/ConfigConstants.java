package net.starliteheart.cobbleride.common.config;

public class ConfigConstants {
    public static class Speed {
        public static final double value = 1.0;
        public static final double min = 0.0;
        public static final double max = 5.0;
    }

    public static class Height {
        public static final double swim = 2.0;
        public static final double fly = 0.5;
        public static final double min = 0.0;
        public static final double max = 5.0;
    }

    public static class SpeedLimit {
        public static final double value = 0.0;
        public static final double min = 0.0;
        public static final double max = 4.2;
    }

    public static class Feature {
        public static final boolean sharesWaterBreathing = true;
        public static final boolean canDismountInAir = false;
    }

    public static class SpeedStat {
        public static final boolean active = true;

        public static class Stat {
            public static final int minStat = 20;
            public static final int maxStat = 400;
            public static final int min = 0;
            public static final int max = 500;
        }

        public static class Speed {
            public static final double minSpeed = 0.5;
            public static final double maxSpeed = 4.0;
            public static final double min = 0.0;
            public static final double max = 5.0;
        }
    }

    public static class Sprint {
        public static final boolean active = true;
        public static final boolean inWater = true;
        public static final boolean inAir = false;

        public static class Speed {
            public static final double value = 1.5;
            public static final double min = 1.0;
            public static final double max = 5.0;
        }

        public static class Exhaust {
            public static final boolean active = true;
            public static final double value = 0.5;
            public static final double min = 0.0;
            public static final double max = 1.0;
        }

        public static class Stamina {
            public static final int value = 200;
            public static final int min = 1;
            public static final int max = 6000;
        }

        public static class Recovery {
            public static final int value = 300;
            public static final int min = 1;
            public static final int max = 6000;
        }

        public static class Delay {
            public static final int value = 20;
            public static final int min = 0;
            public static final int max = 6000;
        }
    }

}
