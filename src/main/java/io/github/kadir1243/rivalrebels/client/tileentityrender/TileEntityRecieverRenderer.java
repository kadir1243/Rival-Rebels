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
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockReciever;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityReciever;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TileEntityRecieverRenderer implements BlockEntityRenderer<TileEntityReciever, TileEntityRecieverRenderer.RecieverBlockEntityRenderState> {
    private final QuadCollection armModel;
    private final QuadCollection trayModel;
    private final QuadCollection adsdragonModel;

    public TileEntityRecieverRenderer(BlockEntityRendererProvider.Context context) {
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        armModel = modelManager.getStandaloneModel(ObjModels.ARM_MODEL);
        trayModel = modelManager.getStandaloneModel(ObjModels.TRAY_MODEL);
        adsdragonModel = modelManager.getStandaloneModel(ObjModels.ADS_DRAGON_MODEL);
    }

    @Override
    public RecieverBlockEntityRenderState createRenderState() {
        return new RecieverBlockEntityRenderState();
    }

    @Override
    public void submit(RecieverBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
		poseStack.translate(0.5F, 0, 0.5F);
        int packedLight = renderState.lightCoords;
        int packedOverlay = OverlayTexture.NO_OVERLAY;
        Direction facing = renderState.facing;

		poseStack.pushPose();
        poseStack.translate(0, 0, 0.5);
        ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etreciever), trayModel, poseStack, CommonColors.WHITE, packedLight, packedOverlay);
		if (renderState.hasWeapon) {
            poseStack.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yaw - facing.toYRot()));
            ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etreciever), armModel, poseStack, CommonColors.WHITE, packedLight, packedOverlay);
            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.pitch));
            ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etreciever), adsdragonModel, poseStack, CommonColors.WHITE, packedLight, packedOverlay);
		}
		poseStack.popPose();
		poseStack.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityReciever blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-1, -1, -1), blockEntity.getBlockPos().offset(2, 2, 2)));
    }

    @Override
    public void extractRenderState(TileEntityReciever blockEntity, RecieverBlockEntityRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.pitch = blockEntity.pitch;
        renderState.yaw = blockEntity.yaw;
        renderState.hasWeapon = blockEntity.hasWeapon;
        renderState.facing = blockEntity.getBlockState().getValue(BlockReciever.FACING);
    }

    public static class RecieverBlockEntityRenderState extends BlockEntityRenderState {
        public Direction facing;
        public float yaw;
        public float pitch;
        public boolean hasWeapon;
    }
}
