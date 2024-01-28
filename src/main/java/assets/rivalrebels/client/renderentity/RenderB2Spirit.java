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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.entity.EntityB2Spirit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderB2Spirit extends EntityRenderer<EntityB2Spirit>
{
	private final ModelFromObj	b2;
	public static ModelFromObj	shuttle = ModelFromObj.readObjFile("shuttle.obj");
	public static ModelFromObj	tupolev = ModelFromObj.readObjFile("tupolev.obj");

	public RenderB2Spirit(EntityRendererFactory.Context manager)
	{
        super(manager);
        b2 = ModelFromObj.readObjFile("d.obj");
		b2.scale(3, 3, 3);
	}

    @Override
    public void render(EntityB2Spirit entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.multiply(new Quaternion(entity.getYaw(), 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternion(entity.getPitch(), 1.0F, 0.0F, 0.0F));
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        if (RRConfig.CLIENT.getBomberType().equals("sh")) {
			matrices.scale(3.0f, 3.0f, 3.0f);
			MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etb2spirit);
			shuttle.render(buffer);
		} else if (RRConfig.CLIENT.getBomberType().equals("tu")) {
			MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.ettupolev);
			tupolev.render(buffer);
		} else {
			MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etb2spirit);
			b2.render(buffer);
		}
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityB2Spirit entity) {
        return null;
    }
}
