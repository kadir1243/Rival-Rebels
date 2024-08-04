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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelBlastRing;
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.client.model.ModelTsarBlast;
import assets.rivalrebels.common.entity.EntityTachyonBombBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;

public class RenderTachyonBombBlast extends EntityRenderer<EntityTachyonBombBlast> {
    private final ModelTsarBlast model;

	public RenderTachyonBombBlast(EntityRendererProvider.Context manager) {
        super(manager);
		model = new ModelTsarBlast();
	}

    @Override
    public void render(EntityTachyonBombBlast entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        double radius = (((entity.getDeltaMovement().x() * 10) - 1) * ((entity.getDeltaMovement().x() * 10) - 1) * 2) + RRConfig.SERVER.getTsarBombaStrength();
        matrices.pushPose();
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.solid());
        if (entity.tickCount < 60) {
            double elev = entity.tickCount / 5f;
            matrices.translate(0, elev, 0);
            ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.tickCount * RRConfig.CLIENT.getShroomScale(), CommonColors.WHITE);
        } else if (entity.tickCount < 600 && radius - RRConfig.SERVER.getTsarBombaStrength() > 9) {
            double elev = (entity.tickCount - 60f) / 32f + 10.0f;
            matrices.pushPose();
            matrices.scale(RRConfig.CLIENT.getShroomScale() * 2.0f, RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 2.0f);
            matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * 2)));
            matrices.mulPose(Axis.XP.rotationDegrees((float) (elev * 3)));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) elev, 1, 0.25f, 0, 1f);
            matrices.popPose();
            matrices.pushPose();
            matrices.scale(RRConfig.CLIENT.getShroomScale() * 2.0f, RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 2.0f);
            matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * -2)));
            matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 4)));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
            matrices.popPose();
            matrices.pushPose();
            matrices.translate(0, elev * 4, 0);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 3.0f, RRConfig.CLIENT.getShroomScale());
            matrices.mulPose(Axis.XP.rotationDegrees((float) (elev * -3)));
            matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 2)));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.4f), CommonColors.RED);
            matrices.popPose();
            matrices.pushPose();
            matrices.translate(0, elev * 4, 0);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 3.0f, RRConfig.CLIENT.getShroomScale());
            matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * -1)));
            matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 3)));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.6f), 1, 1, 0, 1);
            matrices.popPose();
        } else {
            float elev = (entity.tickCount - (radius - RRConfig.SERVER.getTsarBombaStrength() > 9 ? 600f : 0f)) / 8f;
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.0f, 32, 2, 0.5f, 0, 0, 0, 0F, 2F, 0F, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.1f, 32, 2, 0.5f, 0, 0, 0, 0F, 6F, 0F, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.2f, 32, 2, 0.5f, 0, 0, 0, 0F, 10F, 0F, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.3f, 32, 2, 0.5f, 0, 0, 0, 0F, 14F, 0F, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.4f, 32, 2, 0.5f, 0, 0, 0, 0F, 18F, 0F, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.5f, 32, 2, 0.5f, 0, 0, 0, 0F, 22F, 0F, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.6f, 32, 2, 0.5f, 0, 0, 0, 0F, 26F, 0F, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.7f, 32, 2, 0.5f, 0, 0, 0, 0F, 30F, 0F, light);
            matrices.translate(0, 10 + ((entity.getDeltaMovement().x() - 0.1d) * 14.14213562), 0);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale());
            float horizontal = elev * 0.025f + 1.0f;
            matrices.scale((float) (horizontal * radius * 0.116f), (float) (radius * 0.065f), (float) (horizontal * radius * 0.116f));
            matrices.scale(0.8f, 0.8f, 0.8f);
            //RenderSystem.enableBlend();
            //RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
            model.render(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(RRIdentifiers.ettsarflame)), light, OverlayTexture.NO_OVERLAY, tickDelta);
            //RenderSystem.disableBlend();
        }
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTachyonBombBlast entity) {
        return null;
    }

}
