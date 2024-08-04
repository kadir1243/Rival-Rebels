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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class RenderRhodes extends EntityRenderer<EntityRhodes> {
    private static final RenderType LASER_RENDER_TYPE = RenderType.create(RRIdentifiers.MODID+"_laser_render_type", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 1536, true, false, RenderType.CompositeState.builder()
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

    private static final RenderType RHODES_UNKNOWN_TYPE = RenderType.create(RRIdentifiers.MODID+"_unknown_maybe_i_will_change_this_to_better_name",
        DefaultVertexFormat.POSITION_COLOR_NORMAL,
        VertexFormat.Mode.QUADS,
        1536,
        false,
        true,
        RenderType.CompositeState.builder()
            .setCullState(RenderStateShard.NO_CULL)
            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .createCompositeState(false)
    );
    private static final Function<ResourceLocation, RenderType> RHODES_TEXTURE_RENDER_TYPE = Util.memoize(
        resourceLocation -> {
            RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_SOLID_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setOverlayState(RenderStateShard.OVERLAY)
                .setCullState(RenderStateShard.NO_CULL)
                .createCompositeState(true);
            return RenderType.create(RRIdentifiers.MODID+"entity_solid", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 9999, true, false, compositeState);
        }
    );

    public static final ResourceLocation texture = RRIdentifiers.create("entity/rhodes.png");
    private static final ResourceLocation flame = RRIdentifiers.create("entity/flame");
    public static String[] texfolders = {
			"blocks/",
			"entity/",
			"items/",
			"flags/",
	};
    private static final RenderType TEXTURE_RENDER_TYPE = RHODES_TEXTURE_RENDER_TYPE.apply(RenderRhodes.texture);

    public RenderRhodes(EntityRendererProvider.Context manager) {
        super(manager);
    }

	public static float[] colors = {
		255/255f,     255/255f,     255/255f, //1
		125/255f,     142/255f,     180/255f, //2
		146/255f,      68/255f,      68/255f, //3
		102/255f,     102/255f,      96/255f, //4
		217/255f,     202/255f,     119/255f, //5
		176/255f,     127/255f,     250/255f, //6
		153/255f,     137/255f,      89/255f, //7
		253/255f,     178/255f,     142/255f, //8
		114/255f,     187/255f,     255/255f, //9
		251/255f,     209/255f,      97/255f, //10
		137/255f,     160/255f,     143/255f, //11
		230/255f,     150/255f,     250/255f, //12
		129/255f,     123/255f,     163/255f, //13
		211/255f,     235/255f,     113/255f, //14
		145/255f,     163/255f,     175/255f, //15
		 34/255f,      31/255f,      31/255f, //16
		255/255f,     255/255f,     255/255f, //17
	};

    @Override
    public void render(EntityRhodes entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if (entity.health > 0)
		{
			float ptt = Math.min((entity.ticksSinceLastPacket + tickDelta)/5f, 1);
			if (entity.tickCount<10) ptt = 1;
			matrices.pushPose();
			matrices.scale(entity.scale, entity.scale, entity.scale);

			Font fontrenderer = this.getFont();
            float f = 5F;
            float f1 = 0.016666668F * f;
            matrices.pushPose();
            matrices.translate(0, 16, 0);
            matrices.mulPose(Axis.YP.rotationDegrees((float) -this.entityRenderDispatcher.camera.getPosition().y));
            matrices.mulPose(Axis.XP.rotationDegrees((float) this.entityRenderDispatcher.camera.getPosition().x));
            matrices.scale(-f1, -f1, f1);
            Component name = entity.getDisplayName();
            int color = -1;
            if (entity.rider != null)
            {
                name = name.copy().append(" - ").append(entity.rider.getDisplayName());
            	RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(entity.rider.getGameProfile());
            	if (rrp!=null)
            	{
                    color = switch (rrp.rrteam) {
                        case OMEGA -> 0x44FF44;
                        case SIGMA -> 0x4444FF;
                        case NONE -> -1;
                    };
            	}
            }
            int j = fontrenderer.width(name) / 2;
            VertexConsumer buffer = vertexConsumers.getBuffer(RHODES_UNKNOWN_TYPE);
            buffer.addVertex(matrices.last(), -j - 1, -1, 0).setColor(0, 0, 0, 0.25F).setNormal(matrices.last(), 0.0F, 1.0F, 0.0F);
            buffer.addVertex(matrices.last(), -j - 1, 8, 0).setColor(0, 0, 0, 0.25F).setNormal(matrices.last(), 0.0F, 1.0F, 0.0F);
            buffer.addVertex(matrices.last(), j + 1, 8, 0).setColor(0, 0, 0, 0.25F).setNormal(matrices.last(), 0.0F, 1.0F, 0.0F);
            buffer.addVertex(matrices.last(), j + 1, -1, 0).setColor(0, 0, 0, 0.25F).setNormal(matrices.last(), 0.0F, 1.0F, 0.0F);
            float textBackgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25f);
            int backgroundColor = (int)(textBackgroundOpacity * 255.0f) << 24;
            Matrix4f positionMatrix = matrices.last().pose();
            fontrenderer.drawInBatch(name, -fontrenderer.width(name) / 2F, 0, color, true, positionMatrix, vertexConsumers, Font.DisplayMode.NORMAL, backgroundColor, light);
            fontrenderer.drawInBatch(name, -fontrenderer.width(name) / 2F, 0, color, true, positionMatrix, vertexConsumers, Font.DisplayMode.NORMAL, backgroundColor, light);
            matrices.popPose();

			if (entity.colorType == 16) {
				matrices.pushPose();
                matrices.mulPose(Axis.YP.rotationDegrees(entity.getbodyyaw(ptt)));
				matrices.translate(0, 10f, 0);
		    	ObjModels.renderSolid(ObjModels.booster, RRIdentifiers.etbooster, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
				matrices.pushPose();
				matrices.mulPose(Axis.XP.rotationDegrees(-90));
				matrices.translate(0, 4, -2);
				matrices.scale(2.2f, 2.2f, 2.2f);
				if (entity.b2energy > 0) ObjModels.renderSolid(ObjModels.shuttle, RRIdentifiers.etb2spirit, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
				matrices.popPose();
				matrices.popPose();
			} else {
                VertexConsumer textureBuffer = vertexConsumers.getBuffer(TEXTURE_RENDER_TYPE);
				matrices.mulPose(Axis.YP.rotationDegrees(entity.getbodyyaw(ptt)));

				float leftlegheight = 7.26756f - 15
						+ (Mth.cos((entity.getleftthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (Mth.cos((entity.getleftthighpitch(ptt)+entity.getleftshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);
				float rightlegheight = 7.26756f - 15
						+ (Mth.cos((entity.getrightthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (Mth.cos((entity.getrightthighpitch(ptt)+entity.getrightshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);

				//TORSO
				matrices.pushPose();
                int colorOfRhodes = FastColor.ARGB32.colorFromFloat(1F, colors[entity.colorType*3], colors[entity.colorType*3+1], colors[entity.colorType*3+2]);
				matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);

				matrices.pushPose();
				matrices.mulPose(Axis.XP.rotationDegrees(-90));
				matrices.translate(0, 4, -2);
				if (entity.b2energy > 0) {
                    matrices.pushPose();
                    matrices.scale(2.5F, 2.5F, 2.5F);
                    ObjModels.renderSolid(ObjModels.b2ForSpirit, RRIdentifiers.etb2spirit, matrices, vertexConsumers, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
                    matrices.popPose();
                }
				if (entity.jet && entity.b2energy > 0) {
                    matrices.pushPose();
                    matrices.scale(2.5F, 2.5F, 2.5F);
                    ObjModels.b2jetForRhodes.render(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(flame)), colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
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
					    	ObjModels.rhodes_flame.render(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(flame)), CommonColors.WHITE, light, OverlayTexture.NO_OVERLAY);
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
					    	ObjModels.rhodes_flame.render(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(flame)), colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	}
						matrices.popPose();

					matrices.popPose();

					//HEAD
					matrices.pushPose();
					matrices.translate(0, 5.23244f, 0);
					matrices.mulPose(Axis.XP.rotationDegrees(entity.getheadpitch(ptt)));
					matrices.mulPose(Axis.YP.rotationDegrees(entity.getheadyaw(ptt)));
			    	ObjModels.head.render(matrices, textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
			    	if ((entity.laserOn & 1) == 1) {
			    		ObjModels.rhodes_laser.render(matrices, vertexConsumers.getBuffer(LASER_RENDER_TYPE), FastColor.ARGB32.colorFromFloat(0.5F, 1, 0, 0), light, OverlayTexture.NO_OVERLAY);
			    	} else if ((entity.laserOn & 2) == 2) {
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
					if (entity.itexfolder != -1) {
						try {
					    	ObjModels.renderSolid(ObjModels.flag, RRIdentifiers.create(texfolders[entity.itexfolder] + entity.itexloc + ".png"), matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
						} catch (Exception ignored) {
						}
					}
			    	if (entity.forcefield) {
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
						matrices.mulPose(Axis.YP.rotationDegrees(entity.getheadyaw(ptt)));
				    	ObjModels.renderNoise(ObjModels.ffhead, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
						matrices.popPose();
			    	}
				matrices.popPose();
			}
	    	matrices.popPose();
		}
		if (entity.health < 1) {
            VertexConsumer lightning = vertexConsumers.getBuffer(LIGHTNING);
            matrices.pushPose();
			float elev = Mth.sin((entity.health-tickDelta)*-0.0314159265359F)*15;
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
