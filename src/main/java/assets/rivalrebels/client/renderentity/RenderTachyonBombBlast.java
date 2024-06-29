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
import assets.rivalrebels.RivalRebels;
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
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class RenderTachyonBombBlast extends EntityRenderer<EntityTachyonBombBlast> {
    public static final Material TSAR_FLAME_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettsarflame);
    private final ModelTsarBlast model;

	public RenderTachyonBombBlast(EntityRendererProvider.Context manager) {
        super(manager);
		model = new ModelTsarBlast();
	}

    @Override
    public void render(EntityTachyonBombBlast entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        entity.time++;
        double radius = (((entity.getDeltaMovement().x() * 10) - 1) * ((entity.getDeltaMovement().x() * 10) - 1) * 2) + RivalRebels.tsarBombaStrength;
        matrices.pushPose();
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.solid());
        if (entity.time < 60) {
            double elev = entity.time / 5f;
            matrices.translate(x, y + elev, z);
            ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.time * RRConfig.CLIENT.getShroomScale(), 1, 1, 1, 1);
        } else if (entity.time < 600 && radius - RivalRebels.tsarBombaStrength > 9) {
            double elev = (entity.time - 60f) / 32f + 10.0f;
            matrices.pushPose();
            matrices.translate(x, y, z);
            matrices.scale(RRConfig.CLIENT.getShroomScale() * 2.0f, RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 2.0f);
            matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * 2)));
            matrices.mulPose(Axis.XP.rotationDegrees((float) (elev * 3)));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) elev, 1, 0.25f, 0, 1f);
            matrices.popPose();
            matrices.pushPose();
            matrices.translate(x, y, z);
            matrices.scale(RRConfig.CLIENT.getShroomScale() * 2.0f, RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 2.0f);
            matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * -2)));
            matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 4)));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
            matrices.popPose();
            matrices.pushPose();
            matrices.translate(x, y + elev * 4, z);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 3.0f, RRConfig.CLIENT.getShroomScale());
            matrices.mulPose(Axis.XP.rotationDegrees((float) (elev * -3)));
            matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 2)));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.4f), 1, 0, 0, 1f);
            matrices.popPose();
            matrices.pushPose();
            matrices.translate(x, y + elev * 4, z);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 3.0f, RRConfig.CLIENT.getShroomScale());
            matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * -1)));
            matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 3)));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.6f), 1, 1, 0, 1);
            matrices.popPose();
        } else {
            float elev = (entity.time - (radius - RivalRebels.tsarBombaStrength > 9 ? 600f : 0f)) / 8f;
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.0f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 2.0f), (float) z, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.1f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 6.0f), (float) z, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.2f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 10.0f), (float) z, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.3f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 14.0f), (float) z, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.4f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 18.0f), (float) z, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.5f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 22.0f), (float) z, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.6f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 26.0f), (float) z, light);
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.7f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 30.0f), (float) z, light);
            matrices.translate(x, y + 10 + ((entity.getDeltaMovement().x() - 0.1d) * 14.14213562), z);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale());
            float horizontal = elev * 0.025f + 1.0f;
            matrices.scale((float) (horizontal * radius * 0.116f), (float) (radius * 0.065f), (float) (horizontal * radius * 0.116f));
            matrices.scale(0.8f, 0.8f, 0.8f);
            //RenderSystem.enableBlend();
            //RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
            model.render(matrices, TSAR_FLAME_TEXTURE.buffer(vertexConsumers, RenderType::entityTranslucent), light, OverlayTexture.NO_OVERLAY);
            //RenderSystem.disableBlend();
        }
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTachyonBombBlast entity) {
        return null;
    }

}
