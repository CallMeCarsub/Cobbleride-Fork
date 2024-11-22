package net.starliteheart.cobbleride.mod.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.starliteheart.cobbleride.mod.common.CobbleRideMod;

public class CobbleRideModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(CobbleRideMod::registerCommands);
    }

}
