package net.starliteheart.cobbleride.mod.common.net.messages.client.pokemon.update

import com.cobblemon.mod.common.net.messages.client.pokemon.update.SingleUpdatePacket
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.util.readEnumConstant
import com.cobblemon.mod.common.util.writeEnumConstant
import net.minecraft.network.RegistryFriendlyByteBuf
import net.starliteheart.cobbleride.mod.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.mod.common.net.messages.RideState
import net.starliteheart.cobbleride.mod.common.util.rideableResource

class RidePokemonStateUpdatePacket(
    pokemon: () -> Pokemon,
    private val rideState: RideState,
    value: Boolean
) : SingleUpdatePacket<Boolean, RidePokemonStateUpdatePacket>(pokemon, value) {
    override val id = ID

    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        buffer.writeEnumConstant(rideState)
        buffer.writeBoolean(value)
    }

    override fun set(pokemon: Pokemon, value: Boolean) {
        val entity = (pokemon.entity as RideablePokemonEntity)
        when (this.rideState) {
            RideState.ASCENDING -> entity.isRideAscending = value
            RideState.DESCENDING -> entity.isRideDescending = value
            RideState.SPRINTING -> entity.isRideSprinting = value
        }
    }

    companion object {
        val ID = rideableResource("ride_state_update")
        fun decode(buffer: RegistryFriendlyByteBuf) = RidePokemonStateUpdatePacket(
            decodePokemon(buffer),
            buffer.readEnumConstant(RideState::class.java),
            buffer.readBoolean()
        )
    }

}
