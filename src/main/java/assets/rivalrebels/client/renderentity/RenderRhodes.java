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
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public class RenderRhodes extends EntityRenderer<EntityRhodes> {
    public static final SpriteIdentifier RHODES_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/rhodes.png"));
    public static final SpriteIdentifier B2_SPIRIT_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etb2spirit);
    public static final SpriteIdentifier BOOSTER_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etbooster);
    public static final SpriteIdentifier FLAME_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.create("entity/flame"));
    public static Identifier texture = RRIdentifiers.create("entity/rhodes");
    public static WavefrontObject head = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/head.obj"));
    public static WavefrontObject torso = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/torso.obj"));
    public static WavefrontObject flag = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/flag.obj"));
    public static WavefrontObject upperarm = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/upperarm.obj"));
    public static WavefrontObject lowerarm = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/lowerarm.obj"));
    public static WavefrontObject flamethrower = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/flamethrower.obj"));
    public static WavefrontObject rocketlauncher = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/rocketlauncher.obj"));
    public static WavefrontObject thigh = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/thigh.obj"));
    public static WavefrontObject shin = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/shin.obj"));
    private static WavefrontObject booster = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/booster.obj"));
    private static WavefrontObject flame = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/flame.obj"));
    private static WavefrontObject laser = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/laser.obj"));
    public static WavefrontObject ffhead = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/ffhead.obj"));
    public static WavefrontObject fftorso = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/fftorso.obj"));
    public static WavefrontObject ffupperarm = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/ffupperarm.obj"));
    public static WavefrontObject fflowerarm = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/fflowerarm.obj"));
    public static WavefrontObject ffthigh = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/ffthigh.obj"));
    public static WavefrontObject ffshin = WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/ffshin.obj"));
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

	public RenderRhodes(EntityRendererFactory.Context manager) {
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
    public void render(EntityRhodes entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        if (entity.health > 0)
		{
			float ptt = Math.min((entity.ticksSinceLastPacket + tickDelta)/5f, 1);
			if (entity.age<10) ptt = 1;
			matrices.push();
			matrices.scale(entity.scale, entity.scale, entity.scale);

			TextRenderer fontrenderer = this.getTextRenderer();
            float f = 5F;
            float f1 = 0.016666668F * f;
            matrices.push();
            matrices.translate(0, 16, 0);
            // FIXME: GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            matrices.multiply(new Quaternionf((float) -this.dispatcher.camera.getPos().y, 0.0F, 1.0F, 0.0F));
            matrices.multiply(new Quaternionf((float) this.dispatcher.camera.getPos().x, 1.0F, 0.0F, 0.0F));
            matrices.scale(-f1, -f1, f1);
            RenderSystem.depthMask(false);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Text name = entity.getDisplayName();
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
            int j = fontrenderer.getWidth(name) / 2;
            buffer.vertex(-j - 1, -1, 0.0D).color(0, 0, 0, 0.25F).next();
            buffer.vertex(-j - 1, 8, 0.0D).color(0, 0, 0, 0.25F).next();
            buffer.vertex(j + 1, 8, 0.0D).color(0, 0, 0, 0.25F).next();
            buffer.vertex(j + 1, -1, 0.0D).color(0, 0, 0, 0.25F).next();
            float textBackgroundOpacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
            int backgroundColor = (int)(textBackgroundOpacity * 255.0f) << 24;
            Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
            fontrenderer.draw(name, -fontrenderer.getWidth(name) / 2F, 0, color, true, positionMatrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, backgroundColor, light);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            fontrenderer.draw(name, -fontrenderer.getWidth(name) / 2F, 0, color, true, positionMatrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, backgroundColor, light);
            RenderSystem.disableBlend();
            matrices.pop();

			if (entity.colorType == 16) {
				matrices.push();
                matrices.multiply(new Quaternionf(entity.getbodyyaw(ptt), 0, 1, 0));
				matrices.translate(0, 10f, 0);
		    	booster.render(BOOSTER_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, OverlayTexture.DEFAULT_UV);
				matrices.push();
				matrices.multiply(new Quaternionf(-90f, 1, 0, 0));
				matrices.translate(0, 4, -2);
				matrices.scale(2.2f, 2.2f, 2.2f);
				if (entity.b2energy > 0) RenderB2Spirit.shuttle.render(B2_SPIRIT_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, OverlayTexture.DEFAULT_UV);
				matrices.pop();
				matrices.pop();
			} else {
                VertexConsumer textureBuffer = RHODES_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
				RenderSystem.disableCull();
				matrices.multiply(new Quaternionf(entity.getbodyyaw(ptt), 0, 1, 0));

				float leftlegheight = 7.26756f - 15
						+ (MathHelper.cos((entity.getleftthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (MathHelper.cos((entity.getleftthighpitch(ptt)+entity.getleftshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);
				float rightlegheight = 7.26756f - 15
						+ (MathHelper.cos((entity.getrightthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (MathHelper.cos((entity.getrightthighpitch(ptt)+entity.getrightshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);

				//TORSO
				matrices.push();
                Vector4f colorOfRhodes = new Vector4f(colors[entity.colorType*3], colors[entity.colorType*3+1], colors[entity.colorType*3+2], 1);
				matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);

				matrices.push();
				matrices.multiply(new Quaternionf(-90f, 1, 0, 0));
				matrices.translate(0, 4, -2);
				if (entity.b2energy > 0) md.render(B2_SPIRIT_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
				if (entity.jet && entity.b2energy > 0) b2jet.render(FLAME_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityTranslucent), colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
				matrices.pop();

		    	torso.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);


					//RIGHT UPPERARM
					matrices.push();
					matrices.translate(-6.4f, 0, 0);
					matrices.multiply(new Quaternionf(entity.getrightarmyaw(ptt), 0, 1, 0));
					matrices.scale(-1, 1, 1);
			    	upperarm.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);

				    	//RIGHT LOWERARM
						matrices.push();
						matrices.translate(0, -1.5f, 0);
						matrices.multiply(new Quaternionf(entity.getrightarmpitch(ptt), 1, 0, 0));
				    	lowerarm.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
						matrices.scale(-1, 1, 1);
				    	flamethrower.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
						matrices.pop();

					matrices.pop();

					//LEFT UPPERARM
					matrices.push();
					matrices.translate(6.4f, 0, 0);
					matrices.multiply(new Quaternionf(entity.getleftarmyaw(ptt), 0, 1, 0));
			    	upperarm.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);

				    	//LEFT LOWERARM
						matrices.push();
						matrices.translate(0, -1.5f, 0);
						matrices.multiply(new Quaternionf(entity.getleftarmpitch(ptt), 1, 0, 0));
				    	lowerarm.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
				    	rocketlauncher.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
						matrices.pop();

					matrices.pop();

					//RIGHT THIGH
					matrices.push();
					matrices.translate(0, -7.26756f, -0.27904f);
					matrices.multiply(new Quaternionf(entity.getrightthighpitch(ptt), 1, 0, 0));
					matrices.scale(-1, 1, 1);
			    	thigh.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);

				    	//RIGHT SHIN
						matrices.push();
						matrices.translate(0, -7.17156f, -1.52395f);
						matrices.multiply(new Quaternionf(entity.getrightshinpitch(ptt), 1, 0, 0));
				    	shin.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
				    	if (entity.isFire()) {
					    	flame.render(FLAME_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityTranslucent), light, OverlayTexture.DEFAULT_UV);
				    	}
						matrices.pop();

					matrices.pop();

					//LEFT THIGH
					matrices.push();
					matrices.translate(0, -7.26756f, -0.27904f);
					matrices.multiply(new Quaternionf(entity.getleftthighpitch(ptt), 1, 0, 0));
			    	thigh.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);

				    	//LEFT SHIN
						matrices.push();
						matrices.translate(0, -7.17156f, -1.52395f);
						matrices.multiply(new Quaternionf(entity.getleftshinpitch(ptt), 1, 0, 0));
				    	shin.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
				    	if (entity.isFire()) {
					    	flame.render(FLAME_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityTranslucent), colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
				    	}
						matrices.pop();

					matrices.pop();

					//HEAD
					matrices.push();
					matrices.translate(0, 5.23244f, 0);
					matrices.multiply(new Quaternionf(entity.getheadpitch(ptt), 1, 0, 0));
					matrices.multiply(new Quaternionf(entity.getheadyaw(ptt), 0, 1, 0));
			    	head.render(textureBuffer, colorOfRhodes, light, OverlayTexture.DEFAULT_UV);
					RenderSystem.enableCull();
		    		RenderSystem.enableBlend();
                RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
			    	if ((entity.laserOn & 1) == 1) {
			    		laser.render(buffer, new Vector4f(1, 0, 0, 0.5F), light, OverlayTexture.DEFAULT_UV);
			    	} else if ((entity.laserOn & 2) == 2) {
						matrices.scale(1, -1, 1);
						//GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
			    		laser.render(vertexConsumers.getBuffer(RenderLayer.getSolid()), new Vector4f(1, 0, 0, 0.5F), light, OverlayTexture.DEFAULT_UV);
						//GlStateManager.cullFace(GlStateManager.CullFace.BACK);
			    	}
			    	RenderSystem.disableCull();
		    		RenderSystem.disableBlend();
					matrices.pop();

				matrices.pop();
				//TORSO
				matrices.push();
					matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);
					if (entity.itexfolder != -1) {
						try {
					    	flag.render(vertexConsumers.getBuffer(RenderLayer.getEntitySolid(RRIdentifiers.create(texfolders[entity.itexfolder] + entity.itexloc + ".png"))), light, OverlayTexture.DEFAULT_UV);
						} catch (Exception ignored) {
						}
					}
			    	if (entity.forcefield)
			    	{
                        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
			    		fftorso.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
						//RIGHT UPPERARM
						matrices.push();
						matrices.translate(-6.4f, 0, 0);
						matrices.multiply(new Quaternionf(entity.getrightarmyaw(ptt), 0, 1, 0));
						matrices.scale(-1, 1, 1);
				    	ffupperarm.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
					    	//RIGHT LOWERARM
							matrices.push();
							matrices.translate(0, -1.5f, 0);
							matrices.multiply(new Quaternionf(entity.getrightarmpitch(ptt), 1, 0, 0));
					    	fflowerarm.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
							matrices.pop();
						matrices.pop();
						//LEFT UPPERARM
						matrices.push();
						matrices.translate(6.4f, 0, 0);
						matrices.multiply(new Quaternionf(entity.getleftarmyaw(ptt), 0, 1, 0));
				    	ffupperarm.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
					    	//LEFT LOWERARM
							matrices.push();
							matrices.translate(0, -1.5f, 0);
							matrices.multiply(new Quaternionf(entity.getleftarmpitch(ptt), 1, 0, 0));
					    	fflowerarm.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
							matrices.pop();
						matrices.pop();
						//RIGHT THIGH
						matrices.push();
						matrices.translate(0, -7.26756f, -0.27904f);
						matrices.multiply(new Quaternionf(entity.getrightthighpitch(ptt), 1, 0, 0));
						matrices.scale(-1, 1, 1);
				    	ffthigh.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
					    	//RIGHT SHIN
							matrices.push();
							matrices.translate(0, -7.17156f, -1.52395f);
							matrices.multiply(new Quaternionf(entity.getrightshinpitch(ptt), 1, 0, 0));
					    	ffshin.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
							matrices.pop();
						matrices.pop();
						//LEFT THIGH
						matrices.push();
						matrices.translate(0, -7.26756f, -0.27904f);
						matrices.multiply(new Quaternionf(entity.getleftthighpitch(ptt), 1, 0, 0));
				    	ffthigh.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
					    	//LEFT SHIN
							matrices.push();
							matrices.translate(0, -7.17156f, -1.52395f);
							matrices.multiply(new Quaternionf(entity.getleftshinpitch(ptt), 1, 0, 0));
					    	ffshin.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
							matrices.pop();
						matrices.pop();
						//HEAD
						matrices.push();
						matrices.translate(0, 5.23244f, 0);
						matrices.multiply(new Quaternionf(entity.getheadpitch(ptt), 1, 0, 0));
						matrices.multiply(new Quaternionf(entity.getheadyaw(ptt), 0, 1, 0));
				    	ffhead.render(cellularNoise, light, OverlayTexture.DEFAULT_UV);
						matrices.pop();
			    	}
				matrices.pop();
			}
	    	matrices.pop();
		}
		if (entity.health < 1)
		{
			matrices.push();
            matrices.translate((float) entity.getX(), (float) entity.getY(), (float) entity.getZ());
			RenderSystem.disableCull();
    		RenderSystem.enableBlend();
    		RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
			double elev = Math.sin((entity.health-tickDelta)*-0.0314159265359)*15;
			matrices.push();
			matrices.multiply(new Quaternionf((float) (elev * 2), 0, 1, 0));
			matrices.multiply(new Quaternionf((float) (elev * 3), 1, 0, 0));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) elev, 1, 0.25f, 0, 1f);
			matrices.pop();
			matrices.push();
            matrices.multiply(new Quaternionf((float) (elev * -2), 0, 1, 0));
            matrices.multiply(new Quaternionf((float) (elev * 4), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
			matrices.pop();
			matrices.push();
			matrices.multiply(new Quaternionf((float) (elev * -3), 1, 0, 0));
			matrices.multiply(new Quaternionf((float) (elev * 2), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.4f), 1, 0, 0, 1f);
			matrices.pop();
			matrices.push();
			matrices.multiply(new Quaternionf((float) (elev * -1), 0, 1, 0));
			matrices.multiply(new Quaternionf((float) (elev * 3), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.6f), 1, 1, 0, 1);
			matrices.pop();
    		RenderSystem.disableBlend();
			matrices.pop();
		}
	}

	@Override
    public Identifier getTexture(EntityRhodes entity)
	{
		return null;
	}
}
