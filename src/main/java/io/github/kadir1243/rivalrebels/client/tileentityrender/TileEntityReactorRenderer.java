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

import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.world.phys.Vec3;
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
    private final QuadCollection electrodeModel;

    public TileEntityReactorRenderer(BlockEntityRendererProvider.Context context) {
        electrodeModel = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.ELECTRODE_MODEL);
    }

    @Override
    public void render(TileEntityReactor blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
		Direction facing = blockEntity.getBlockState().getValue(BlockReactor.FACING);
        poseStack.pushPose();
		poseStack.translate(0.5F, 1.1875F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
		ModelLaptop.renderModel(bufferSource, poseStack, (float) -blockEntity.slide, packedLight, packedOverlay);
		ModelLaptop.renderScreen(bufferSource, RRIdentifiers.etscreen, poseStack, (float) -blockEntity.slide, packedLight, packedOverlay);
		poseStack.popPose();
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
		ModelReactor.renderModel(poseStack, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etreactor)), packedLight, packedOverlay);
		poseStack.translate(0, 2, -0.125f);
		poseStack.scale(0.2f, 0.2f, 0.2f);
        VertexConsumer electrodeBuffer = bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etelectrode));
        for (BakedQuad quad : electrodeModel.getAll()) {
            electrodeBuffer.putBulkData(poseStack.last(), quad, 1, 1, 1, 1, packedLight, packedOverlay);
        }
        poseStack.popPose();
        Map<BlockPos, ReactorMachinesPacket.MachineEntry> entries = new HashMap<>(blockEntity.entries);
        for (TileEntityMachineBase temb : blockEntity.machines) {
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
				RenderLibrary.renderModel(poseStack, bufferSource, 0.5F, 2.5F, 0.5F, temb.getBlockPos().getX() - blockEntity.getBlockPos().getX(), temb.getBlockPos().getY() - blockEntity.getBlockPos().getY() - 2.5f, temb.getBlockPos().getZ() - blockEntity.getBlockPos().getZ(), 0.5f, radius, steps, (temb.edist / 2), 0.1f, 0.45f, 0.45f, 0.5f, 0.5f);
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
