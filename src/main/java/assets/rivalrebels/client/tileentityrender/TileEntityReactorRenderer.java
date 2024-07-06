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
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.client.model.RenderLibrary;
import assets.rivalrebels.common.block.machine.BlockReactor;
import assets.rivalrebels.common.tileentity.TileEntityMachineBase;
import assets.rivalrebels.common.tileentity.TileEntityReactor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@Environment(EnvType.CLIENT)
public class TileEntityReactorRenderer implements BlockEntityRenderer<TileEntityReactor>, CustomRenderBoxExtension<TileEntityReactor> {
    public static final Material ELECTRODE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etelectrode);
    public static final Material REACTOR_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etreactor);
    public static final Material LAPTOP_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etlaptop);
    public static final Material SCREEN_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etscreen);
    private final ModelReactor mr = new ModelReactor();

    public TileEntityReactorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityReactor entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		Direction facing = entity.getBlockState().getValue(BlockReactor.FACING);
        matrices.pushPose();
		matrices.translate(0.5F, 1.1875F, 0.5F);
		matrices.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
		ModelLaptop.renderModel(LAPTOP_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), matrices, (float) -entity.slide, light, overlay);
		ModelLaptop.renderScreen(SCREEN_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), matrices, (float) -entity.slide, light, overlay);
		matrices.popPose();
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
		mr.renderModel(matrices, REACTOR_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		matrices.translate(0, 2, -0.125f);
		matrices.scale(0.2f, 0.2f, 0.2f);
		ObjModels.electrode.render(matrices, ELECTRODE_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		matrices.popPose();
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
				RenderLibrary.renderModel(matrices, vertexConsumers, (float) entity.getBlockPos().getX() + 0.5f, (float) entity.getBlockPos().getY() + 2.5f, (float) entity.getBlockPos().getZ() + 0.5f, temb.getBlockPos().getX() - entity.getBlockPos().getX(), temb.getBlockPos().getY() - entity.getBlockPos().getY() - 2.5f, temb.getBlockPos().getZ() - entity.getBlockPos().getZ(), 0.5f, radius, steps, (temb.edist / 2), 0.1f, 0.45f, 0.45f, 0.5f, 0.5f);
			}
		}
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityReactor blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-100, -100, -100), blockEntity.getBlockPos().offset(100, 100, 100)));
    }
}
