package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PokemonBattle.class)
public abstract class PokemonBattleMixin {
    /*
        In 1.6, Cobblemon forces Pokemon to recall after a battle if they have a raft, because they didn't have the clientside moving behaviour. I fixed this for Ride Pokemon (and by extension, all owned Pokemon), so this is unnecessary.
     */
    @Inject(
            method = "end",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/pokemon/Pokemon;tryRecallWithAnimation()V"
            ),
            remap = false,
            cancellable = true
    )
    public void preventRecalling(CallbackInfo ci) {
        ci.cancel();
    }
}