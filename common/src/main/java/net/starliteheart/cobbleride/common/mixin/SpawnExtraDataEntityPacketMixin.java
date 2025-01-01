package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.net.messages.client.spawn.SpawnExtraDataEntityPacket;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SpawnExtraDataEntityPacket.class)
public abstract class SpawnExtraDataEntityPacketMixin {
    @Redirect(
            method = "spawnAndApply",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;execute(Ljava/lang/Runnable;)V")
    )
    public void replaceEntityDefinition(Minecraft client, Runnable runnable) {
        client.execute(runnable);
    }
}
