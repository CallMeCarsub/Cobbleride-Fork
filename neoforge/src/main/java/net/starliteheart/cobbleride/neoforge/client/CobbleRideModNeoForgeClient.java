package net.starliteheart.cobbleride.neoforge.client;

import kotlin.Unit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.client.gui.RideStaminaOverlay;
import net.starliteheart.cobbleride.common.client.keybind.CobbleRideKeyBinds;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync.GetRidePokemonPassengersPacket;

@Mod(value = CobbleRideMod.MOD_ID, dist = Dist.CLIENT)
public class CobbleRideModNeoForgeClient {
    public CobbleRideModNeoForgeClient(ModContainer container, IEventBus modBus) {
        // This will use NeoForge's ConfigurationScreen to display this mod's configs
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modBus.addListener(this::registerRideKeyBindings);
        NeoForge.EVENT_BUS.addListener(this::renderRideStaminaOverlay);
        NeoForge.EVENT_BUS.addListener(this::getRidePokemonPassengers);
    }

    private void registerRideKeyBindings(RegisterKeyMappingsEvent event) {
        CobbleRideKeyBinds.INSTANCE.register(registrar -> {
            event.register(registrar);
            return Unit.INSTANCE;
        });
    }

    private void renderRideStaminaOverlay(RenderGuiLayerEvent.Pre event) {
        if (event.getName() == VanillaGuiLayers.CHAT) {
            RideStaminaOverlay.render(event.getGuiGraphics());
        }
    }

    private void getRidePokemonPassengers(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof RideablePokemonEntity pokemon) {
            new GetRidePokemonPassengersPacket(pokemon.getId()).sendToServer();
        }
    }
}
