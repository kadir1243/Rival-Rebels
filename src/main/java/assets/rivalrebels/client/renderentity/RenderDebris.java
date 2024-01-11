/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.common.entity.EntityDebris;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderDebris extends Render<EntityDebris> {
	public RenderDebris(RenderManager manager)
	{
        super(manager);
        this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(EntityDebris debris, double x, double y, double z, float pitch, float yaw) {
		if (debris.isDead) return;
        IBlockState state = debris.state;
        if (state == null || state.getRenderType() == EnumBlockRenderType.INVISIBLE) return; // Why ???
        GlStateManager.pushMatrix();
        bindEntityTexture(debris);
        GlStateManager.disableLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(debris));
        }

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        BlockPos blockpos = new BlockPos(debris.posX, debris.getEntityBoundingBox().maxY, debris.posZ);
        GlStateManager.translate((float)(x - (double)blockpos.getX() - 0.5D), (float)(y - (double)blockpos.getY()), (float)(z - (double)blockpos.getZ() - 0.5D));
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        dispatcher.getBlockModelRenderer().renderModel(debris.world, dispatcher.getModelForState(state), state, blockpos, buffer, false, MathHelper.getPositionRandom(debris.getPosition()));
        tessellator.draw();

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityDebris r)
	{
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
