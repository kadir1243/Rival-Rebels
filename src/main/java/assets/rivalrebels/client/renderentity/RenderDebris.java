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

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import assets.rivalrebels.common.entity.EntityDebris;

public class RenderDebris extends Render<EntityDebris>
{
	private final RenderBlocks blockrenderer = new RenderBlocks();

	public RenderDebris(RenderManager manager)
	{
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
            int metadata = block.getMetaFromState(state);
            GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			bindEntityTexture(debris);
			GL11.glDisable(GL11.GL_LIGHTING);
			Tessellator tessellator = Tessellator.getInstance();
			blockrenderer.blockAccess = world;
			block.setBlockBoundsBasedOnState(world, pos);
			blockrenderer.setRenderBoundsFromBlock(block);
			int color = block.getBlockColor();
			float r = (color >> 16 & 255) / 255.0F;
			float g = (color >> 8 & 255) / 255.0F;
			float b = (color & 255) / 255.0F;
			tessellator.startDrawingQuads();
			tessellator.setBrightness(block.getMixedBrightnessForBlock(world, pos));
			tessellator.setColorOpaque_F(0.5f * r, 0.5f * g, 0.5f * b);
			blockrenderer.renderFaceYNeg(block, -0.5D, -0.5D, -0.5D, blockrenderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
			tessellator.setColorOpaque_F(r, g, b);
			blockrenderer.renderFaceYPos(block, -0.5D, -0.5D, -0.5D, blockrenderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
			tessellator.setColorOpaque_F(0.8f * r, 0.8f * g, 0.8f * b);
			blockrenderer.renderFaceZNeg(block, -0.5D, -0.5D, -0.5D, blockrenderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
			blockrenderer.renderFaceZPos(block, -0.5D, -0.5D, -0.5D, blockrenderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
			tessellator.setColorOpaque_F(0.6f * r, 0.6f * g, 0.6f * b);
			blockrenderer.renderFaceXNeg(block, -0.5D, -0.5D, -0.5D, blockrenderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
			blockrenderer.renderFaceXPos(block, -0.5D, -0.5D, -0.5D, blockrenderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
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
