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
import assets.rivalrebels.client.model.ModelAntimatterBombBlast;
import assets.rivalrebels.client.model.ModelBlastRing;
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.entity.EntityAntimatterBombBlast;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;
import org.joml.Vector4f;

public class RenderAntimatterBombBlast extends EntityRenderer<EntityAntimatterBombBlast> {
    public static final Material ANTIMATTER_BLAST_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etantimatterblast);
    private final ModelAntimatterBombBlast modelabomb = new ModelAntimatterBombBlast();

	public RenderAntimatterBombBlast(EntityRendererProvider.Context manager)
	{
        super(manager);
    }

    @Override
    public void render(EntityAntimatterBombBlast entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        RandomSource random = entity.getCommandSenderWorld().random;
        entity.time++;
		double radius = (((entity.getDeltaMovement().x() * 10) - 1) * ((entity.getDeltaMovement().x() * 10) - 1) * 2) + RivalRebels.tsarBombaStrength;
		matrices.pushPose();
		matrices.pushPose();
		matrices.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
		float size = (entity.time % 100) * 2.0f;
		ModelBlastRing.renderModel(matrices, vertexConsumers.getBuffer(RenderType.solid()), size, 64, 6f, 2f, 0f, 0f, 0f, (float)entity.getX(), (float)entity.getY(), (float)entity.getZ(), new Vector4f(0, 0, 0.2F, 1), light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
		if (entity.time < 60) {
			double elev = entity.time / 5f;
			matrices.translate(entity.getX(), entity.getY() + elev, entity.getZ());
			ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.time, 1, 1, 1, 1);
		}
		else
		{
			//double elev = Math.sin(entity.time * 0.1f) * 5.0f + 60.0f;
			//double noisy = 5.0f;
			//double hnoisy = noisy * 0.5f;
			matrices.translate(entity.getX(), entity.getY(), entity.getZ());
			matrices.scale((float) (radius * 0.06f), (float) (radius * 0.06f), (float) (radius * 0.06f));
			modelabomb.render(matrices, ANTIMATTER_BLAST_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light);
			/*modelsphere.renderModel(50.0f, 0.0f, 0.0f, 0.0f, 1.0f, false);
			matrices.push();
			//RenderSystem.translatef(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * 2), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 3), 1, 0, 0);
			modelsphere.renderModel((float) elev, 0.2f, 0.6f, 1, 1f);
			matrices.pop();
			matrices.push();
			//RenderSystem.translatef(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -2), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 4), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.2f), 0.6f, 0.2f, 1, 1f);
			matrices.pop();
			matrices.push();
			//RenderSystem.translatef(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -3), 1, 0, 0);
			RenderSystem.rotatef((float) (elev * 2), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.4f), 0.4f, 0, 1, 1f);
			matrices.pop();
			matrices.push();
			//RenderSystem.translatef(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -1), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 3), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.6f), 0, 0.4f, 1, 1);
			matrices.pop();*/
			///summon rivalrebels.rivalrebelsentity51 ~ ~-2 ~ {charge:5}
		}
		matrices.popPose();
		if (RRConfig.CLIENT.isAntimatterFlash()) {
			int ran = (int) (random.nextDouble() * 10f - 5f);
			for (int i = 0; i < ran; i++) {
				matrices.popPose();
			}
			for (int i = -5; i < 0; i++) {
				matrices.pushPose();
			}
			RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE);
			matrices.scale(random.nextFloat(), random.nextFloat(), random.nextFloat());
			matrices.mulPose(new Quaternionf(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat() * 360));
			matrices.translate(random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f);
			ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.time, (float)random.nextDouble(), (float)random.nextDouble(), (float)random.nextDouble(), 1);
		}
	}

    @Override
    public ResourceLocation getTextureLocation(EntityAntimatterBombBlast entity) {
        return null;
    }
}
