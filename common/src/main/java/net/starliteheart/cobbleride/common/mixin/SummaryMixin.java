package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.client.gui.summary.Summary;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.starliteheart.cobbleride.common.api.pokemon.RideablePokemonSpecies;
import net.starliteheart.cobbleride.common.pokemon.RideableSpecies;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.starliteheart.cobbleride.common.client.CobbleRideClientUtilsKt.blitRideIcon;
import static net.starliteheart.cobbleride.common.util.CobbleRideUtilsKt.rideableResource;

@Mixin(Summary.class)
public abstract class SummaryMixin extends Screen {
    protected SummaryMixin(Component arg) {
        super(arg);
    }

    @Shadow(remap = false)
    public Pokemon selectedPokemon;

    @Final
    @Shadow(remap = false)
    public static int BASE_WIDTH;

    @Final
    @Shadow(remap = false)
    public static int BASE_HEIGHT;

    @Final
    @Shadow(remap = false)
    private static float SCALE;

    @Inject(
        method = "render", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"
        )
    )
    public void displayRideIcon(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int x = (width - BASE_WIDTH) / 2;
        int y = (height - BASE_HEIGHT) / 2;
        PoseStack matrices = context.pose();

        RideableSpecies species = RideablePokemonSpecies.INSTANCE.getByName(selectedPokemon.getSpecies().showdownId());
        if (species != null) blitRideIcon(
                matrices,
                rideableResource("textures/gui/summary/ride-icon.png"),
                (x + 56) / SCALE,
                (y + 101) / SCALE,
                32,
                16,
                SCALE
        );
    }
}