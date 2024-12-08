package net.starliteheart.cobbleride.fabric.mixin;

import net.minecraft.world.entity.Entity;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public class EntityMixin {
    @Shadow
    private Entity vehicle;

    @Inject(method = "removeVehicle", at = @At("HEAD"), cancellable = true)
    private void addDismountHandler(CallbackInfo ci) {
        if (vehicle instanceof RideablePokemonEntity pokemon) {
            if (!pokemon.onGround() && pokemon.isFlying() && !CobbleRideMod.config.getGeneral().getCanDismountInAir())
                ci.cancel();
        }
    }
}
