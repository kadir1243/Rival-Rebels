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
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class RenderRhodes extends EntityRenderer<EntityRhodes>
{
    public static Identifier texture;
    public static Identifier flametex;
    //public static IModel head;
    //public static IModel torso;
    //public static IModel flag;
    //public static IModel upperarm;
    //public static IModel lowerarm;
    //public static IModel flamethrower;
    //public static IModel rocketlauncher;
    //public static IModel thigh;
    //public static IModel shin;
    //private static IModel booster;
    //private static IModel flame;
    //private static IModel laser;
    //public static IModel ffhead;
    //public static IModel fftorso;
    //public static IModel ffupperarm;
    //public static IModel fflowerarm;
    //public static IModel ffthigh;
    //public static IModel ffshin;
	private ModelBlastSphere modelsphere;
	public static ModelFromObj	md;
	public static ModelFromObj	b2jet;
	public static String[] texfolders = {
			"blocks/",
			"entity/",
			"items/",
			"flags/",
	};

	public RenderRhodes(EntityRendererFactory.Context manager)
	{
        super(manager);
    	texture = new Identifier(RivalRebels.MODID, "textures/entity/rhodes.png");
    	flametex = new Identifier(RivalRebels.MODID, "textures/entity/flame.png");
        try {
            //head = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/head.obj"));
            //torso = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/torso.obj"));
            //flag = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/flag.obj"));
            //upperarm = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/upperarm.obj"));
            //lowerarm = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/lowerarm.obj"));
            //flamethrower = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/flamethrower.obj"));
            //rocketlauncher = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/rocketlauncher.obj"));
            //thigh = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/thigh.obj"));
            //shin = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/shin.obj"));
            //flame = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/flame.obj"));
            //laser = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/laser.obj"));
            //booster = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/booster.obj"));
            //ffhead = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/ffhead.obj"));
            //fftorso = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/fftorso.obj"));
            //ffupperarm = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/ffupperarm.obj"));
            //fflowerarm = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/fflowerarm.obj"));
            //ffthigh = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/ffthigh.obj"));
            //ffshin = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/rhodes/ffshin.obj"));
        } catch (Exception e) {
            RivalRebels.LOGGER.error("Can not load model for rhodes", e);
        }

		modelsphere = new ModelBlastSphere();
		try
		{
			md = ModelFromObj.readObjFile("d.obj");
			b2jet = ModelFromObj.readObjFile("s.obj");
			md.scale(2.5f, 2.5f, 2.5f);
			b2jet.scale(2.5f, 2.5f, 2.5f);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            matrices.multiply(new Quaternion((float) -this.dispatcher.camera.getPos().y, 0.0F, 1.0F, 0.0F));
            matrices.multiply(new Quaternion((float) this.dispatcher.camera.getPos().x, 1.0F, 0.0F, 0.0F));
            matrices.scale(-f1, -f1, f1);
            RenderSystem.depthMask(false);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ONE, DstFactor.ZERO);
            RenderSystem.disableTexture();
            String name = entity.getEntityName();
            int color = -1;
            if (entity.rider != null)
            {
            	name += " - " + entity.rider.getDisplayName();
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
            RenderSystem.enableTexture();
            fontrenderer.draw(matrices, name, -fontrenderer.getWidth(name) / 2F, 0, color);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            fontrenderer.draw(matrices, name, -fontrenderer.getWidth(name) / 2F, 0, color);
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();

			if (entity.colorType == 16)
			{
				matrices.push();
                matrices.multiply(new Quaternion(entity.getbodyyaw(ptt), 0, 1, 0));
				matrices.translate(0, 10f, 0);
				MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etbooster);
		    	//booster.renderAll();
				MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etb2spirit);
				matrices.push();
				matrices.multiply(new Quaternion(-90f, 1, 0, 0));
				matrices.translate(0, 4, -2);
				matrices.scale(2.2f, 2.2f, 2.2f);
				if (entity.b2energy > 0) RenderB2Spirit.shuttle.render(buffer);
				matrices.pop();
				matrices.pop();
			}
			else {
				MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
				RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				RenderSystem.disableCull();
				matrices.multiply(new Quaternion(entity.getbodyyaw(ptt), 0, 1, 0));

				float leftlegheight = 7.26756f - 15
						+ (MathHelper.cos((entity.getleftthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (MathHelper.cos((entity.getleftthighpitch(ptt)+entity.getleftshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);
				float rightlegheight = 7.26756f - 15
						+ (MathHelper.cos((entity.getrightthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (MathHelper.cos((entity.getrightthighpitch(ptt)+entity.getrightshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);

				//TORSO
				matrices.push();
				RenderSystem.setShaderColor(colors[entity.colorType*3], colors[entity.colorType*3+1], colors[entity.colorType*3+2], 1);
				matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);

				MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etb2spirit);
				matrices.push();
				matrices.multiply(new Quaternion(-90f, 1, 0, 0));
				matrices.translate(0, 4, -2);
				if (entity.b2energy > 0) md.render(buffer);
	    		RenderSystem.enableBlend();
                RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
	    		MinecraftClient.getInstance().getTextureManager().bindTexture(flametex);
				RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				if (entity.jet && entity.b2energy > 0) b2jet.render(buffer);
	    		RenderSystem.disableBlend();
				matrices.pop();
				MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

		    	//torso.renderAll();


					//RIGHT UPPERARM
					matrices.push();
					matrices.translate(-6.4f, 0, 0);
					matrices.multiply(new Quaternion(entity.getrightarmyaw(ptt), 0, 1, 0));
					matrices.scale(-1, 1, 1);
			    	//upperarm.renderAll();

				    	//RIGHT LOWERARM
						matrices.push();
						matrices.translate(0, -1.5f, 0);
						matrices.multiply(new Quaternion(entity.getrightarmpitch(ptt), 1, 0, 0));
				    	//lowerarm.renderAll();
						matrices.scale(-1, 1, 1);
				    	//flamethrower.renderAll();
						matrices.pop();

					matrices.pop();

					//LEFT UPPERARM
					matrices.push();
					matrices.translate(6.4f, 0, 0);
					matrices.multiply(new Quaternion(entity.getleftarmyaw(ptt), 0, 1, 0));
			    	//upperarm.renderAll();

				    	//LEFT LOWERARM
						matrices.push();
						matrices.translate(0, -1.5f, 0);
						matrices.multiply(new Quaternion(entity.getleftarmpitch(ptt), 1, 0, 0));
				    	//lowerarm.renderAll();
				    	//rocketlauncher.renderAll();
						matrices.pop();

					matrices.pop();

					//RIGHT THIGH
					matrices.push();
					matrices.translate(0, -7.26756f, -0.27904f);
					matrices.multiply(new Quaternion(entity.getrightthighpitch(ptt), 1, 0, 0));
					matrices.scale(-1, 1, 1);
			    	//thigh.renderAll();

				    	//RIGHT SHIN
						matrices.push();
						matrices.translate(0, -7.17156f, -1.52395f);
						matrices.multiply(new Quaternion(entity.getrightshinpitch(ptt), 1, 0, 0));
				    	//shin.renderAll();
				    	if (entity.isFire())
				    	{
				    		RenderSystem.enableBlend();
				    		RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
				    		MinecraftClient.getInstance().getTextureManager().bindTexture(flametex);
							RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
							RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
					    	//flame.renderAll();
				    		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
				    		RenderSystem.disableBlend();
				    	}
						matrices.pop();

					matrices.pop();

					//LEFT THIGH
					matrices.push();
					matrices.translate(0, -7.26756f, -0.27904f);
					matrices.multiply(new Quaternion(entity.getleftthighpitch(ptt), 1, 0, 0));
			    	//thigh.renderAll();

				    	//LEFT SHIN
						matrices.push();
						matrices.translate(0, -7.17156f, -1.52395f);
						matrices.multiply(new Quaternion(entity.getleftshinpitch(ptt), 1, 0, 0));
				    	//shin.renderAll();
				    	if (entity.isFire())
				    	{
				    		RenderSystem.enableBlend();
                            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
				    		MinecraftClient.getInstance().getTextureManager().bindTexture(flametex);
					    	//flame.renderAll();
				    		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
				    		RenderSystem.disableBlend();
				    	}
						matrices.pop();

					matrices.pop();

					//HEAD
					matrices.push();
					matrices.translate(0, 5.23244f, 0);
					matrices.multiply(new Quaternion(entity.getheadpitch(ptt), 1, 0, 0));
					matrices.multiply(new Quaternion(entity.getheadyaw(ptt), 0, 1, 0));
			    	//head.renderAll();
					RenderSystem.enableCull();
		    		RenderSystem.enableBlend();
                RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
                RenderSystem.setShaderColor(1f, 0f, 0f, 0.5f);
			    	if ((entity.laserOn & 1) == 1)
			    	{
			    		//laser.renderAll();
			    	}
			    	if ((entity.laserOn & 2) == 2)
			    	{
						matrices.scale(1, -1, 1);
						//GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
			    		//laser.renderAll();
						//GlStateManager.cullFace(GlStateManager.CullFace.BACK);
			    	}
		    		RenderSystem.enableTexture();
			    	RenderSystem.disableCull();
		    		RenderSystem.disableBlend();
					matrices.pop();

				matrices.pop();
				//TORSO
				matrices.push();
					RenderSystem.setShaderColor(1,1,1, 1);
					matrices.translate(0, Math.max(leftlegheight, rightlegheight), 0);
					if (entity.itexfolder != -1)
					{
						try
						{
							MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier(RivalRebels.MODID, "textures/" + texfolders[entity.itexfolder] + entity.itexloc + ".png"));
					    	//flag.renderAll();
						}
						catch (Exception e)
						{
						}
					}
			    	if (entity.forcefield)
			    	{
				    	RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
				    	RenderSystem.enableBlend();
                        RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			    		//fftorso.renderAll();
						//RIGHT UPPERARM
						matrices.push();
						matrices.translate(-6.4f, 0, 0);
						matrices.multiply(new Quaternion(entity.getrightarmyaw(ptt), 0, 1, 0));
						matrices.scale(-1, 1, 1);
				    	//ffupperarm.renderAll();
					    	//RIGHT LOWERARM
							matrices.push();
							matrices.translate(0, -1.5f, 0);
							matrices.multiply(new Quaternion(entity.getrightarmpitch(ptt), 1, 0, 0));
					    	//fflowerarm.renderAll();
							matrices.pop();
						matrices.pop();
						//LEFT UPPERARM
						matrices.push();
						matrices.translate(6.4f, 0, 0);
						matrices.multiply(new Quaternion(entity.getleftarmyaw(ptt), 0, 1, 0));
				    	//ffupperarm.renderAll();
					    	//LEFT LOWERARM
							matrices.push();
							matrices.translate(0, -1.5f, 0);
							matrices.multiply(new Quaternion(entity.getleftarmpitch(ptt), 1, 0, 0));
					    	//fflowerarm.renderAll();
							matrices.pop();
						matrices.pop();
						//RIGHT THIGH
						matrices.push();
						matrices.translate(0, -7.26756f, -0.27904f);
						matrices.multiply(new Quaternion(entity.getrightthighpitch(ptt), 1, 0, 0));
						matrices.scale(-1, 1, 1);
				    	//ffthigh.renderAll();
					    	//RIGHT SHIN
							matrices.push();
							matrices.translate(0, -7.17156f, -1.52395f);
							matrices.multiply(new Quaternion(entity.getrightshinpitch(ptt), 1, 0, 0));
					    	//ffshin.renderAll();
							matrices.pop();
						matrices.pop();
						//LEFT THIGH
						matrices.push();
						matrices.translate(0, -7.26756f, -0.27904f);
						matrices.multiply(new Quaternion(entity.getleftthighpitch(ptt), 1, 0, 0));
				    	//ffthigh.renderAll();
					    	//LEFT SHIN
							matrices.push();
							matrices.translate(0, -7.17156f, -1.52395f);
							matrices.multiply(new Quaternion(entity.getleftshinpitch(ptt), 1, 0, 0));
					    	//ffshin.renderAll();
							matrices.pop();
						matrices.pop();
						//HEAD
						matrices.push();
						matrices.translate(0, 5.23244f, 0);
						matrices.multiply(new Quaternion(entity.getheadpitch(ptt), 1, 0, 0));
						matrices.multiply(new Quaternion(entity.getheadyaw(ptt), 0, 1, 0));
				    	//ffhead.renderAll();
						matrices.pop();
						RenderSystem.disableBlend();
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
			matrices.multiply(new Quaternion((float) (elev * 2), 0, 1, 0));
			matrices.multiply(new Quaternion((float) (elev * 3), 1, 0, 0));
			modelsphere.renderModel(matrices, buffer, (float) elev, 1, 0.25f, 0, 1f);
			matrices.pop();
			matrices.push();
            matrices.multiply(new Quaternion((float) (elev * -2), 0, 1, 0));
            matrices.multiply(new Quaternion((float) (elev * 4), 0, 0, 1));
			modelsphere.renderModel(matrices, buffer, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
			matrices.pop();
			matrices.push();
			matrices.multiply(new Quaternion((float) (elev * -3), 1, 0, 0));
			matrices.multiply(new Quaternion((float) (elev * 2), 0, 0, 1));
			modelsphere.renderModel(matrices, buffer, (float) (elev - 0.4f), 1, 0, 0, 1f);
			matrices.pop();
			matrices.push();
			matrices.multiply(new Quaternion((float) (elev * -1), 0, 1, 0));
			matrices.multiply(new Quaternion((float) (elev * 3), 0, 0, 1));
			modelsphere.renderModel(matrices, buffer, (float) (elev - 0.6f), 1, 1, 0, 1);
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
