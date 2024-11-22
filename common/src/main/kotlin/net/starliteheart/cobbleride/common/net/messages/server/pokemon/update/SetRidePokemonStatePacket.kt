package net.starliteheart.cobbleride.common.net.messages.server.pokemon.update

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.util.readEnumConstant
import com.cobblemon.mod.common.util.writeEnumConstant
import net.minecraft.network.RegistryFriendlyByteBuf
import net.starliteheart.cobbleride.common.net.messages.RideState
import net.starliteheart.cobbleride.common.util.rideableResource
import java.util.*

class SetRidePokemonStatePacket(
    val pokemonUUID: UUID,
    val rideState: RideState,
    val bl: Boolean
) : NetworkPacket<SetRidePokemonStatePacket> {
    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(pokemonUUID)
        buffer.writeEnumConstant(rideState)
        buffer.writeBoolean(bl)
    }

    companion object {
        val ID = rideableResource("set_ride_state")
        fun decode(buffer: RegistryFriendlyByteBuf) = SetRidePokemonStatePacket(
            buffer.readUUID(), buffer.readEnumConstant(RideState::class.java), buffer.readBoolean()
        )
    }
}