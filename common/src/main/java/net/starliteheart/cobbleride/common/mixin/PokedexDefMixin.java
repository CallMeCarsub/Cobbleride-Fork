package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.api.pokedex.def.PokedexDef;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.starliteheart.cobbleride.common.api.pokedex.def.CondensedPokedexDef;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PokedexDef.Companion.class, remap = false)
public abstract class PokedexDefMixin {
    @Inject(method = "getPacketCodecById", at = @At(value = "HEAD"), cancellable = true)
    public void returnCondensedPacketCodec(ResourceLocation id, CallbackInfoReturnable<StreamCodec<ByteBuf, ? extends PokedexDef>> cir) {
        if (id.equals(CondensedPokedexDef.Companion.getID()))
            cir.setReturnValue(CondensedPokedexDef.Companion.getPACKET_CODEC());
    }

    @Inject(method = "getCodecById", at = @At(value = "HEAD"), cancellable = true)
    public void returnCondensedCodec(ResourceLocation id, CallbackInfoReturnable<MapCodec<? extends PokedexDef>> cir) {
        if (id.equals(CondensedPokedexDef.Companion.getID()))
            cir.setReturnValue(CondensedPokedexDef.Companion.getCODEC());
    }
}
