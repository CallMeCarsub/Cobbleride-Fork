package net.starliteheart.cobbleride.mod.common.client.keybind.keybinds

import com.cobblemon.mod.common.CobblemonNetwork
import com.cobblemon.mod.common.client.keybind.CobblemonBlockingKeyBinding
import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.Minecraft
import net.starliteheart.cobbleride.mod.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.mod.common.net.messages.RideState
import net.starliteheart.cobbleride.mod.common.net.messages.server.pokemon.update.SetRidePokemonStatePacket

object AscendBinding : CobblemonBlockingKeyBinding(
    "key.cobbleride.ascend",
    InputConstants.Type.KEYSYM,
    InputConstants.KEY_SPACE,
    "key.cobbleride.categories.cobbleride"
) {
    override fun onPress() {
        val vehicleEntity = Minecraft.getInstance().player?.vehicle
        if (vehicleEntity is RideablePokemonEntity) {
            CobblemonNetwork.sendToServer(
                SetRidePokemonStatePacket(
                    vehicleEntity.uuid,
                    RideState.ASCENDING,
                    true
                )
            )
        }
    }

    override fun onRelease() {
        val vehicleEntity = Minecraft.getInstance().player?.vehicle
        if (vehicleEntity is RideablePokemonEntity) {
            CobblemonNetwork.sendToServer(
                SetRidePokemonStatePacket(
                    vehicleEntity.uuid,
                    RideState.ASCENDING,
                    false
                )
            )
        }
    }
}