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
package assets.rivalrebels.client.model;

import assets.rivalrebels.client.renderhelper.QuadHelper;
import assets.rivalrebels.client.renderhelper.TextureFace;
import assets.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ModelJump implements UnbakedModel, BakedModel {
    public static final ModelJump INSTANCE = new ModelJump();
	private static final float s = 0.501F;
	private static final float t = 0.25F;

	private static final Vector3f v1 = new Vector3f(s, t, s);
	private static final Vector3f v2 = new Vector3f(s, t, -s);
	private static final Vector3f v3 = new Vector3f(-s, t, -s);
	private static final Vector3f v4 = new Vector3f(-s, t, s);

	private static final Vector3f v5 = new Vector3f(s, -t, s);
	private static final Vector3f v6 = new Vector3f(s, -t, -s);
	private static final Vector3f v7 = new Vector3f(-s, -t, -s);
	private static final Vector3f v8 = new Vector3f(-s, -t, s);
    private Mesh mesh;

    public static void renderModel(ModelManager manager, ModelBlockRenderer modelRenderer, PoseStack pose, VertexConsumer buffer, int light, int overlay) {
        BakedModel model = manager.getModel(RRModelLoadingPlugin.JUMP_BLOCK);
        modelRenderer.renderModel(pose.last(), buffer, null, model, 1, 1, 1, light, overlay);
	}

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
        return ModelHelper.toQuadLists(mesh)[ModelHelper.NULL_FACE_ID];
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(TextureManager.INTENTIONAL_MISSING_TEXTURE);
    }

    @Override
    public ItemTransforms getTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return List.of();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {
    }

    @Nullable
    @Override
    public BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state) {
        MeshBuilder builder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        TextureFace textureFace = new TextureFace(new TextureVertice(0, 0), new TextureVertice(1, 0), new TextureVertice(1, 1), new TextureVertice(0, 1));
        QuadHelper.addFace(emitter, v2, v1, v4, v3, textureFace);
        QuadHelper.addFace(emitter, v5, v6, v7, v8, textureFace);

        mesh = builder.build();
        return this;
    }
}
