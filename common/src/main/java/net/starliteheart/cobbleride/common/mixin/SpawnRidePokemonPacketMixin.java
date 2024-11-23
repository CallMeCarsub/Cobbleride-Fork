package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.mixin.invoker.ClientPlayNetworkHandlerInvoker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import net.starliteheart.cobbleride.common.net.messages.client.spawn.SpawnRidePokemonPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = SpawnRidePokemonPacket.class, remap = false)
public abstract class SpawnRidePokemonPacketMixin extends SpawnExtraDataEntityPacketMixin {
    @Shadow
    public abstract boolean checkType(Entity entity);

    @Shadow
    public abstract void applyData(RideablePokemonEntity entity);

    @Override
    public void replaceEntityDefinition(Minecraft client, Runnable runnable) {
        client.execute(() -> {
            LocalPlayer player = client.player;
            if (player == null) return;

            ClientLevel world = (ClientLevel) player.level();
            if (world == null) return;

            ClientboundAddEntityPacket vanillaSpawnPacket = cobbleride$getVSP();
            if (vanillaSpawnPacket == null) return;

            // This is a copy pasta of ClientPlayNetworkHandler#onEntitySpawn
            // This exists due to us needing to do everything it does except spawn the entity in the world.
            // We invoke applyData then we add the entity to the world.
            PacketUtils.ensureRunningOnSameThread(vanillaSpawnPacket, player.connection, client);
            RideablePokemonEntity entity = new RideablePokemonEntity(world);

            entity.recreateFromPacket(vanillaSpawnPacket);
            entity.setDeltaMovement(new Vec3(
                    vanillaSpawnPacket.getXa(),
                    vanillaSpawnPacket.getYa(),
                    vanillaSpawnPacket.getZa()
            ));

            // Cobblemon start
            if (this.checkType(entity)) {
                this.applyData(entity);
            }
            // Cobblemon end

            world.addEntity(entity);
            ((ClientPlayNetworkHandlerInvoker) player.connection).callPlaySpawnSound(entity);
        });
    }
}