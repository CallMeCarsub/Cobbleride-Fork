package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokedex.DexAdditions;
import com.cobblemon.mod.common.api.pokedex.Dexes;
import com.cobblemon.mod.common.api.pokedex.def.PokedexDef;
import com.cobblemon.mod.common.api.pokedex.entry.DexEntries;
import net.minecraft.resources.ResourceLocation;
import net.starliteheart.cobbleride.common.api.pokedex.def.CondensedPokedexDef;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(value = DexAdditions.class, remap = false)
public abstract class DexAdditionsMixin {
    @Inject(method = "reload(Ljava/util/Map;)V", at = @At("HEAD"))
    public void returnCondensedPacketCodec(Map<ResourceLocation, DexAdditions.DexAddition> data, CallbackInfo ci) {
        data.forEach((key, value) -> {
            PokedexDef dexDef = Dexes.INSTANCE.getDexEntryMap().get(value.getDexId());
            if (dexDef instanceof CondensedPokedexDef condensedDef) {
                AtomicBoolean invalid = new AtomicBoolean(false);
                value.getEntries().forEach(entryKey -> {
                    if (!DexEntries.INSTANCE.getEntries().containsKey(entryKey)) {
                        invalid.set(true);
                        Cobblemon.LOGGER.error("Unable to apply dex addition {} as the entry {} does not exist", key, entryKey);
                    }
                });
                if (invalid.get()) {
                    return;
                }
                condensedDef.appendEntries(value.getEntries());
            }
        });
    }
}
