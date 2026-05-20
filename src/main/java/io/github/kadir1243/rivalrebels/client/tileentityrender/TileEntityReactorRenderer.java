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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TileEntityReactorRenderer implements BlockEntityRenderer<TileEntityReactor, TileEntityReactorRenderer.ReactorBlockEntityRenderState> {
    private final QuadCollection electrodeModel;

    public TileEntityReactorRenderer(BlockEntityRendererProvider.Context context) {
        electrodeModel = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.ELECTRODE_MODEL);
    }

    @Override
    public void submit(ReactorBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        int packedLight = renderState.lightCoords;
        int packedOverlay = OverlayTexture.NO_OVERLAY;
        Direction facing = renderState.facing;
        poseStack.pushPose();
		poseStack.translate(0.5F, 1.1875F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
		ModelLaptop.renderModel(nodeCollector, poseStack, -renderState.slide, true, packedLight, packedOverlay);
		ModelLaptop.renderScreen(nodeCollector, RRIdentifiers.etscreen, poseStack, -renderState.slide, packedLight, packedOverlay);
		poseStack.popPose();
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(RRIdentifiers.etreactor), (pose, consumer) -> {
            ModelReactor.renderModel(pose, consumer, packedLight, packedOverlay);
        });
		poseStack.translate(0, 2, -0.125f);
		poseStack.scale(0.2f, 0.2f, 0.2f);
        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(RRIdentifiers.etelectrode), (pose, consumer) -> {
            ObjModels.render(electrodeModel, consumer, pose, CommonColors.WHITE, packedLight, packedOverlay);
        });
        poseStack.popPose();

        for (TileEntityMachineBase temb : renderState.machines) {
            if (!renderState.entries.get(temb.getBlockPos()).enabled()) {
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
				RenderLibrary.renderModel(poseStack, nodeCollector, RenderTypes.lightning(), 0.5F, 2.5F, 0.5F, temb.getBlockPos().getX() - renderState.blockPos.getX(), temb.getBlockPos().getY() - renderState.blockPos.getY() - 2.5f, temb.getBlockPos().getZ() - renderState.blockPos.getZ(), 0.5f, radius, steps, (temb.edist / 2), 0.1f, 0.45f, 0.45f, 0.5f, 0.5f);
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

    @Override
    public ReactorBlockEntityRenderState createRenderState() {
        return new ReactorBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(TileEntityReactor blockEntity, ReactorBlockEntityRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.slide = (float) blockEntity.slide;
        renderState.machines = blockEntity.machines;
        renderState.entries = blockEntity.entries;
        renderState.facing = blockEntity.getBlockState().getValue(BlockReactor.FACING);
    }

    public static class ReactorBlockEntityRenderState extends BlockEntityRenderState {
        public Direction facing;
        public float slide;
        public List<TileEntityMachineBase> machines;
        public Map<BlockPos, ReactorMachinesPacket.MachineEntry> entries;
    }

}
