package net.starliteheart.cobbleride.mod.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.starliteheart.cobbleride.mod.common.CobbleRideMod;

@Mod(CobbleRideMod.MOD_ID)
public class CobbleRideModNeoForge {

    public CobbleRideModNeoForge() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommandRegistration(RegisterCommandsEvent event) {
        CobbleRideMod.registerCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }
}
