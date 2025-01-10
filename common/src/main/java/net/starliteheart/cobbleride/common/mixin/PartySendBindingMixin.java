package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.CobblemonNetwork;
import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.client.keybind.keybinds.PartySendBinding;
import com.cobblemon.mod.common.net.messages.server.SendOutPokemonPacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.starliteheart.cobbleride.common.client.settings.ClientSettings;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PartySendBinding.class)
public class PartySendBindingMixin {
    /**
     * A dismount handler for ensuring that any attempts to dismount in midair are blocked if the feature is enabled.
     * We also apply this before checking anything else, including battle triggers. This is due to a problem that arises where we might try to enter a battle or perform other actions, but the Ride Pokemon might block the raytrace. Or, it doesn't, but then we have a problem where we cannot dismount while a battle is ongoing. The best solution that was decided upon was preventing any other actions with the R key until dismounted.
     */
    @Inject(
            method = "onRelease",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/client/CobblemonClient;getBattle()Lcom/cobblemon/mod/common/client/battle/ClientBattle;"
            ),
            remap = false,
            cancellable = true
    )
    private void prioritizeDismountActionWithMidairLogic(CallbackInfo ci, @Local LocalPlayer player) {
        int selectedSlot = CobblemonClient.INSTANCE.getStorage().getSelectedSlot();
        if (selectedSlot != -1 && Minecraft.getInstance().screen == null) {
            Pokemon pokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(selectedSlot);
            if (
                    player.getVehicle() instanceof RideablePokemonEntity mount && mount.isAlive()
                            && (!mount.isOwnedBy(player)
                            || (pokemon != null && mount.is(pokemon.getEntity()))
                    )
            ) {
                // If we aren't canceling, send a packet to queue the expected dismount action
                if (mount.onGround() || !mount.isFlying() || ClientSettings.INSTANCE.getCanDismountInMidair()) {
                    CobblemonNetwork.INSTANCE.sendToServer(new SendOutPokemonPacket(selectedSlot));
                }
                ci.cancel();
            }
        }
    }
}
