package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.client.keybind.keybinds.PartySendBinding;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.LocalPlayer;
import net.starliteheart.cobbleride.common.client.settings.ClientSettings;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = PartySendBinding.class)
public class PartySendBindingMixin {
    /*
        A dismount handler for ensuring that any attempts to dismount in midair are blocked if the feature is enabled.
     */
    @Inject(
            method = "onRelease",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/CobblemonNetwork;sendToServer(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V"
            ),
            remap = false,
            cancellable = true
    )
    private void handleCancelledMidairDismounts(CallbackInfo ci, @Local LocalPlayer player, @Local Pokemon pokemon) {
        if (pokemon.getEntity() instanceof RideablePokemonEntity rideable && Objects.equals(rideable.getOwnerUUID(), player.getUUID()) && rideable.isAlive()
                && player.getVehicle() == rideable && !rideable.onGround() && rideable.isFlying() && !ClientSettings.INSTANCE.getCanDismountInMidair()) {
            ci.cancel();
        }
    }
}
