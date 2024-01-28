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

import assets.rivalrebels.client.model.ModelDisk;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import assets.rivalrebels.common.entity.EntityRoddiskRep;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class RenderRoddiskRep extends EntityRenderer<EntityRoddiskRep>
{
	int					er	= 0;
	private ModelDisk	model;

	public RenderRoddiskRep(EntityRendererFactory.Context manager)
	{
        super(manager);
		model = new ModelDisk();
	}

    @Override
    public void render(EntityRoddiskRep entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		er += 13.46;
        RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
        RenderSystem.enableBlend();
		RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
        RenderSystem.disableBlend();
        matrices.push();
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(entity.getPitch()));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.getYaw() - 90.0f + er));

		model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getSolid()));

        matrices.pop();
        matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityRoddiskRep entity) {
        return null;
    }
}
