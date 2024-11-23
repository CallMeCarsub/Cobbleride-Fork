package net.starliteheart.cobbleride.common.api.pokedex.def

import com.cobblemon.mod.common.api.pokedex.def.PokedexDef
import com.cobblemon.mod.common.api.pokedex.entry.DexEntries
import com.cobblemon.mod.common.api.pokedex.entry.PokedexEntry
import com.cobblemon.mod.common.net.IntSize
import com.cobblemon.mod.common.util.readIdentifier
import com.cobblemon.mod.common.util.readSizedInt
import com.cobblemon.mod.common.util.writeIdentifier
import com.cobblemon.mod.common.util.writeSizedInt
import com.google.common.collect.Lists
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.starliteheart.cobbleride.common.util.rideableResource

class CondensedPokedexDef(
    override val id: ResourceLocation
): PokedexDef()  {
    override val typeId = ID
    private val entries = mutableListOf<ResourceLocation>()
    var squash = true
        private set

    fun appendEntries(entries: List<ResourceLocation>) {
        this.entries.addAll(entries)
    }

    override fun getEntries(): List<PokedexEntry> {
        val entryList = entries.mapNotNull { DexEntries.entries[it] }
        if (!squash) return entryList

        val speciesToEntry = linkedMapOf<ResourceLocation, PokedexEntry>()
        entryList.forEach { entry ->
            val preExisting = speciesToEntry[entry.speciesId]
            if (preExisting != null) {
                speciesToEntry[preExisting.speciesId] = preExisting.combinedWith(entry)
            } else {
                speciesToEntry[entry.speciesId] = entry
            }
        }
        return speciesToEntry.values.toList()
    }

    override fun shouldSynchronize(other: PokedexDef) = true

    override fun decode(buffer: RegistryFriendlyByteBuf) {
        sortOrder = buffer.readSizedInt(IntSize.U_BYTE)
        val size = buffer.readInt()
        for (i in 0 until size) {
            entries.add(buffer.readIdentifier())
        }
    }

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeSizedInt(IntSize.U_BYTE, sortOrder)
        buffer.writeInt(entries.size)
        entries.forEach {
            buffer.writeIdentifier(it)
        }
    }

    companion object {
        val ID = rideableResource("condensed_pokedex_def")
        val CODEC: MapCodec<CondensedPokedexDef> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter { it.id },
                PrimitiveCodec.INT.fieldOf("sortOrder").forGetter { it.sortOrder },
                ResourceLocation.CODEC.listOf().fieldOf("entries").forGetter { it.entries },
                PrimitiveCodec.BOOL.fieldOf("squash").forGetter { it.squash }
            ).apply(instance) { id, sortOrder, entries, squash ->
                val result = CondensedPokedexDef(id)
                result.sortOrder = sortOrder
                result.entries.addAll(entries)
                result.squash = squash
                result
            }
        }

        val PACKET_CODEC: StreamCodec<ByteBuf, CondensedPokedexDef> = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, CondensedPokedexDef::id,
            ByteBufCodecs.INT, CondensedPokedexDef::sortOrder,
            ByteBufCodecs.collection(Lists::newArrayListWithCapacity, ResourceLocation.STREAM_CODEC), CondensedPokedexDef::entries
        ) { id, sortOrder, entries ->
            val result = CondensedPokedexDef(id)
            result.sortOrder = sortOrder
            result.entries.addAll(entries)
            result
        }
    }


}