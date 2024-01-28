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
package assets.rivalrebels.client.tileentityrender;

import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.common.block.machine.BlockForceFieldNode;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import assets.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class TileEntityForceFieldNodeRenderer implements BlockEntityRenderer<TileEntityForceFieldNode> {
	public static int				frames	= 28;
	public static int[]			id		= new int[frames];
	RenderHelper	model;

	public TileEntityForceFieldNodeRenderer(BlockEntityRendererFactory.Context context) {
        model = new RenderHelper();
		id = genTexture(28, 28, frames);
	}

    @Override
    public void render(TileEntityForceFieldNode entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.pInR <= 0) return;

		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);

        int meta = entity.getCachedState().get(BlockForceFieldNode.META);
        if (meta == 2)
		{
			matrices.multiply(new Quaternion(90, 0, 1, 0));
		}

		if (meta == 3)
		{
			matrices.multiply(new Quaternion(-90, 0, 1, 0));
		}

		if (meta == 4)
		{
			matrices.multiply(new Quaternion(180, 0, 1, 0));
		}

		RenderSystem.bindTexture(id[(int) ((getTime() / 100) % frames)]);
		RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
		matrices.multiply(new Quaternion(90, 0.0F, 1.0F, 0.0F));
		matrices.translate(0, 0, 0.5f);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
		vertexConsumer.vertex(-0.0625f, 3.5f, 0f).texture(0, 0).next();
		vertexConsumer.vertex(-0.0625f, -3.5f, 0f).texture(0, 1).next();
		vertexConsumer.vertex(-0.0625f, -3.5f, 35f).texture(5, 1).next();
		vertexConsumer.vertex(-0.0625f, 3.5f, 35f).texture(5, 0).next();
		vertexConsumer.vertex(0.0625f, -3.5f, 0f).texture(0, 1).next();
		vertexConsumer.vertex(0.0625f, 3.5f, 0f).texture(0, 0).next();
		vertexConsumer.vertex(0.0625f, 3.5f, 35f).texture(5, 0).next();
		vertexConsumer.vertex(0.0625f, -3.5f, 35f).texture(5, 1).next();

		matrices.pop();
	}

    private static final Random random = new Random();

	private int[] genTexture(int xs, int zs, int ys)
	{
		int[] ids = new int[ys];
		RivalRebelsCellularNoise.refresh3D(random);
		int size = xs * zs * 4;
		byte red = (byte) 0xBB;
		byte grn = (byte) 0x88;
		byte blu = (byte) 0xFF;
		for (int i = 0; i < ys; i++)
		{
			ByteBuffer bb = BufferUtils.createByteBuffer(size);
			bb.order(ByteOrder.nativeOrder());
			for (int x = 0; x < xs; x++)
			{
				for (int z = 0; z < zs; z++)
				{
					bb.put(red);
					bb.put(grn);
					bb.put(blu);
					bb.put((byte) ((RivalRebelsCellularNoise.noise((double) x / (double) xs, (double) z / (double) zs, (double) i / (double) ys) + 1) * 127));
				}
			}
			bb.flip();
			int id = GL11.glGenTextures();
			RenderSystem.bindTexture(id);
			RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, xs, zs, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
			ids[i] = id;
		}
		return ids;
	}

    public static long getTime()
	{
		return System.currentTimeMillis();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

}
