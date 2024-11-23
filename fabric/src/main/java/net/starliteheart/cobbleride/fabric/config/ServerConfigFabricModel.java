package net.starliteheart.cobbleride.fabric.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.config.ConfigConstants.*;

@Modmenu(modId = CobbleRideMod.MOD_ID)
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Config(name = "cobbleride-server", wrapperName = "ServerConfigFabric")
public class ServerConfigFabricModel {
    @SectionHeader("general")
    @Hook
    @RangeConstraint(min = Speed.min, max = Speed.max)
    public double globalBaseSpeedModifier = Speed.value;
    @Hook
    @RangeConstraint(min = Speed.min, max = Speed.max)
    public double globalLandSpeedModifier = Speed.value;
    @Hook
    @RangeConstraint(min = Speed.min, max = Speed.max)
    public double globalWaterSpeedModifier = Speed.value;
    @Hook
    @RangeConstraint(min = Speed.min, max = Speed.max)
    public double globalAirSpeedModifier = Speed.value;
    @Hook
    @RangeConstraint(min = Height.min, max = Height.max)
    public double waterVerticalClimbSpeed = Height.swim;
    @Hook
    @RangeConstraint(min = Height.min, max = Height.max)
    public double airVerticalClimbSpeed = Height.fly;
    @Hook
    @RangeConstraint(min = SpeedLimit.min, max = SpeedLimit.max)
    public double rideSpeedLimit = SpeedLimit.value;
    @Hook
    public boolean isWaterBreathingShared = Feature.sharesWaterBreathing;
    @Hook
    public boolean canDismountInMidair = Feature.canDismountInAir;

    @SectionHeader("speedStat")
    @Hook
    public boolean doesSpeedStatAffectSpeed = SpeedStat.active;
    @Hook
    @RangeConstraint(min = SpeedStat.Stat.min, max = SpeedStat.Stat.max)
    public int minSpeedStatThreshold = SpeedStat.Stat.minStat;
    @Hook
    @RangeConstraint(min = SpeedStat.Stat.min, max = SpeedStat.Stat.max)
    public int maxSpeedStatThreshold = SpeedStat.Stat.maxStat;
    @Hook
    @RangeConstraint(min = SpeedStat.Speed.min, max = SpeedStat.Speed.max)
    public double minSpeedStatModifier = SpeedStat.Speed.minSpeed;
    @Hook
    @RangeConstraint(min = SpeedStat.Speed.min, max = SpeedStat.Speed.max)
    public double maxSpeedStatModifier = SpeedStat.Speed.maxSpeed;

    @SectionHeader("sprinting")
    @Hook
    public boolean canRidePokemonSprint = Sprint.active;
    @Hook
    @RangeConstraint(min = Sprint.Speed.min, max = Sprint.Speed.max)
    public double rideSprintingSpeed = Sprint.Speed.value;
    @Hook
    public boolean canSprintInWater = Sprint.inWater;
    @Hook
    public boolean canSprintInAir = Sprint.inAir;
    @Hook
    public boolean canRidePokemonExhaust = Sprint.Exhaust.active;
    @Hook
    @RangeConstraint(min = Sprint.Stamina.min, max = Sprint.Stamina.max)
    public int rideSprintMaxStamina = Sprint.Stamina.value;
    @Hook
    @RangeConstraint(min = Sprint.Recovery.min, max = Sprint.Recovery.max)
    public int rideSprintRecoveryTime = Sprint.Recovery.value;
    @Hook
    @RangeConstraint(min = Sprint.Delay.min, max = Sprint.Delay.max)
    public int rideSprintRecoveryDelay = Sprint.Delay.value;
    @Hook
    @RangeConstraint(min = Sprint.Exhaust.min, max = Sprint.Exhaust.max)
    public double rideSprintExhaustionSpeed = Sprint.Exhaust.value;
}
