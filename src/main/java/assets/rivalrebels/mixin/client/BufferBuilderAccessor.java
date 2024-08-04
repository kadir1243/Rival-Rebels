package assets.rivalrebels.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BufferBuilder.class)
@Environment(EnvType.CLIENT)
public interface BufferBuilderAccessor {
    @Accessor
    VertexFormat.Mode getMode();
}
