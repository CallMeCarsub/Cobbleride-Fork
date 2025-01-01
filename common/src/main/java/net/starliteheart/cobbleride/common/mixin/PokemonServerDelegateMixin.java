package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.entity.PlatformType;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.entity.pokemon.PokemonServerDelegate;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PokemonServerDelegate.class, remap = false)
public abstract class PokemonServerDelegateMixin<T> {
    @Shadow
    public PokemonEntity entity;

    @SuppressWarnings("unchecked")
    @Redirect(
            method = "updateTrackedValues", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/syncher/SynchedEntityData;set(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V"
    )
    )
    public void setIfRideableIsMoving(SynchedEntityData instance, EntityDataAccessor<T> arg, T object) {
        if (arg.equals(PokemonEntity.Companion.getMOVING()) && entity instanceof RideablePokemonEntity rideable && rideable.getControllingPassenger() != null && rideable.getControllingPassenger() instanceof Player player) {
            float x = player.xxa * 0.5f;
            float z = player.zza;
            if (z <= 0.0f) {
                z *= 0.25f;
            }
            Vec3 input = new Vec3(x, 0.0, z);
            boolean isRideableMoving = input.length() > 0.005F;
            boolean hasPlatform = rideable.getPlatform() != PlatformType.NONE;
            instance.set((EntityDataAccessor<Boolean>) arg, ((Boolean) object) || (!hasPlatform && isRideableMoving));
        } else {
            instance.set(arg, object);
        }
    }
}