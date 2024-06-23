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
package assets.rivalrebels.client.itemrenders;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelRocketLauncherBody;
import assets.rivalrebels.client.model.ModelRocketLauncherHandle;
import assets.rivalrebels.client.model.ModelRocketLauncherTube;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class RocketLauncherRenderer implements DynamicItemRenderer {
    public static final Material ROCKET_LAUNCHER_HANDLE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etrocketlauncherhandle);
    public static final Material ROCKET_LAUNCHER_TUBE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etrocketlaunchertube);
    public static final Material ROCKET_LAUNCHER_BODY_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etrocketlauncherbody);

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(0.4f, 0.35f, -0.03f);
		matrices.mulPose(new Quaternionf(-55, 0.0F, 0.0F, 1.0F));
		matrices.translate(0f, 0.05f, 0.05f);
		if (mode.firstPerson()) matrices.scale(1, 1, -1);
		matrices.pushPose();
		matrices.translate(0.22f, -0.025f, 0f);
		matrices.mulPose(new Quaternionf(90, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
        ModelRocketLauncherHandle.render(matrices, ROCKET_LAUNCHER_HANDLE_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		if (stack.isEnchanted()) {
			ModelRocketLauncherHandle.render(matrices, cellularNoise, light, overlay);
		}
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f, 0.31f, 0f);
		matrices.mulPose(new Quaternionf(90, 0.0F, 0.0F, 1.0F));
		matrices.mulPose(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.4f, 0.4f, 0.4f);
		ModelRocketLauncherBody.render(matrices, ROCKET_LAUNCHER_BODY_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		if (stack.isDamaged()) {
			ModelRocketLauncherBody.render(matrices, cellularNoise, light, overlay);
		}
		matrices.popPose();

		float s = 0.0812f;

		matrices.pushPose();
		matrices.translate(-0.07f + s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
        VertexConsumer rocketLauncherTubeTextureVertexConsumer = ROCKET_LAUNCHER_TUBE_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
		ModelRocketLauncherTube.render(matrices, rocketLauncherTubeTextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f - s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketLauncherTubeTextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f + s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketLauncherTubeTextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f - s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketLauncherTubeTextureVertexConsumer, light, overlay);
		matrices.popPose();

		// ---

		matrices.pushPose();
		matrices.translate(-0.07f + s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketLauncherTubeTextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f - s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketLauncherTubeTextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f + s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketLauncherTubeTextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f - s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketLauncherTubeTextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.popPose();
	}
}

