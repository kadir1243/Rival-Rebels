package io.github.kadir1243.rivalrebels.mixin.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiGraphics.class)
@OnlyIn(Dist.CLIENT)
public interface GuiGraphicsAccessor {
    @Invoker(value = "innerBlit", remap = false)
    void blit(ResourceLocation atlasLocation, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV);

    @Invoker(value = "innerBlit", remap = false)
    void blit(ResourceLocation atlasLocation, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV, float red, float green, float blue, float alpha);

}
