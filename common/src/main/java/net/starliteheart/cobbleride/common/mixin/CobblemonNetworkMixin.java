package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.CobblemonNetwork;
import com.cobblemon.mod.common.client.net.data.DataRegistrySyncPacketHandler;
import com.cobblemon.mod.common.net.PacketRegisterInfo;
import net.starliteheart.cobbleride.common.client.net.settings.SendServerSettingsHandler;
import net.starliteheart.cobbleride.common.client.net.spawn.SpawnRidePokemonHandler;
import net.starliteheart.cobbleride.common.net.messages.client.data.RideableSpeciesRegistrySyncPacket;
import net.starliteheart.cobbleride.common.net.messages.client.settings.SendServerSettingsPacket;
import net.starliteheart.cobbleride.common.net.messages.client.spawn.SpawnRidePokemonPacket;
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync.GetRidePokemonPassengersPacket;
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.update.DismountPokemonPacket;
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.update.SetRidePokemonExhaustPacket;
import net.starliteheart.cobbleride.common.net.serverhandling.pokemon.sync.GetRidePokemonPassengersHandler;
import net.starliteheart.cobbleride.common.net.serverhandling.pokemon.update.DismountPokemonHandler;
import net.starliteheart.cobbleride.common.net.serverhandling.pokemon.update.SetRidePokemonExhaustHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = CobblemonNetwork.class, remap = false)
public abstract class CobblemonNetworkMixin {
    /**
     * These injects add the packets required for the Ride Pokemon to function correctly. To consider, maybe these packets should be moved to a custom, personalized network manager made for the mod itself, so that it doesn't have to rely as strongly on the core Cobblemon network. (It might also allow for optional packet handling, if a client-optional approach is actually viable.)
     */
    @Inject(method = "generateS2CPacketInfoList", at = @At(value = "RETURN"), cancellable = true)
    private void addRideableS2CPacketHandlers(CallbackInfoReturnable<List<PacketRegisterInfo<?>>> cir) {
        List<PacketRegisterInfo<?>> list = cir.getReturnValue();
        list.add(new PacketRegisterInfo<>(SendServerSettingsPacket.Companion.getID(), SendServerSettingsPacket.Companion::decode, new SendServerSettingsHandler(), null));
        list.add(new PacketRegisterInfo<>(SpawnRidePokemonPacket.Companion.getID(), SpawnRidePokemonPacket.Companion::decode, new SpawnRidePokemonHandler(), null));
        list.add(new PacketRegisterInfo<>(RideableSpeciesRegistrySyncPacket.Companion.getID(), RideableSpeciesRegistrySyncPacket.Companion::decode, new DataRegistrySyncPacketHandler<>(), null));
        cir.setReturnValue(list);
    }

    @Inject(method = "generateC2SPacketInfoList", at = @At(value = "RETURN"), cancellable = true)
    private void addRideableC2SPacketHandlers(CallbackInfoReturnable<List<PacketRegisterInfo<?>>> cir) {
        List<PacketRegisterInfo<?>> list = cir.getReturnValue();
        list.add(new PacketRegisterInfo<>(SetRidePokemonExhaustPacket.Companion.getID(), SetRidePokemonExhaustPacket.Companion::decode, new SetRidePokemonExhaustHandler(), null));
        list.add(new PacketRegisterInfo<>(GetRidePokemonPassengersPacket.Companion.getID(), GetRidePokemonPassengersPacket.Companion::decode, new GetRidePokemonPassengersHandler(), null));
        list.add(new PacketRegisterInfo<>(DismountPokemonPacket.Companion.getID(), DismountPokemonPacket.Companion::decode, new DismountPokemonHandler(), null));
        cir.setReturnValue(list);
    }
}
