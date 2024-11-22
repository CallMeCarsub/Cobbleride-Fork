package net.starliteheart.cobbleride.mod.common.net.serverhandling.pokemon.sync

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.starliteheart.cobbleride.mod.common.net.messages.server.pokemon.sync.GetRidePokemonPassengersPacket

class GetRidePokemonPassengersHandler : ServerNetworkPacketHandler<GetRidePokemonPassengersPacket> {

    override fun handle(packet: GetRidePokemonPassengersPacket, server: MinecraftServer, player: ServerPlayer) {
        val entity = player.serverLevel().getEntity(packet.pokemonID)
        if (entity != null && entity.passengers.isNotEmpty())
            player.connection.send(ClientboundSetPassengersPacket(entity))
    }
}