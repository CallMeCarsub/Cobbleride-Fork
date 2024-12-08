package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.api.pokedex.entry.PokedexEntry;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.client.gui.pokedex.PokedexGUIConstants;
import com.cobblemon.mod.common.client.gui.pokedex.widgets.PokemonInfoWidget;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.starliteheart.cobbleride.common.api.pokemon.RideablePokemonSpecies;
import net.starliteheart.cobbleride.common.pokemon.RideableSpecies;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.starliteheart.cobbleride.common.client.CobbleRideClientUtilsKt.blitRideIcon;
import static net.starliteheart.cobbleride.common.util.CobbleRideUtilsKt.rideableResource;

@Mixin(value = PokemonInfoWidget.class, remap = false)
public abstract class PokemonInfoWidgetMixin {
    @Shadow
    public abstract PokedexEntry getCurrentEntry();

    @Shadow
    public abstract int getPY();

    @Shadow
    public abstract int getPX();

    @Inject(
            method = "renderWidget", at = @At(
            value = "INVOKE",
            target = "Lcom/cobblemon/mod/common/client/gui/pokedex/ScaledButton;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            ordinal = 6, shift = At.Shift.AFTER
    )
    )
    public void displayRideDexIcon(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        PoseStack matrices = context.pose();
        float scale = PokedexGUIConstants.SCALE;

        Species species = PokemonSpecies.INSTANCE.getByIdentifier(getCurrentEntry().getSpeciesId());
        if (species != null) {
            RideableSpecies rideableSpecies = RideablePokemonSpecies.INSTANCE.getByName(species.showdownId());
            if (rideableSpecies != null) blitRideIcon(
                    matrices,
                    rideableResource("textures/gui/pokedex/ride-dex-icon.png"),
                    (getPX() + 114) / scale,
                    (getPY() + 69) / scale,
                    44,
                    20,
                    scale
            );
        }
    }
}