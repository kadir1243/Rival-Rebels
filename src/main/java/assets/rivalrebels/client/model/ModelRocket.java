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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
public class ModelRocket implements UnbakedModel, BakedModel {
	public static final ModelRocket INSTANCE_WITH_FIN = new ModelRocket(true);
    public static final ModelRocket INSTANCE_WITHOUT_FIN = new ModelRocket(false);
    private static final Vector3f	vy1		= new Vector3f(0, 0, 0);
	private static final Vector3f	vy2		= new Vector3f(0, 2.5f, 0);
	private static final Vector3f	vpx1	= new Vector3f(0.5f, 0, 0);
	private static final Vector3f	vnx1	= new Vector3f(-0.5f, 0, 0);
	private static final Vector3f	vpz1	= new Vector3f(0, 0, 0.5f);
	private static final Vector3f	vnz1	= new Vector3f(0, 0, -0.5f);
	private static final Vector3f	vpxpz1	= new Vector3f(0.3535533f, 0, 0.3535533f);
	private static final Vector3f	vpxnz1	= new Vector3f(0.3535533f, 0, -0.3535533f);
	private static final Vector3f	vnxpz1	= new Vector3f(-0.3535533f, 0, 0.3535533f);
	private static final Vector3f	vnxnz1	= new Vector3f(-0.3535533f, 0, -0.3535533f);
	private static final Vector3f	vpx2	= new Vector3f(0.5f, 2, 0);
	private static final Vector3f	vnx2	= new Vector3f(-0.5f, 2, 0);
	private static final Vector3f	vpz2	= new Vector3f(0, 2, 0.5f);
	private static final Vector3f	vnz2	= new Vector3f(0, 2, -0.5f);
	private static final Vector3f	vpxpz2	= new Vector3f(0.3535533f, 2, 0.3535533f);
	private static final Vector3f	vpxnz2	= new Vector3f(0.3535533f, 2, -0.3535533f);
	private static final Vector3f	vnxpz2	= new Vector3f(-0.3535533f, 2, 0.3535533f);
	private static final Vector3f	vnxnz2	= new Vector3f(-0.3535533f, 2, -0.3535533f);
	private static final Vector3f	vpx3	= new Vector3f(1, -0.2f, 0);
	private static final Vector3f	vnx3	= new Vector3f(-1, -0.2f, 0);
	private static final Vector3f	vpz3	= new Vector3f(0, -0.2f, 1);
	private static final Vector3f	vnz3	= new Vector3f(0, -0.2f, -1);
	private static final Vector3f	vpx4	= new Vector3f(1, 1f, 0);
	private static final Vector3f	vnx4	= new Vector3f(-1, 1f, 0);
	private static final Vector3f	vpz4	= new Vector3f(0, 1f, 1);
	private static final Vector3f	vnz4	= new Vector3f(0, 1f, -1);
	private static final float	tx1		= 0;
	private static final float	tx2		= 0.28125f;
	private static final float	tx3		= 0.375f;
	private static final float	tx4		= 0.5625f;
	private static final float	tx5		= 0.65625f;
	private static final float	ty1		= 0;
	private static final float	ty2		= 0.09375f;
	private static final float	ty3		= 0.1875f;
    private final boolean fins;
    private Mesh mesh;

    public ModelRocket(boolean fins) {
        this.fins = fins;
    }

    public static void render(ModelManager manager, ModelBlockRenderer modelRenderer, PoseStack pose, MultiBufferSource vertexConsumers, ResourceLocation texture, boolean fins, int light, int overlay) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.entityCutout(texture));
        pose.pushPose();
		pose.scale(0.125f, 0.25f, 0.125f);

		BakedModel model = manager.getModel(fins ? RRModelLoadingPlugin.ROCKET_WITH_FINS : RRModelLoadingPlugin.ROCKET_WITHOUT_FINS);
        modelRenderer.renderModel(pose.last(), buffer, null, model, 1, 1, 1, light, overlay);

		pose.popPose();
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

        QuadHelper.addFace(emitter, vpx1, vpx2, vpxpz2, vpxpz1, tx1, tx2, ty1, ty2);
        QuadHelper.addFace(emitter, vpxpz1, vpxpz2, vpz2, vpz1, tx1, tx2, ty1, ty2);
        QuadHelper.addFace(emitter, vpz1, vpz2, vnxpz2, vnxpz1, tx1, tx2, ty1, ty2);
        QuadHelper.addFace(emitter, vnxpz1, vnxpz2, vnx2, vnx1, tx1, tx2, ty1, ty2);
        QuadHelper.addFace(emitter, vnx1, vnx2, vnxnz2, vnxnz1, tx1, tx2, ty1, ty2);
        QuadHelper.addFace(emitter, vnxnz1, vnxnz2, vnz2, vnz1, tx1, tx2, ty1, ty2);
        QuadHelper.addFace(emitter, vnz1, vnz2, vpxnz2, vpxnz1, tx1, tx2, ty1, ty2);
        QuadHelper.addFace(emitter, vpxnz1, vpxnz2, vpx2, vpx1, tx1, tx2, ty1, ty2);

        QuadHelper.addFace(emitter, vpxpz2, vpx2, vy2, vpz2, tx2, tx3, ty1, ty2);
        QuadHelper.addFace(emitter, vnxpz2, vpz2, vy2, vnx2, tx2, tx3, ty1, ty2);
        QuadHelper.addFace(emitter, vnxnz2, vnx2, vy2, vnz2, tx2, tx3, ty1, ty2);
        QuadHelper.addFace(emitter, vpxnz2, vnz2, vy2, vpx2, tx2, tx3, ty1, ty2);

        QuadHelper.addFace(emitter, vpx1, vpxpz1, vpz1, vy1, tx4, tx5, ty1, ty2);
        QuadHelper.addFace(emitter, vpz1, vnxpz1, vnx1, vy1, tx4, tx5, ty1, ty2);
        QuadHelper.addFace(emitter, vnx1, vnxnz1, vnz1, vy1, tx4, tx5, ty1, ty2);
        QuadHelper.addFace(emitter, vnz1, vpxnz1, vpx1, vy1, tx4, tx5, ty1, ty2);

        if (fins) {
            QuadHelper.addFace(emitter, vnx3, vpx3, vpx4, vnx4, tx3, tx4, ty1, ty3);
            QuadHelper.addFace(emitter, vpx3, vnx3, vnx4, vpx4, tx3, tx4, ty1, ty3);
            QuadHelper.addFace(emitter, vnz3, vpz3, vpz4, vnz4, tx3, tx4, ty1, ty3);
            QuadHelper.addFace(emitter, vpz3, vnz3, vnz4, vpz4, tx3, tx4, ty1, ty3);
        }

        mesh = builder.build();

        return this;
    }
}
