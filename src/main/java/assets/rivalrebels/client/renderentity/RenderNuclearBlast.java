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
import assets.rivalrebels.common.entity.EntityNuclearBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import org.joml.Vector3f;

public class RenderNuclearBlast extends EntityRenderer<EntityNuclearBlast>
{
	private float	ring1			= 0;
	private float	ring2			= 0;
	private float	ring3			= 0;
	private float	height			= 0;

	private int		textureCoordx	= 0;
	private int		textureCoordy	= 0;

	public RenderNuclearBlast(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityNuclearBlast entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

		matrices.pushPose();
		if (entity.tickCount == 0)
		{
			ring1 = 0;
			ring2 = 0;
			ring3 = 0;
			height = 0;
			textureCoordx = entity.level().random.nextInt(64);
		}

		ring1 += 0.02;
		ring2 += Math.sin(ring1) * 0.01;
		ring3 -= Math.sin(ring1) * 0.01;

		if (ring2 < 6)
		{
			ring2 += 0.1;
		}

		if (ring3 < 8)
		{
			ring3 += 0.1;
		}

		if (height < 8)
		{
			height += 0.1;
		}

        if (entity.tickCount < 600) {
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.solid());
            ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * ring1 * 15, 64, 4, 0.5f, 0, 0, 0, (float) x, (float) y - 3, (float) z, light);
			ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * ring2, 32, 1, 0.5f, 0, 0, 0, (float) x, (float) y + height + ring3, (float) z, light);
			ModelBlastRing.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * ring3, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) y + height + 7 + ring2, (float) z, light);
			if (entity.tickCount > 550)
			{
				ring2 += 0.1;
				ring3 += 0.1;
			}
		}
		else
		{
			ring1 = 0;
			ring2 = 0;
			ring3 = 0;
			height = 0;
		}

		textureCoordy -= 1;
		if (textureCoordy <= 0)
		{
			textureCoordy = 128;
		}

		float par5 = (textureCoordx + 128) / 128.0F;
		float par6 = (textureCoordx) / 128.0F;
		float par7 = (textureCoordy + 128) / 128.0F;
		float par8 = (textureCoordy) / 128.0F;

		matrices.pushPose();
		matrices.translate(x, y - 10, z);
		matrices.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
		matrices.scale(0.5F + (float) entity.getDeltaMovement().y() * 0.3F, 2.6F + (float) entity.getDeltaMovement().y() * 0.3F, 0.5F + (float) entity.getDeltaMovement().y() * 0.3F);

        ResourceLocation identifier;
        if (entity.getDeltaMovement().x() == 1) {
			identifier = RRIdentifiers.ettroll;
		} else {
			identifier = RRIdentifiers.etradiation;
		}

        VertexConsumer solidRenderer = vertexConsumers.getBuffer(RenderType.entitySolid(identifier));

        int size = (int) (entity.getDeltaMovement().y());

		Vector3f pxv1 = new Vector3f(4, 0 - size * 2.5F, 0);
		Vector3f nxv1 = new Vector3f(-4, 0 - size * 2.5F, 0);
		Vector3f pzv1 = new Vector3f(0, 0 - size * 2.5F, 4);
		Vector3f nzv1 = new Vector3f(0, 0 - size * 2.5F, -4);

		Vector3f pxv2 = new Vector3f(2F, 1.5F - size * 2F, 0);
		Vector3f nxv2 = new Vector3f(-2F, 1.5F - size * 2F, 0);
		Vector3f pzv2 = new Vector3f(0, 1.5F - size * 2F, 2F);
		Vector3f nzv2 = new Vector3f(0, 1.5F - size * 2F, -2F);

		Vector3f pxv3 = new Vector3f(1.5F, 3.5F - size * 1.5F, 0);
		Vector3f nxv3 = new Vector3f(-1.5F, 3.5F - size * 1.5F, 0);
		Vector3f pzv3 = new Vector3f(0, 3.5F - size * 1.5F, 1.5F);
		Vector3f nzv3 = new Vector3f(0, 3.5F - size * 1.5F, -1.5F);

		Vector3f pxv4 = new Vector3f(1.5F, 6.5F - size * 1F, 0);
		Vector3f nxv4 = new Vector3f(-1.5F, 6.5F - size * 1F, 0);
		Vector3f pzv4 = new Vector3f(0, 6.5F - size * 1F, 1.5F);
		Vector3f nzv4 = new Vector3f(0, 6.5F - size * 1F, -1.5F);

		Vector3f pxv5 = new Vector3f(2F, 9.5F - size * 0.5F, 0);
		Vector3f nxv5 = new Vector3f(-2F, 9.5F - size * 0.5F, 0);
		Vector3f pzv5 = new Vector3f(0, 9.5F - size * 0.5F, 2F);
		Vector3f nzv5 = new Vector3f(0, 9.5F - size * 0.5F, -2F);

		Vector3f pxv6 = new Vector3f(3F, 11F, 0);
		Vector3f nxv6 = new Vector3f(-3F, 11F, 0);
		Vector3f pzv6 = new Vector3f(0, 11F, 3F);
		Vector3f nzv6 = new Vector3f(0, 11F, -3F);

		Vector3f pxv7 = new Vector3f(16F, 10F, 0);
		Vector3f nxv7 = new Vector3f(-16F, 10F, 0);
		Vector3f pzv7 = new Vector3f(0, 10F, 16F);
		Vector3f nzv7 = new Vector3f(0, 10F, -16F);

		Vector3f ppv7 = new Vector3f(8F, 10F, 8F);
		Vector3f npv7 = new Vector3f(-8F, 10F, 8F);
		Vector3f pnv7 = new Vector3f(8F, 10F, -8F);
		Vector3f nnv7 = new Vector3f(-8F, 10F, -8F);

		Vector3f pxv8 = new Vector3f(32F, 12F, 0);
		Vector3f nxv8 = new Vector3f(-32F, 12F, 0);
		Vector3f pzv8 = new Vector3f(0, 12F, 32F);
		Vector3f nzv8 = new Vector3f(0, 12F, -32F);

		Vector3f ppv8 = new Vector3f(22.5F, 12F, 22.5F);
		Vector3f npv8 = new Vector3f(-22.5F, 12F, 22.5F);
		Vector3f pnv8 = new Vector3f(22.5F, 12F, -22.5F);
		Vector3f nnv8 = new Vector3f(-22.5F, 12F, -22.5F);

		Vector3f pxv9 = new Vector3f(16F, 13F, 0);
		Vector3f nxv9 = new Vector3f(-16F, 13F, 0);
		Vector3f pzv9 = new Vector3f(0, 13F, 16F);
		Vector3f nzv9 = new Vector3f(0, 13F, -16F);

		Vector3f ppv9 = new Vector3f(11.5F, 13F, 11.5F);
		Vector3f npv9 = new Vector3f(-11.5F, 13F, 11.5F);
		Vector3f pnv9 = new Vector3f(11.5F, 13F, -11.5F);
		Vector3f nnv9 = new Vector3f(-11.5F, 13F, -11.5F);

		Vector3f v9 = new Vector3f(0F, 13F, 0F);

		int time = size * 10;

		if (entity.tickCount > 0 && entity.tickCount < 600 + time)
		{
			addFace(matrices, solidRenderer, pxv1, nzv1, nzv2, pxv2, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, pzv1, pxv1, pxv2, pzv2, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nxv1, pzv1, pzv2, nxv2, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nzv1, nxv1, nxv2, nzv2, par5, par6, par7, par8, light);
		}

		if (entity.tickCount > 10 && entity.tickCount < 610 + time)
		{
			addFace(matrices, solidRenderer, pxv2, nzv2, nzv3, pxv3, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, pzv2, pxv2, pxv3, pzv3, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nxv2, pzv2, pzv3, nxv3, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nzv2, nxv2, nxv3, nzv3, par5, par6, par7, par8, light);
		}

		if (entity.tickCount > 20 && entity.tickCount < 620 + time)
		{
			addFace(matrices, solidRenderer, pxv3, nzv3, nzv4, pxv4, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, pzv3, pxv3, pxv4, pzv4, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nxv3, pzv3, pzv4, nxv4, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nzv3, nxv3, nxv4, nzv4, par5, par6, par7, par8, light);
		}

		if (entity.tickCount > 30 && entity.tickCount < 630 + time)
		{
			addFace(matrices, solidRenderer, pxv4, nzv4, nzv5, pxv5, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, pzv4, pxv4, pxv5, pzv5, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nxv4, pzv4, pzv5, nxv5, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nzv4, nxv4, nxv5, nzv5, par5, par6, par7, par8, light);
		}

		if (entity.tickCount > 40 && entity.tickCount < 640 + time)
		{
			addFace(matrices, solidRenderer, pxv5, nzv5, nzv6, pxv6, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, pzv5, pxv5, pxv6, pzv6, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nxv5, pzv5, pzv6, nxv6, par5, par6, par7, par8, light);
			addFace(matrices, solidRenderer, nzv5, nxv5, nxv6, nzv6, par5, par6, par7, par8, light);
		}

		if (entity.tickCount > 30 && entity.tickCount < 650 + time)
		{
			addFace(matrices, solidRenderer, pxv6, nzv6, nzv7, pxv7, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, pzv6, pxv6, pxv7, pzv7, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, nxv6, pzv6, pzv7, nxv7, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, nzv6, nxv6, nxv7, nzv7, par6, par5, par8, par7, light);
		}

		if (entity.tickCount > 20 && entity.tickCount < 650 + time)
		{
			addFace(matrices, solidRenderer, pzv7, ppv7, ppv8, pzv8, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, ppv7, pxv7, pxv8, ppv8, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, pxv7, pnv7, pnv8, pxv8, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, pnv7, nzv7, nzv8, pnv8, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, nzv7, nnv7, nnv8, nzv8, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, nnv7, nxv7, nxv8, nnv8, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, nxv7, npv7, npv8, nxv8, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, npv7, pzv7, pzv8, npv8, par6, par5, par8, par7, light);
		}

		if (entity.tickCount > 10 && entity.tickCount < 650 + time)
		{
			addFace(matrices, solidRenderer, pzv8, ppv8, ppv9, pzv9, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, ppv8, pxv8, pxv9, ppv9, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, pxv8, pnv8, pnv9, pxv9, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, pnv8, nzv8, nzv9, pnv9, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, nzv8, nnv8, nnv9, nzv9, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, nnv8, nxv8, nxv9, nnv9, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, nxv8, npv8, npv9, nxv9, par6, par5, par8, par7, light);
			addFace(matrices, solidRenderer, npv8, pzv8, pzv9, npv9, par6, par5, par8, par7, light);

			addFace(matrices, solidRenderer, pxv6, pzv6, nxv6, nzv6, par6, par5, par8, par7, light);

			addTri(matrices, solidRenderer, ppv9, v9, pzv9, par6, par5, par8, par7, light);
			addTri(matrices, solidRenderer, pxv9, v9, ppv9, par6, par5, par8, par7, light);
			addTri(matrices, solidRenderer, pnv9, v9, pxv9, par6, par5, par8, par7, light);
			addTri(matrices, solidRenderer, nzv9, v9, pnv9, par6, par5, par8, par7, light);
			addTri(matrices, solidRenderer, nnv9, v9, nzv9, par6, par5, par8, par7, light);
			addTri(matrices, solidRenderer, nxv9, v9, nnv9, par6, par5, par8, par7, light);
			addTri(matrices, solidRenderer, npv9, v9, nxv9, par6, par5, par8, par7, light);
			addTri(matrices, solidRenderer, pzv9, v9, npv9, par6, par5, par8, par7, light);
		}

		matrices.popPose();
		matrices.popPose();
	}

	private void addFace(PoseStack poseStack, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, float par5, float par6, float par7, float par8, int light) {
		addVertice(poseStack, buffer, v1, par5, par8, light);
		addVertice(poseStack, buffer, v2, par6, par8, light);
		addVertice(poseStack, buffer, v3, par6, par7, light);
		addVertice(poseStack, buffer, v4, par5, par7, light);
	}

	private void addTri(PoseStack poseStack, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, float par5, float par6, float par7, float par8, int light) {
		addVertice(poseStack, buffer, v3, par5, par8, light);
		addVertice(poseStack, buffer, v1, par6, par8, light);
		addVertice(poseStack, buffer, v2, par6, par7, light);
	}

	private void addVertice(PoseStack poseStack, VertexConsumer buffer, Vector3f v, float t, float t2, int light) {
		buffer.addVertex(poseStack.last(), v)
            .setUv(t, t2)
            .setColor(CommonColors.WHITE)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(light)
            .setNormal(poseStack.last(), 0, 1, 0);
	}

    @Override
    public ResourceLocation getTextureLocation(EntityNuclearBlast entity) {
        return null;
    }
}
