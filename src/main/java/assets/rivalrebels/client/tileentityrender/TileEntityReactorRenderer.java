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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Box;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TileEntityReactorRenderer implements BlockEntityRenderer<TileEntityReactor>, CustomRenderBoxExtension<TileEntityReactor> {
    public static final SpriteIdentifier ELECTRODE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etelectrode);
    public static final SpriteIdentifier REACTOR_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etreactor);
    public static final SpriteIdentifier LAPTOP_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etlaptop);
    public static final SpriteIdentifier SCREEN_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etscreen);
    private final ModelReactor mr = new ModelReactor();
	private static final ModelFromObj mo = ModelFromObj.readObjFile("a.obj");

	public TileEntityReactorRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(TileEntityReactor entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		int meta = entity.getCachedState().get(BlockReactor.META);
		short var11 = switch (meta) {
            case 2 -> 180;
            case 3 -> 0;
            case 4 -> -90;
            case 5 -> 90;
            default -> 0;
        };
        matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 1.1875F, (float) entity.getPos().getZ() + 0.5F);
		matrices.multiply(new Quaternionf(var11, 0.0F, 1.0F, 0.0F));
		ModelLaptop.renderModel(LAPTOP_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), matrices, (float) -entity.slide, light, overlay);
		ModelLaptop.renderScreen(SCREEN_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), matrices, (float) -entity.slide, light, overlay);
		matrices.pop();
		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
		matrices.multiply(new Quaternionf(var11, 0.0F, 1.0F, 0.0F));
		mr.renderModel(matrices, REACTOR_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		matrices.translate(0, 2, -0.125f);
		matrices.scale(0.2f, 0.2f, 0.2f);
		mo.render(ELECTRODE_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
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
				RenderLibrary.renderModel(matrices, vertexConsumers, (float) entity.getPos().getX() + 0.5f, (float) entity.getPos().getY() + 2.5f, (float) entity.getPos().getZ() + 0.5f, temb.getPos().getX() - entity.getPos().getX(), temb.getPos().getY() - entity.getPos().getY() - 2.5f, temb.getPos().getZ() - entity.getPos().getZ(), 0.5f, radius, steps, (temb.edist / 2), 0.1f, 0.45f, 0.45f, 0.5f, 0.5f);
			}
		}
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

    @Override
    public Box getRenderBoundingBox(TileEntityReactor blockEntity) {
        return Box.from(BlockBox.create(blockEntity.getPos().add(-100, -100, -100), blockEntity.getPos().add(100, 100, 100)));
    }
}
