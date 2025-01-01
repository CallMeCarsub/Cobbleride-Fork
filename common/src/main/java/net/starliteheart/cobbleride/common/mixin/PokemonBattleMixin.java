package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PokemonBattle.class, remap = false)
public abstract class PokemonBattleMixin {
    @Redirect(
            method = "end",
            at = @At(value = "INVOKE", target = "Lcom/cobblemon/mod/common/pokemon/Pokemon;tryRecallWithAnimation()V")
    )
    public void preventRecalling(Pokemon pokemon) {
    }
}