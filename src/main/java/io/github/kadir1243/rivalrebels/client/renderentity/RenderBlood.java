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
package io.github.kadir1243.rivalrebels.client.renderentity;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.QuadInstance;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import io.github.kadir1243.rivalrebels.common.entity.EntityBlood;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.neoforge.client.model.quad.MutableQuad;
import org.joml.Vector3f;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class RenderBlood extends EntityRenderer<EntityBlood, EntityRenderState> {
    private final BlockModelResolver blockModelResolver;

    public RenderBlood(EntityRendererProvider.Context context) {
        super(context);
        blockModelResolver = context.getBlockModelResolver();
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    private static final Supplier<QuadHelper.BakedData> BAKED_MODEL = QuadHelper.createBakedModel(buffer -> {
        QuadHelper.addVertice(buffer, new Vector3f(-0.5f, -0.25f, 0), new TextureVertice(0, 0));
        QuadHelper.addVertice(buffer, new Vector3f(0.5f, -0.25f, 0), new TextureVertice(1, 0));
        QuadHelper.addVertice(buffer, new Vector3f(0.5f, 0.75f, 0), new TextureVertice(1, 1));
        QuadHelper.addVertice(buffer, new Vector3f(-0.5f, 0.75f, 0), new TextureVertice(0, 1));
    });

    private static final Supplier<MutableQuad> QUAD = Suppliers.memoize(() -> {
        MutableQuad quad = new MutableQuad();
        quad.setSprite(new Material.Baked(Minecraft.getInstance().getAtlasManager().get(new SpriteId(Sheets.BLOCK_ENTITIES_MAPPER.sheet(), RRIdentifiers.etblood)), false));
        quad.setPosition(0, -0.5f, -0.25f, 0).setUvFromSprite(0, 0, 0);
        quad.setPosition(1, 0.5f, -0.25f, 0).setUvFromSprite(1, 1, 0);
        quad.setPosition(2, 0.5f, 0.75f, 0).setUvFromSprite(2, 1, 1);
        quad.setPosition(3, -0.5f, 0.75f, 0).setUvFromSprite(3, 0, 1);
        return quad;
    });

    @Override
    public void submit(EntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
		poseStack.scale(0.25F, 0.25F, 0.25F);
        poseStack.mulPose(cameraRenderState.orientation);
        QuadInstance instance = new QuadInstance();
        instance.setLightCoords(renderState.lightCoords);
        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(RRIdentifiers.etblood), (pose, buffer) -> buffer.putMutableQuad(pose, QUAD.get(), instance));
        poseStack.popPose();
	}
}
