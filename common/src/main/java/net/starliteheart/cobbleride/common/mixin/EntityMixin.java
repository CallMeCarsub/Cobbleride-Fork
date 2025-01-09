package net.starliteheart.cobbleride.common.mixin;

import net.minecraft.world.entity.Entity;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class)
public class EntityMixin {
    /*
        A dismount handler for ensuring that any attempts to dismount in midair are blocked if the feature is enabled.
     */
    @Inject(method = "removeVehicle", at = @At(value = "HEAD"), cancellable = true)
    private void addDismountHandler(CallbackInfo ci) {
        if (((Entity) (Object) this).getVehicle() instanceof RideablePokemonEntity pokemon && pokemon.isAlive()) {
            if (!pokemon.onGround() && pokemon.isFlying() && !CobbleRideMod.config.getGeneral().getCanDismountInMidair())
                ci.cancel();
        }
    }
}
