package net.starliteheart.cobbleride.common;

import com.cobblemon.mod.common.data.CobblemonDataProvider;
import net.starliteheart.cobbleride.common.api.pokemon.RideablePokemonSpecies;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CobbleRideMod {
    public static final String MOD_ID = "cobbleride";

    public static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        CobblemonDataProvider.INSTANCE.register(RideablePokemonSpecies.INSTANCE);
    }
}
