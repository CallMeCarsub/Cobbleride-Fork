package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.CobblemonNetwork;
import com.cobblemon.mod.common.client.net.data.DataRegistrySyncPacketHandler;
import com.cobblemon.mod.common.client.net.pokemon.update.PokemonUpdatePacketHandler;
import com.cobblemon.mod.common.client.net.spawn.SpawnExtraDataEntityHandler;
import com.cobblemon.mod.common.net.PacketRegisterInfo;
import net.starliteheart.cobbleride.common.net.messages.client.data.RideableSpeciesRegistrySyncPacket;
import net.starliteheart.cobbleride.common.net.messages.client.pokemon.update.RidePokemonStateUpdatePacket;
import net.starliteheart.cobbleride.common.net.messages.client.spawn.SpawnRidePokemonPacket;
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync.GetRidePokemonPassengersPacket;
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.update.SetRidePokemonStatePacket;
import net.starliteheart.cobbleride.common.net.serverhandling.pokemon.sync.GetRidePokemonPassengersHandler;
import net.starliteheart.cobbleride.common.net.serverhandling.pokemon.update.SetRidePokemonStateHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = CobblemonNetwork.class, remap = false)
public abstract class CobblemonNetworkMixin {
    @Inject(method = "generateS2CPacketInfoList", at = @At(value = "RETURN"), cancellable = true)
    public void addRideableS2CPacketHandlers(CallbackInfoReturnable<List<PacketRegisterInfo<?>>> cir) {
        List<PacketRegisterInfo<?>> list = cir.getReturnValue();
        list.add(new PacketRegisterInfo<>(SpawnRidePokemonPacket.Companion.getID(), SpawnRidePokemonPacket.Companion::decode, new SpawnExtraDataEntityHandler<>(), null));
        list.add(new PacketRegisterInfo<>(RidePokemonStateUpdatePacket.Companion.getID(), RidePokemonStateUpdatePacket.Companion::decode, new PokemonUpdatePacketHandler<>(), null));
        list.add(new PacketRegisterInfo<>(RideableSpeciesRegistrySyncPacket.Companion.getID(), RideableSpeciesRegistrySyncPacket.Companion::decode, new DataRegistrySyncPacketHandler<>(), null));
        cir.setReturnValue(list);
    }

    @Inject(method = "generateC2SPacketInfoList", at = @At(value = "RETURN"), cancellable = true)
    public void addRideableC2SPacketHandlers(CallbackInfoReturnable<List<PacketRegisterInfo<?>>> cir) {
        List<PacketRegisterInfo<?>> list = cir.getReturnValue();
        list.add(new PacketRegisterInfo<>(SetRidePokemonStatePacket.Companion.getID(), SetRidePokemonStatePacket.Companion::decode, new SetRidePokemonStateHandler(), null));
        list.add(new PacketRegisterInfo<>(GetRidePokemonPassengersPacket.Companion.getID(), GetRidePokemonPassengersPacket.Companion::decode, new GetRidePokemonPassengersHandler(), null));
        cir.setReturnValue(list);
    }
}
