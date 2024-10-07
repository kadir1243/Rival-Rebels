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

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.client.model.ModelBlastSphere;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.entity.RhodesType;
import io.github.kadir1243.rivalrebels.common.entity.RhodesTypes;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class RenderRhodes extends EntityRenderer<EntityRhodes> {
    private static final RenderType LASER_RENDER_TYPE = RenderType.create(RRIdentifiers.MODID+"_laser_render_type", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, RenderType.CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
            .setLightmapState(RenderStateShard.LIGHTMAP)
            .setCullState(RenderStateShard.CULL)
            .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
            .setOverlayState(RenderStateShard.OVERLAY)
            .createCompositeState(true));
    private static final RenderType LIGHTNING = RenderType.create(RRIdentifiers.MODID+"_rhodes_lightning",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.TRIANGLES,
        1536,
        RenderType.CompositeState.builder()
            .setCullState(RenderStateShard.NO_CULL)
            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
            .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(false, false))
            .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
            .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
            .createCompositeState(false)
    );

    private static final Function<ResourceLocation, RenderType> RHODES_TEXTURE_RENDER_TYPE_TRIANGLE = Util.memoize(
        resourceLocation -> {
            RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_SOLID_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setOverlayState(RenderStateShard.OVERLAY)
                .setCullState(RenderStateShard.NO_CULL)
                .createCompositeState(true);
            return RenderType.create(RRIdentifiers.MODID+"_rhodes_solid_render", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 9999, true, false, compositeState);
        }
    );
    private static final Function<ResourceLocation, RenderType> RHODES_TEXTURE_RENDER_TYPE_QUAD = Util.memoize(
        resourceLocation -> {
            RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_SOLID_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setOverlayState(RenderStateShard.OVERLAY)
                .setCullState(RenderStateShard.NO_CULL)
                .createCompositeState(true);
            return RenderType.create(RRIdentifiers.MODID+"_rhodes_solid_render", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 9999, true, false, compositeState);
        }
    );

    private static final ResourceLocation flame = RRIdentifiers.create("textures/entity/flame.png");

    public RenderRhodes(EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    public boolean shouldRender(EntityRhodes livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityRhodes entity, BlockPos pos) {
        return super.getBlockLightLevel(entity, pos.atY(255));
    }

    @Override
    protected int getSkyLightLevel(EntityRhodes entity, BlockPos pos) {
        return super.getSkyLightLevel(entity, pos.atY(255));
    }

    @Override
    protected void renderNameTag(EntityRhodes entity, Component displayName, PoseStack pose, MultiBufferSource bufferSource, int packedLight, float partialTick) {
        int color = 0;
        if (entity.rider != null) {
            RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(entity.rider.getGameProfile());
            color = switch (rrp.rrteam) {
                case OMEGA -> 0x44FF44;
                case SIGMA -> 0x4444FF;
                case NONE -> CommonColors.WHITE;
            };
        }
        pose.pushPose();
        pose.translate(0, 16, 0);
        pose.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pose.scale(0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = pose.last().pose();
        float f = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int)(f * 255.0F) << 24;
        Font font = this.getFont();
        float x = (float)(-font.width(displayName) / 2);
        font.drawInBatch(
            displayName, x, 0, color, false, matrix4f, bufferSource, Font.DisplayMode.SEE_THROUGH, j, packedLight
        );
        font.drawInBatch(displayName, x, 0, color, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, packedLight);

        pose.popPose();
    }

    @Override
    public void render(EntityRhodes entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if (entity.getHealth() > 0) {
			float ptt = Math.min((entity.ticksSinceLastPacket + tickDelta)/5f, 1);
			if (entity.tickCount<10) ptt = 1;
			matrices.pushPose();
			matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());

            {
                Component name = entity.getDisplayName();
                if (entity.rider != null) {
                    name = name.copy().append(" - ").append(entity.rider.getDisplayName());
                }
                this.renderNameTag(entity, name, matrices, vertexConsumers, light, tickDelta);
            }

            RhodesType rhodesType = entity.getVariant().value();
            if (rhodesType == RhodesTypes.Space) {
				matrices.pushPose();
                matrices.mulPose(Axis.YP.rotationDegrees(entity.getbodyyaw(ptt)));
				matrices.translate(0, 10f, 0);
		    	ObjModels.renderSolid(ObjModels.booster, RRIdentifiers.etbooster, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
				matrices.pushPose();
				matrices.mulPose(Axis.XP.rotationDegrees(-90));
				matrices.translate(0, 4, -2);
				matrices.scale(2.2f, 2.2f, 2.2f);
				if (entity.getB2Energy() > 0) ObjModels.renderSolid(ObjModels.shuttle, RRIdentifiers.etb2spirit, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
				matrices.popPose();
				matrices.popPose();
			} else {
                VertexConsumer textureBuffer = vertexConsumers.getBuffer(RHODES_TEXTURE_RENDER_TYPE_TRIANGLE.apply(rhodesType.getTexture()));
                matrices.mulPose(Axis.YP.rotationDegrees(entity.getbodyyaw(ptt)));

				float leftlegheight = 7.26756f - 15
						+ (Mth.cos((entity.getleftthighpitch(ptt)+11.99684962f)*Mth.DEG_TO_RAD) * 7.331691240f)
						+ (Mth.cos((entity.getleftthighpitch(ptt)+entity.getleftshinpitch(ptt)-12.2153067f)*Mth.DEG_TO_RAD) * 8.521366426f);
				float rightlegheight = 7.26756f - 15
						+ (Mth.cos((entity.getrightthighpitch(ptt)+11.99684962f)*Mth.DEG_TO_RAD) * 7.331691240f)
						+ (Mth.cos((entity.getrightthighpitch(ptt)+entity.getrightshinpitch(ptt)-12.2153067f)*Mth.DEG_TO_RAD) * 8.521366426f);

				//TORSO
				matrices.pushPose();
                int colorOfRhodes = rhodesType.getColor();
				matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);

				matrices.pushPose();
				matrices.mulPose(Axis.XP.rotationDegrees(-90));
				matrices.translate(0, 4, -2);
				if (entity.getB2Energy() > 0) {
                    matrices.pushPose();
                    matrices.scale(2.5F, 2.5F, 2.5F);
                    ObjModels.renderSolid(ObjModels.b2ForSpirit, RRIdentifiers.etb2spirit, matrices, vertexConsumers, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
                    matrices.popPose();
                }
				if (entity.jet && entity.getB2Energy() > 0) {
                    matrices.pushPose();
                    matrices.scale(2.5F, 2.5F, 2.5F);
                    ObjModels.b2jetForRhodes.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_TRANSLUCENT_TRIANGLES.apply(flame)), colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
                    matrices.popPose();
                }
				matrices.popPose();

		    	ObjModels.torso.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);


					//RIGHT UPPERARM
					matrices.pushPose();
					matrices.translate(-6.4f, 0, 0);
					matrices.mulPose(Axis.YP.rotationDegrees(entity.getrightarmyaw(ptt)));
					matrices.scale(-1, 1, 1);
			    	ObjModels.upperarm.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);

				    	//RIGHT LOWERARM
						matrices.pushPose();
						matrices.translate(0, -1.5f, 0);
						matrices.mulPose(Axis.XP.rotationDegrees(entity.getrightarmpitch(ptt)));
				    	ObjModels.lowerarm.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
						matrices.scale(-1, 1, 1);
				    	ObjModels.rhodes_flamethrower.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
						matrices.popPose();

					matrices.popPose();

					//LEFT UPPERARM
					matrices.pushPose();
					matrices.translate(6.4f, 0, 0);
					matrices.mulPose(Axis.YP.rotationDegrees(entity.getleftarmyaw(ptt)));
			    	ObjModels.upperarm.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);

				    	//LEFT LOWERARM
						matrices.pushPose();
						matrices.translate(0, -1.5f, 0);
						matrices.mulPose(Axis.XP.rotationDegrees(entity.getleftarmpitch(ptt)));
				    	ObjModels.lowerarm.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	ObjModels.rhodes_rocketlauncher.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
						matrices.popPose();

					matrices.popPose();

					//RIGHT THIGH
					matrices.pushPose();
					matrices.translate(0, -7.26756f, -0.27904f);
					matrices.mulPose(Axis.XP.rotationDegrees(entity.getrightthighpitch(ptt)));
					matrices.scale(-1, 1, 1);
                ObjModels.thigh.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);

				    	//RIGHT SHIN
						matrices.pushPose();
						matrices.translate(0, -7.17156f, -1.52395f);
						matrices.mulPose(Axis.XP.rotationDegrees(entity.getrightshinpitch(ptt)));
				    	ObjModels.shin.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	if (entity.isFire()) {
					    	ObjModels.rhodes_flame.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_TRANSLUCENT_TRIANGLES.apply(flame)), CommonColors.WHITE, light, OverlayTexture.NO_OVERLAY);
				    	}
						matrices.popPose();

					matrices.popPose();

					//LEFT THIGH
					matrices.pushPose();
					matrices.translate(0, -7.26756f, -0.27904f);
					matrices.mulPose(Axis.XP.rotationDegrees(entity.getleftthighpitch(ptt)));
			    	ObjModels.thigh.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);

				    	//LEFT SHIN
						matrices.pushPose();
						matrices.translate(0, -7.17156f, -1.52395f);
						matrices.mulPose(Axis.XP.rotationDegrees(entity.getleftshinpitch(ptt)));
				    	ObjModels.shin.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	if (entity.isFire()) {
					    	ObjModels.rhodes_flame.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_TRANSLUCENT_TRIANGLES.apply(flame)), colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	}
						matrices.popPose();

					matrices.popPose();

					//HEAD
					matrices.pushPose();
					matrices.translate(0, 5.23244f, 0);
					matrices.mulPose(Axis.XP.rotationDegrees(entity.getheadpitch(ptt)));
					matrices.mulPose(Axis.YP.rotationDegrees(entity.getViewYRot(ptt)));
			    	ObjModels.head.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
			    	if (entity.isTopLaserEnabled()) {
			    		ObjModels.rhodes_laser.render(matrices, vertexConsumers.getBuffer(LASER_RENDER_TYPE), FastColor.ARGB32.colorFromFloat(0.5F, 1, 0, 0), light, OverlayTexture.NO_OVERLAY);
			    	} else if (entity.isBottomLaserEnabled()) {
						matrices.scale(1, -1, 1);
						//GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
			    		ObjModels.rhodes_laser.render(matrices, vertexConsumers.getBuffer(LASER_RENDER_TYPE), FastColor.ARGB32.colorFromFloat(0.5F, 1, 0, 0), light, OverlayTexture.NO_OVERLAY);
						//GlStateManager.cullFace(GlStateManager.CullFace.BACK);
			    	}
					matrices.popPose();

				matrices.popPose();
				//TORSO
				matrices.pushPose();
					matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);
					if (!entity.getFlagTextureLocation().isBlank()) {
						try {
					    	ObjModels.renderSolid(ObjModels.flag, RRIdentifiers.create(entity.getFlagTextureLocation() + ".png"), matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
						} catch (Exception ignored) {
						}
					}
			    	if (entity.isForceFieldEnabled()) {
			    		ObjModels.renderNoise(ObjModels.fftorso, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
						//RIGHT UPPERARM
						matrices.pushPose();
						matrices.translate(-6.4f, 0, 0);
						matrices.mulPose(Axis.YP.rotationDegrees(entity.getrightarmyaw(ptt)));
						matrices.scale(-1, 1, 1);
				    	ObjModels.renderNoise(ObjModels.ffupperarm, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
					    	//RIGHT LOWERARM
							matrices.pushPose();
							matrices.translate(0, -1.5f, 0);
							matrices.mulPose(Axis.XP.rotationDegrees(entity.getrightarmpitch(ptt)));
					    	ObjModels.renderNoise(ObjModels.fflowerarm, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
							matrices.popPose();
						matrices.popPose();
						//LEFT UPPERARM
						matrices.pushPose();
						matrices.translate(6.4f, 0, 0);
						matrices.mulPose(Axis.YP.rotationDegrees(entity.getleftarmyaw(ptt)));
				    	ObjModels.renderNoise(ObjModels.ffupperarm, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
					    	//LEFT LOWERARM
							matrices.pushPose();
							matrices.translate(0, -1.5f, 0);
							matrices.mulPose(Axis.XP.rotationDegrees(entity.getleftarmpitch(ptt)));
					    	ObjModels.renderNoise(ObjModels.fflowerarm, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
							matrices.popPose();
						matrices.popPose();
						//RIGHT THIGH
						matrices.pushPose();
						matrices.translate(0, -7.26756f, -0.27904f);
						matrices.mulPose(Axis.XP.rotationDegrees(entity.getrightthighpitch(ptt)));
						matrices.scale(-1, 1, 1);
				    	ObjModels.renderNoise(ObjModels.ffthigh, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
					    	//RIGHT SHIN
							matrices.pushPose();
							matrices.translate(0, -7.17156f, -1.52395f);
							matrices.mulPose(Axis.XP.rotationDegrees(entity.getrightshinpitch(ptt)));
					    	ObjModels.renderNoise(ObjModels.ffshin, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
							matrices.popPose();
						matrices.popPose();
						//LEFT THIGH
						matrices.pushPose();
						matrices.translate(0, -7.26756f, -0.27904f);
						matrices.mulPose(Axis.XP.rotationDegrees(entity.getleftthighpitch(ptt)));
				    	ObjModels.renderNoise(ObjModels.ffthigh, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
					    	//LEFT SHIN
							matrices.pushPose();
							matrices.translate(0, -7.17156f, -1.52395f);
							matrices.mulPose(Axis.XP.rotationDegrees(entity.getleftshinpitch(ptt)));
					    	ObjModels.renderNoise(ObjModels.ffshin, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
							matrices.popPose();
						matrices.popPose();
						//HEAD
						matrices.pushPose();
						matrices.translate(0, 5.23244f, 0);
						matrices.mulPose(Axis.XP.rotationDegrees(entity.getheadpitch(ptt)));
						matrices.mulPose(Axis.YP.rotationDegrees(entity.getViewYRot(ptt)));
				    	ObjModels.renderNoise(ObjModels.ffhead, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
						matrices.popPose();
			    	}
				matrices.popPose();
			}
	    	matrices.popPose();
		}
		if (entity.getHealth() < 1) {
            VertexConsumer lightning = vertexConsumers.getBuffer(LIGHTNING);
            matrices.pushPose();
			float elev = Mth.sin((entity.getHealth()-tickDelta)*-0.0314159265359F)*15;
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees(elev * 2));
			matrices.mulPose(Axis.XP.rotationDegrees(elev * 3));
			ModelBlastSphere.renderModel(matrices, lightning, elev, FastColor.ARGB32.colorFromFloat(1F, 1F, 0.25f, 0));
			matrices.popPose();
			matrices.pushPose();
            matrices.mulPose(Axis.YP.rotationDegrees(elev * -2));
            matrices.mulPose(Axis.ZP.rotationDegrees(elev * 4));
			ModelBlastSphere.renderModel(matrices, lightning, elev - 0.2f, FastColor.ARGB32.colorFromFloat(1F, 1, 0.5f, 0));
			matrices.popPose();
			matrices.pushPose();
			matrices.mulPose(Axis.XP.rotationDegrees(elev * -3));
			matrices.mulPose(Axis.ZP.rotationDegrees(elev * 2));
			ModelBlastSphere.renderModel(matrices, lightning, elev - 0.4f, CommonColors.RED);
			matrices.popPose();
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees(elev * -1));
			matrices.mulPose(Axis.ZP.rotationDegrees(elev * 3));
			ModelBlastSphere.renderModel(matrices, lightning, elev - 0.6f, CommonColors.YELLOW);
			matrices.popPose();
			matrices.popPose();
		}
	}

	@Override
    public ResourceLocation getTextureLocation(EntityRhodes entity)
	{
		return null;
	}
}
