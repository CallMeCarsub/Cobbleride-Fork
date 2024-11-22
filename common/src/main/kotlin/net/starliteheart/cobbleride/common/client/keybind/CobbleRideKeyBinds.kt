package net.starliteheart.cobbleride.common.client.keybind

import com.cobblemon.mod.common.client.keybind.CobblemonKeyBinding
import com.cobblemon.mod.common.platform.events.PlatformEvents
import net.minecraft.client.KeyMapping
import net.starliteheart.cobbleride.common.client.keybind.keybinds.AscendBinding
import net.starliteheart.cobbleride.common.client.keybind.keybinds.DescendBinding
import net.starliteheart.cobbleride.common.client.keybind.keybinds.SprintBinding

object CobbleRideKeyBinds {
    private val keyBinds = arrayListOf<CobblemonKeyBinding>()

    init {
        PlatformEvents.CLIENT_TICK_POST.subscribe { onTick() }
    }

    val ASCEND = queue(AscendBinding)
    val DESCEND = queue(DescendBinding)
    val SPRINT = queue(SprintBinding)

    fun register(registrar: (KeyMapping) -> Unit) {
        keyBinds.forEach(registrar::invoke)
    }

    // Both Forge and Fabric recommend this as the method to detect clicks inside a game
    private fun onTick() {
        keyBinds.forEach(CobblemonKeyBinding::onTick)
    }

    private fun queue(keyBinding: CobblemonKeyBinding): KeyMapping {
        keyBinds.add(keyBinding)
        return keyBinding
    }

}