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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.client.objfileloader.WavefrontObject;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public class RenderRhodes extends EntityRenderer<EntityRhodes> {
    public static final Material RHODES_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.create("entity/rhodes.png"));
    public static final Material B2_SPIRIT_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etb2spirit);
    public static final Material BOOSTER_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etbooster);
    public static final Material FLAME_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.create("entity/flame"));
    public static ResourceLocation texture = RRIdentifiers.create("entity/rhodes");
    public static WavefrontObject head = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/head.obj"));
    public static WavefrontObject torso = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/torso.obj"));
    public static WavefrontObject flag = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/flag.obj"));
    public static WavefrontObject upperarm = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/upperarm.obj"));
    public static WavefrontObject lowerarm = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/lowerarm.obj"));
    public static WavefrontObject flamethrower = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/flamethrower.obj"));
    public static WavefrontObject rocketlauncher = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/rocketlauncher.obj"));
    public static WavefrontObject thigh = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/thigh.obj"));
    public static WavefrontObject shin = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/shin.obj"));
    private static WavefrontObject booster = WavefrontObject.loadModel(RRIdentifiers.create("models/booster.obj"));
    private static WavefrontObject flame = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/flame.obj"));
    private static WavefrontObject laser = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/laser.obj"));
    public static WavefrontObject ffhead = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/ffhead.obj"));
    public static WavefrontObject fftorso = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/fftorso.obj"));
    public static WavefrontObject ffupperarm = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/ffupperarm.obj"));
    public static WavefrontObject fflowerarm = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/fflowerarm.obj"));
    public static WavefrontObject ffthigh = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/ffthigh.obj"));
    public static WavefrontObject ffshin = WavefrontObject.loadModel(RRIdentifiers.create("models/rhodes/ffshin.obj"));
	public static final ModelFromObj	md = ModelFromObj.readObjFile("d.obj");
	public static final ModelFromObj	b2jet = ModelFromObj.readObjFile("s.obj");
	public static String[] texfolders = {
			"blocks/",
			"entity/",
			"items/",
			"flags/",
	};

    static {
        md.scale(2.5f, 2.5f, 2.5f);
        b2jet.scale(2.5f, 2.5f, 2.5f);
    }

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
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.solid());
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
            // FIXME: GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            matrices.mulPose(new Quaternionf((float) -this.entityRenderDispatcher.camera.getPosition().y, 0.0F, 1.0F, 0.0F));
            matrices.mulPose(new Quaternionf((float) this.entityRenderDispatcher.camera.getPosition().x, 1.0F, 0.0F, 0.0F));
            matrices.scale(-f1, -f1, f1);
            RenderSystem.depthMask(false);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
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
            buffer.addVertex(matrices.last(), -j - 1, -1, 0).setColor(0, 0, 0, 0.25F);
            buffer.addVertex(matrices.last(), -j - 1, 8, 0).setColor(0, 0, 0, 0.25F);
            buffer.addVertex(matrices.last(), j + 1, 8, 0).setColor(0, 0, 0, 0.25F);
            buffer.addVertex(matrices.last(), j + 1, -1, 0).setColor(0, 0, 0, 0.25F);
            float textBackgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25f);
            int backgroundColor = (int)(textBackgroundOpacity * 255.0f) << 24;
            Matrix4f positionMatrix = matrices.last().pose();
            fontrenderer.drawInBatch(name, -fontrenderer.width(name) / 2F, 0, color, true, positionMatrix, vertexConsumers, Font.DisplayMode.NORMAL, backgroundColor, light);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            fontrenderer.drawInBatch(name, -fontrenderer.width(name) / 2F, 0, color, true, positionMatrix, vertexConsumers, Font.DisplayMode.NORMAL, backgroundColor, light);
            RenderSystem.disableBlend();
            matrices.popPose();

			if (entity.colorType == 16) {
				matrices.pushPose();
                matrices.mulPose(new Quaternionf(entity.getbodyyaw(ptt), 0, 1, 0));
				matrices.translate(0, 10f, 0);
		    	booster.render(BOOSTER_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, OverlayTexture.NO_OVERLAY);
				matrices.pushPose();
				matrices.mulPose(new Quaternionf(-90f, 1, 0, 0));
				matrices.translate(0, 4, -2);
				matrices.scale(2.2f, 2.2f, 2.2f);
				if (entity.b2energy > 0) RenderB2Spirit.shuttle.render(B2_SPIRIT_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, OverlayTexture.NO_OVERLAY);
				matrices.popPose();
				matrices.popPose();
			} else {
                VertexConsumer textureBuffer = RHODES_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
				RenderSystem.disableCull();
				matrices.mulPose(new Quaternionf(entity.getbodyyaw(ptt), 0, 1, 0));

				float leftlegheight = 7.26756f - 15
						+ (Mth.cos((entity.getleftthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (Mth.cos((entity.getleftthighpitch(ptt)+entity.getleftshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);
				float rightlegheight = 7.26756f - 15
						+ (Mth.cos((entity.getrightthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (Mth.cos((entity.getrightthighpitch(ptt)+entity.getrightshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);

				//TORSO
				matrices.pushPose();
                Vector4f colorOfRhodes = new Vector4f(colors[entity.colorType*3], colors[entity.colorType*3+1], colors[entity.colorType*3+2], 1);
				matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);

				matrices.pushPose();
				matrices.mulPose(new Quaternionf(-90f, 1, 0, 0));
				matrices.translate(0, 4, -2);
				if (entity.b2energy > 0) md.render(B2_SPIRIT_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				if (entity.jet && entity.b2energy > 0) b2jet.render(FLAME_TEXTURE.buffer(vertexConsumers, RenderType::entityTranslucent), colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				matrices.popPose();

		    	torso.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);


					//RIGHT UPPERARM
					matrices.pushPose();
					matrices.translate(-6.4f, 0, 0);
					matrices.mulPose(new Quaternionf(entity.getrightarmyaw(ptt), 0, 1, 0));
					matrices.scale(-1, 1, 1);
			    	upperarm.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);

				    	//RIGHT LOWERARM
						matrices.pushPose();
						matrices.translate(0, -1.5f, 0);
						matrices.mulPose(new Quaternionf(entity.getrightarmpitch(ptt), 1, 0, 0));
				    	lowerarm.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
						matrices.scale(-1, 1, 1);
				    	flamethrower.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
						matrices.popPose();

					matrices.popPose();

					//LEFT UPPERARM
					matrices.pushPose();
					matrices.translate(6.4f, 0, 0);
					matrices.mulPose(new Quaternionf(entity.getleftarmyaw(ptt), 0, 1, 0));
			    	upperarm.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);

				    	//LEFT LOWERARM
						matrices.pushPose();
						matrices.translate(0, -1.5f, 0);
						matrices.mulPose(new Quaternionf(entity.getleftarmpitch(ptt), 1, 0, 0));
				    	lowerarm.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	rocketlauncher.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
						matrices.popPose();

					matrices.popPose();

					//RIGHT THIGH
					matrices.pushPose();
					matrices.translate(0, -7.26756f, -0.27904f);
					matrices.mulPose(new Quaternionf(entity.getrightthighpitch(ptt), 1, 0, 0));
					matrices.scale(-1, 1, 1);
			    	thigh.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);

				    	//RIGHT SHIN
						matrices.pushPose();
						matrices.translate(0, -7.17156f, -1.52395f);
						matrices.mulPose(new Quaternionf(entity.getrightshinpitch(ptt), 1, 0, 0));
				    	shin.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	if (entity.isFire()) {
					    	flame.render(FLAME_TEXTURE.buffer(vertexConsumers, RenderType::entityTranslucent), light, OverlayTexture.NO_OVERLAY);
				    	}
						matrices.popPose();

					matrices.popPose();

					//LEFT THIGH
					matrices.pushPose();
					matrices.translate(0, -7.26756f, -0.27904f);
					matrices.mulPose(new Quaternionf(entity.getleftthighpitch(ptt), 1, 0, 0));
			    	thigh.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);

				    	//LEFT SHIN
						matrices.pushPose();
						matrices.translate(0, -7.17156f, -1.52395f);
						matrices.mulPose(new Quaternionf(entity.getleftshinpitch(ptt), 1, 0, 0));
				    	shin.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	if (entity.isFire()) {
					    	flame.render(FLAME_TEXTURE.buffer(vertexConsumers, RenderType::entityTranslucent), colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
				    	}
						matrices.popPose();

					matrices.popPose();

					//HEAD
					matrices.pushPose();
					matrices.translate(0, 5.23244f, 0);
					matrices.mulPose(new Quaternionf(entity.getheadpitch(ptt), 1, 0, 0));
					matrices.mulPose(new Quaternionf(entity.getheadyaw(ptt), 0, 1, 0));
			    	head.render(textureBuffer, colorOfRhodes, light, OverlayTexture.NO_OVERLAY);
					RenderSystem.enableCull();
		    		RenderSystem.enableBlend();
                RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE);
			    	if ((entity.laserOn & 1) == 1) {
			    		laser.render(buffer, new Vector4f(1, 0, 0, 0.5F), light, OverlayTexture.NO_OVERLAY);
			    	} else if ((entity.laserOn & 2) == 2) {
						matrices.scale(1, -1, 1);
						//GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
			    		laser.render(vertexConsumers.getBuffer(RenderType.solid()), new Vector4f(1, 0, 0, 0.5F), light, OverlayTexture.NO_OVERLAY);
						//GlStateManager.cullFace(GlStateManager.CullFace.BACK);
			    	}
			    	RenderSystem.disableCull();
		    		RenderSystem.disableBlend();
					matrices.popPose();

				matrices.popPose();
				//TORSO
				matrices.pushPose();
					matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);
					if (entity.itexfolder != -1) {
						try {
					    	flag.render(vertexConsumers.getBuffer(RenderType.entitySolid(RRIdentifiers.create(texfolders[entity.itexfolder] + entity.itexloc + ".png"))), light, OverlayTexture.NO_OVERLAY);
						} catch (Exception ignored) {
						}
					}
			    	if (entity.forcefield)
			    	{
                        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
			    		fftorso.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
						//RIGHT UPPERARM
						matrices.pushPose();
						matrices.translate(-6.4f, 0, 0);
						matrices.mulPose(new Quaternionf(entity.getrightarmyaw(ptt), 0, 1, 0));
						matrices.scale(-1, 1, 1);
				    	ffupperarm.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
					    	//RIGHT LOWERARM
							matrices.pushPose();
							matrices.translate(0, -1.5f, 0);
							matrices.mulPose(new Quaternionf(entity.getrightarmpitch(ptt), 1, 0, 0));
					    	fflowerarm.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
							matrices.popPose();
						matrices.popPose();
						//LEFT UPPERARM
						matrices.pushPose();
						matrices.translate(6.4f, 0, 0);
						matrices.mulPose(new Quaternionf(entity.getleftarmyaw(ptt), 0, 1, 0));
				    	ffupperarm.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
					    	//LEFT LOWERARM
							matrices.pushPose();
							matrices.translate(0, -1.5f, 0);
							matrices.mulPose(new Quaternionf(entity.getleftarmpitch(ptt), 1, 0, 0));
					    	fflowerarm.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
							matrices.popPose();
						matrices.popPose();
						//RIGHT THIGH
						matrices.pushPose();
						matrices.translate(0, -7.26756f, -0.27904f);
						matrices.mulPose(new Quaternionf(entity.getrightthighpitch(ptt), 1, 0, 0));
						matrices.scale(-1, 1, 1);
				    	ffthigh.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
					    	//RIGHT SHIN
							matrices.pushPose();
							matrices.translate(0, -7.17156f, -1.52395f);
							matrices.mulPose(new Quaternionf(entity.getrightshinpitch(ptt), 1, 0, 0));
					    	ffshin.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
							matrices.popPose();
						matrices.popPose();
						//LEFT THIGH
						matrices.pushPose();
						matrices.translate(0, -7.26756f, -0.27904f);
						matrices.mulPose(new Quaternionf(entity.getleftthighpitch(ptt), 1, 0, 0));
				    	ffthigh.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
					    	//LEFT SHIN
							matrices.pushPose();
							matrices.translate(0, -7.17156f, -1.52395f);
							matrices.mulPose(new Quaternionf(entity.getleftshinpitch(ptt), 1, 0, 0));
					    	ffshin.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
							matrices.popPose();
						matrices.popPose();
						//HEAD
						matrices.pushPose();
						matrices.translate(0, 5.23244f, 0);
						matrices.mulPose(new Quaternionf(entity.getheadpitch(ptt), 1, 0, 0));
						matrices.mulPose(new Quaternionf(entity.getheadyaw(ptt), 0, 1, 0));
				    	ffhead.render(cellularNoise, light, OverlayTexture.NO_OVERLAY);
						matrices.popPose();
			    	}
				matrices.popPose();
			}
	    	matrices.popPose();
		}
		if (entity.health < 1)
		{
			matrices.pushPose();
            matrices.translate((float) entity.getX(), (float) entity.getY(), (float) entity.getZ());
			RenderSystem.disableCull();
    		RenderSystem.enableBlend();
    		RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE);
			double elev = Math.sin((entity.health-tickDelta)*-0.0314159265359)*15;
			matrices.pushPose();
			matrices.mulPose(new Quaternionf((float) (elev * 2), 0, 1, 0));
			matrices.mulPose(new Quaternionf((float) (elev * 3), 1, 0, 0));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) elev, 1, 0.25f, 0, 1f);
			matrices.popPose();
			matrices.pushPose();
            matrices.mulPose(new Quaternionf((float) (elev * -2), 0, 1, 0));
            matrices.mulPose(new Quaternionf((float) (elev * 4), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
			matrices.popPose();
			matrices.pushPose();
			matrices.mulPose(new Quaternionf((float) (elev * -3), 1, 0, 0));
			matrices.mulPose(new Quaternionf((float) (elev * 2), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.4f), 1, 0, 0, 1f);
			matrices.popPose();
			matrices.pushPose();
			matrices.mulPose(new Quaternionf((float) (elev * -1), 0, 1, 0));
			matrices.mulPose(new Quaternionf((float) (elev * 3), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.6f), 1, 1, 0, 1);
			matrices.popPose();
    		RenderSystem.disableBlend();
			matrices.popPose();
		}
	}

	@Override
    public ResourceLocation getTextureLocation(EntityRhodes entity)
	{
		return null;
	}
}
