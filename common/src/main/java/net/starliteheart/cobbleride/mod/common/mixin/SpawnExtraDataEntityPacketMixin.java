package net.starliteheart.cobbleride.mod.common.mixin;

import com.cobblemon.mod.common.net.messages.client.spawn.SpawnExtraDataEntityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpawnExtraDataEntityPacket.class)
public abstract class SpawnExtraDataEntityPacketMixin {

    @Final
    @Shadow
    private ClientboundAddEntityPacket vanillaSpawnPacket;

    @Unique
    protected ClientboundAddEntityPacket cobbleride$getVSP() {
        return vanillaSpawnPacket;
    }

    @Redirect(
            method = "spawnAndApply",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;execute(Ljava/lang/Runnable;)V")
    )
    public void replaceEntityDefinition(Minecraft client, Runnable runnable) {
        client.execute(runnable);
    }
}
