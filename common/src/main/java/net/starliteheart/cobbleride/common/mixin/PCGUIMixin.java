package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.client.gui.pc.PCGUI;
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

@Mixin(value = PCGUI.class, remap = false)
public abstract class PCGUIMixin extends Screen {
    protected PCGUIMixin(Component arg) {
        super(arg);
    }

    @Shadow
    private Pokemon previewPokemon;

    @Final
    @Shadow
    public static int BASE_WIDTH;

    @Final
    @Shadow
    public static int BASE_HEIGHT;

    @Final
    @Shadow
    public static float SCALE;

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

        if (previewPokemon != null) {
            RideableSpecies species = RideablePokemonSpecies.INSTANCE.getByName(previewPokemon.getSpecies().showdownId());
            if (species != null) blitRideIcon(
                    matrices,
                    rideableResource("textures/gui/summary/ride-icon.png"),
                    (x + 56) / SCALE,
                    (y + 95.5) / SCALE,
                    32,
                    16,
                    SCALE
            );
        }
    }
}