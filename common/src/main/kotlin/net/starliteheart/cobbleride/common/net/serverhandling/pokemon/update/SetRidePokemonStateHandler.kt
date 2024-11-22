package net.starliteheart.cobbleride.common.net.serverhandling.pokemon.update

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.common.net.messages.RideState
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.update.SetRidePokemonStatePacket

class SetRidePokemonStateHandler : ServerNetworkPacketHandler<SetRidePokemonStatePacket> {

    override fun handle(packet: SetRidePokemonStatePacket, server: MinecraftServer, player: ServerPlayer) {
        val rideEntity = player.vehicle
        if (rideEntity is RideablePokemonEntity && rideEntity.uuid == packet.pokemonUUID) {
            when (packet.rideState) {
                RideState.ASCENDING -> rideEntity.isRideAscending = packet.bl
                RideState.DESCENDING -> rideEntity.isRideDescending = packet.bl
                RideState.SPRINTING -> rideEntity.isRideSprinting = packet.bl
            }
        }
    }
}