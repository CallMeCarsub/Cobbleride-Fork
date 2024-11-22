package net.starliteheart.cobbleride.mod.common.net.messages.client.data

import com.cobblemon.mod.common.net.messages.client.data.DataRegistrySyncPacket
import com.cobblemon.mod.common.util.readIdentifier
import com.cobblemon.mod.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.starliteheart.cobbleride.mod.common.CobbleRideMod
import net.starliteheart.cobbleride.mod.common.api.pokemon.RideablePokemonSpecies
import net.starliteheart.cobbleride.mod.common.pokemon.RideableSpecies
import net.starliteheart.cobbleride.mod.common.util.rideableResource

@Suppress("DEPRECATION")
class RideableSpeciesRegistrySyncPacket(species: Collection<RideableSpecies>) :
    DataRegistrySyncPacket<RideableSpecies, RideableSpeciesRegistrySyncPacket>(species) {
    override val id = ID

    // Manually redeclared here, to bypass internal check from external module
    internal fun decodeBuffer(buffer: RegistryFriendlyByteBuf) {
        val size = buffer.readInt()
        val newBuffer = RegistryFriendlyByteBuf(buffer.readBytes(size), buffer.registryAccess())
        this.buffer = newBuffer
    }

    override fun encodeEntry(buffer: RegistryFriendlyByteBuf, entry: RideableSpecies) {
        try {
            buffer.writeIdentifier(entry.identifier)
            entry.encode(buffer)
        } catch (e: Exception) {
            CobbleRideMod.LOGGER.error("Caught exception encoding the rideable species {}", entry.identifier, e)
        }
    }

    override fun decodeEntry(buffer: RegistryFriendlyByteBuf): RideableSpecies? {
        val identifier = buffer.readIdentifier()
        val species = RideableSpecies()
        species.identifier = identifier
        return try {
            species.decode(buffer)
            species
        } catch (e: Exception) {
            CobbleRideMod.LOGGER.error("Caught exception decoding the species {}", identifier, e)
            null
        }
    }

    override fun synchronizeDecoded(entries: Collection<RideableSpecies>) {
        RideablePokemonSpecies.reload(entries.associateBy { it.identifier })
    }

    companion object {
        val ID: ResourceLocation = rideableResource("species_sync")

        @JvmStatic
        fun decode(buffer: RegistryFriendlyByteBuf): RideableSpeciesRegistrySyncPacket =
            RideableSpeciesRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }

}