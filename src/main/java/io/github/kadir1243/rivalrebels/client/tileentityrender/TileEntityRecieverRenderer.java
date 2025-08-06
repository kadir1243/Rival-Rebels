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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@OnlyIn(Dist.CLIENT)
public class TileEntityRecieverRenderer implements BlockEntityRenderer<TileEntityReciever> {
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
    public void render(TileEntityReciever blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        poseStack.pushPose();
		poseStack.translate(0.5F, 0, 0.5F);
        Direction facing = blockEntity.getBlockState().getValue(BlockReciever.FACING);

		poseStack.pushPose();
        poseStack.translate(0, 0, 0.5);
        ObjModels.render(trayModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etreciever)), poseStack, CommonColors.WHITE, packedLight, packedOverlay);
		if (blockEntity.hasWeapon) {
            poseStack.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.yaw - facing.toYRot()));
			ObjModels.render(armModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etreciever)), poseStack, CommonColors.WHITE, packedLight, packedOverlay);
            poseStack.mulPose(Axis.XP.rotationDegrees(blockEntity.pitch));
			ObjModels.render(adsdragonModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etadsdragon)), poseStack, CommonColors.WHITE, packedLight, packedOverlay);
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
}
