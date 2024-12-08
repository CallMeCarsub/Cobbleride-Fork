package net.starliteheart.cobbleride.common.client.gui

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiGraphics
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.common.util.rideableResource

class RideStaminaOverlay : Gui(Minecraft.getInstance()) {

    companion object {
        @JvmStatic
        fun render(context: GuiGraphics) {
            val minecraft = Minecraft.getInstance()

            // Do not render if any other screen is open, or if gui should be hidden
            if (minecraft.screen != null || minecraft.options.hideGui) {
                return
            }

            // Do not render if we are not riding a Rideable Pokemon
            val player = minecraft.player
            if (player == null || player.vehicle == null || player.vehicle !is RideablePokemonEntity) {
                return
            }

            val ridePokemon = player.vehicle as RideablePokemonEntity
            // Do not render if either sprinting is disabled or exhaustion mechanics are disabled
            if (!ridePokemon.canSprint || !ridePokemon.canExhaust) {
                return
            }

            minecraft.profiler.push("staminaBar")
            val f = ridePokemon.sprintStaminaScale
            val j = (f * 183.0f).toInt()
            val k = context.guiHeight() - 32 + 3
            val l = context.guiWidth() / 2 - 91
            RenderSystem.enableBlend()
            context.blitSprite(rideableResource("hud/stamina_bar_background"), l, k, 182, 5)
            if (ridePokemon.isExhausted) {
                context.blitSprite(rideableResource("hud/stamina_bar_cooldown"), l, k, 182, 5)
            } else if (j > 0) {
                context.blitSprite(
                    rideableResource("hud/stamina_bar_progress"),
                    182,
                    5,
                    0,
                    0,
                    l,
                    k,
                    j,
                    5
                )
            }

            RenderSystem.disableBlend()
            minecraft.profiler.pop()
        }
    }

}