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
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderDebris extends Render<EntityDebris> {
	private static final BlockRendererDispatcher blockrenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

	public RenderDebris(RenderManager manager) {
        super(manager);
        shadowSize = 0.5F;
	}

	@Override
	public void doRender(EntityDebris debris, double x, double y, double z, float pitch, float yaw)
	{
		GL11.glEnable(GL11.GL_LIGHTING);
		if (debris.isDead) return;
		World world = debris.worldObj;
        IBlockState state = debris.blockState;
        BlockPos pos = debris.getPosition();
		if (state != null) {
            Block block = state.getBlock();
            GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			bindEntityTexture(debris);
			GL11.glDisable(GL11.GL_LIGHTING);
			Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();
			block.setBlockBoundsBasedOnState(world, pos);
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
            worldRenderer.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
			blockrenderer.renderBlock(state, pos, world, worldRenderer);
			tessellator.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDebris debris)
	{
		return TextureMap.locationBlocksTexture;
	}
}
