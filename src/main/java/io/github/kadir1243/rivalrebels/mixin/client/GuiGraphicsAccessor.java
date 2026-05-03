package io.github.kadir1243.rivalrebels.mixin.client;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiGraphicsExtractor.class)
@OnlyIn(Dist.CLIENT)
public interface GuiGraphicsAccessor {
    @Invoker(value = "innerBlit", remap = false)
    void blit(RenderPipeline function, Identifier atlasLocation, int x1, int x2, int y1, int y2, float minU, float maxU, float minV, float maxV, int color);
}
