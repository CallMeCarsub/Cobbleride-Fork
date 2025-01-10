package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.client.keybind.CobblemonKeyBinds;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    /**
     * This redirect is required to change the translated key passed to the player. Ride Pokemon use a different keybind, so we have to make sure that they are receiving the correct input!
     */
    @Redirect(
            method = "handleSetEntityPassengersPacket", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"
    )
    )
    public MutableComponent displayRideIcon(String string, Object[] objects, @Local(ordinal = 0) Entity entity) {
        return Component.translatable(string, (entity instanceof RideablePokemonEntity)
                ? new Object[]{CobblemonKeyBinds.INSTANCE.getSEND_OUT_POKEMON().getTranslatedKeyMessage()}
                : objects);
    }
}