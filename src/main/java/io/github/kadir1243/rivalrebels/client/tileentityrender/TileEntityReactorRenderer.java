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
package io.github.kadir1243.rivalrebels.client.tileentityrender;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ModelLaptop;
import io.github.kadir1243.rivalrebels.client.model.ModelReactor;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.model.RenderLibrary;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockReactor;
import io.github.kadir1243.rivalrebels.common.packet.ReactorMachinesPacket;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityMachineBase;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityReactor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TileEntityReactorRenderer implements BlockEntityRenderer<TileEntityReactor> {
    public TileEntityReactorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityReactor entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		Direction facing = entity.getBlockState().getValue(BlockReactor.FACING);
        matrices.pushPose();
		matrices.translate(0.5F, 1.1875F, 0.5F);
		matrices.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
		ModelLaptop.renderModel(vertexConsumers, matrices, (float) -entity.slide, light, overlay);
		ModelLaptop.renderScreen(vertexConsumers, RRIdentifiers.etscreen, matrices, (float) -entity.slide, light, overlay);
		matrices.popPose();
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
		ModelReactor.renderModel(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.etreactor)), light, overlay);
		matrices.translate(0, 2, -0.125f);
		matrices.scale(0.2f, 0.2f, 0.2f);
		ObjModels.renderSolid(ObjModels.electrode, RRIdentifiers.etelectrode, matrices, vertexConsumers, light, overlay);
        matrices.popPose();
        Map<BlockPos, ReactorMachinesPacket.MachineEntry> entries = new HashMap<>(entity.entries);
        for (TileEntityMachineBase temb : entity.machines) {
            if (!entries.get(temb.getBlockPos()).enabled()) {
                continue;
            }
            if (temb.powerGiven > 0) {
				float radius = (temb.powerGiven * temb.powerGiven) / 40000;
				radius += 0.03;
				int steps = 2;
				if (radius > 0.05) steps++;
				if (radius > 0.10) steps++;
				if (radius > 0.15) steps++;
				if (radius > 0.25) radius = 0.25f;
				// if (steps == 2 && temb.world.random.nextInt(5) != 0) return;
				RenderLibrary.renderModel(matrices, vertexConsumers, 0.5F, 2.5F, 0.5F, temb.getBlockPos().getX() - entity.getBlockPos().getX(), temb.getBlockPos().getY() - entity.getBlockPos().getY() - 2.5f, temb.getBlockPos().getZ() - entity.getBlockPos().getZ(), 0.5f, radius, steps, (temb.edist / 2), 0.1f, 0.45f, 0.45f, 0.5f, 0.5f);
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
