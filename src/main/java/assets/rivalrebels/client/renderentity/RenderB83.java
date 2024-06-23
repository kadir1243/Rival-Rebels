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
import assets.rivalrebels.common.entity.EntityB83;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RenderB83 extends EntityRenderer<EntityB83>
{
    public static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etb83);
    public static final ModelFromObj md = ModelFromObj.readObjFile("c.obj");

	public RenderB83(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityB83 entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.scale(RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale());
        matrices.mulPose(new Quaternionf(yaw - 90.0f, 0.0F, 1.0F, 0.0F));
        matrices.mulPose(new Quaternionf(entity.getXRot() - 180.0f, 0.0F, 0.0F, 1.0F));
        md.render(TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light);
        matrices.popPose();
    }

	@Override
    public ResourceLocation getTextureLocation(EntityB83 entity)
	{
		return RRIdentifiers.etb83;
	}
}
