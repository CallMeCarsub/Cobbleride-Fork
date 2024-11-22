package net.starliteheart.cobbleride.mod.common.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.starliteheart.cobbleride.mod.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Pokemon.class)
public abstract class PokemonMixin {

    @Redirect(
            method = "sendOut",
            at = @At(value = "NEW", target = "com/cobblemon/mod/common/entity/pokemon/PokemonEntity")
    )
    public PokemonEntity returnRideablePokemonEntity(Level world, Pokemon pokemon, EntityType<PokemonEntity> type, int i, DefaultConstructorMarker defaultConstructorMarker) {
        return new RideablePokemonEntity(world, pokemon);
    }
}