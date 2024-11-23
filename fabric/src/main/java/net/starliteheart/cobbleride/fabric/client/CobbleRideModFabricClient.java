package net.starliteheart.cobbleride.fabric.client;

import kotlin.Unit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.starliteheart.cobbleride.common.client.gui.RideStaminaOverlay;
import net.starliteheart.cobbleride.common.client.keybind.CobbleRideKeyBinds;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync.GetRidePokemonPassengersPacket;

public final class CobbleRideModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.

        CobbleRideKeyBinds.INSTANCE.register(registrar -> {
            KeyBindingHelper.registerKeyBinding(registrar);
            return Unit.INSTANCE;
        });

        HudRenderCallback.EVENT.register((matrixStack, delta) -> {
            RideStaminaOverlay.render(matrixStack);
        });

        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof RideablePokemonEntity pokemon) {
                new GetRidePokemonPassengersPacket(pokemon.getId()).sendToServer();
            }
        });
    }
}