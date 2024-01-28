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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.client.model.ModelReactor;
import assets.rivalrebels.client.model.RenderLibrary;
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.block.machine.BlockReactor;
import assets.rivalrebels.common.tileentity.TileEntityMachineBase;
import assets.rivalrebels.common.tileentity.TileEntityReactor;
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

@OnlyIn(Dist.CLIENT)
public class TileEntityReactorRenderer implements BlockEntityRenderer<TileEntityReactor>
{
	private final ModelReactor mr;
	private final ModelLaptop ml;
	private final ModelFromObj mo;

	public TileEntityReactorRenderer(BlockEntityRendererFactory.Context context) {
		mr = new ModelReactor();
		ml = new ModelLaptop();
        mo = ModelFromObj.readObjFile("a.obj");
	}

    @Override
    public void render(TileEntityReactor entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		int var9 = entity.getCachedState().get(BlockReactor.META);
		short var11 = 0;
		if (var9 == 2)
		{
			var11 = 180;
		}

		if (var9 == 3)
		{
			var11 = 0;
		}

		if (var9 == 4)
		{
			var11 = -90;
		}

		if (var9 == 5)
		{
			var11 = 90;
		}
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 1.1875F, (float) entity.getPos().getZ() + 0.5F);
		matrices.multiply(new Quaternion(var11, 0.0F, 1.0F, 0.0F));
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etlaptop);
		ml.renderModel(buffer, matrices, (float) -entity.slide);
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etscreen);
		ml.renderScreen(buffer, matrices, (float) -entity.slide);
		matrices.pop();
		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
		matrices.multiply(new Quaternion(var11, 0.0F, 1.0F, 0.0F));
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etreactor);
		mr.renderModel(matrices, buffer);
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etelectrode);
		matrices.translate(0, 2, -0.125f);
		matrices.scale(0.2f, 0.2f, 0.2f);
		mo.render(buffer);
		matrices.pop();
		for (int i = 0; i < entity.machines.size(); i++) {
			TileEntityMachineBase temb = entity.machines.get(i);
			if (temb.powerGiven > 0)
			{
				float radius = (temb.powerGiven * temb.powerGiven) / 40000;
				radius += 0.03;
				int steps = 2;
				if (radius > 0.05) steps++;
				if (radius > 0.10) steps++;
				if (radius > 0.15) steps++;
				if (radius > 0.25) radius = 0.25f;
				// if (steps == 2 && temb.world.random.nextInt(5) != 0) return;
				RenderLibrary.renderModel(matrices, buffer, (float) entity.getPos().getX() + 0.5f, (float) entity.getPos().getY() + 2.5f, (float) entity.getPos().getZ() + 0.5f, temb.getPos().getX() - entity.getPos().getX(), temb.getPos().getY() - entity.getPos().getY() - 2.5f, temb.getPos().getZ() - entity.getPos().getZ(), 0.5f, radius, steps, (temb.edist / 2), 0.1f, 0.45f, 0.45f, 0.5f, 0.5f);
			}
		}
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

}
