package net.starliteheart.cobbleride.common

import com.cobblemon.mod.common.api.tags.CobblemonItemTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity

abstract class CobbleRideModImpl {
    /**
     * Implemented here, where it can be overridden or augmented as needed for compatibility reasons
     */
    open fun canInteractToMount(player: Player, hand: InteractionHand): Boolean {
        return !player.isShiftKeyDown && hand == InteractionHand.MAIN_HAND
                && !player.getItemInHand(hand).`is`(CobblemonItemTags.POKEDEX)
    }

    open fun shouldRenderStaminaBar(player: Player): Boolean {
        return if (player.vehicle is RideablePokemonEntity) {
            val mount = (player.vehicle as RideablePokemonEntity)
            mount.canSprint && mount.canExhaust && mount.isControlledByLocalInstance
        } else false
    }
}