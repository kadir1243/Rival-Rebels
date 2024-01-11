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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderRhodes extends Render<EntityRhodes>
{
    public static ResourceLocation texture;
    public static ResourceLocation flametex;
    public static IModel head;
    public static IModel torso;
    public static IModel flag;
    public static IModel upperarm;
    public static IModel lowerarm;
    public static IModel flamethrower;
    public static IModel rocketlauncher;
    public static IModel thigh;
    public static IModel shin;
    private static IModel booster;
    private static IModel flame;
    private static IModel laser;
    public static IModel ffhead;
    public static IModel fftorso;
    public static IModel ffupperarm;
    public static IModel fflowerarm;
    public static IModel ffthigh;
    public static IModel ffshin;
	private ModelBlastSphere modelsphere;
	public static ModelFromObj	md;
	public static ModelFromObj	b2jet;
	public static String[] texfolders = {
			"blocks/",
			"entity/",
			"items/",
			"flags/",
	};

	public RenderRhodes(RenderManager manager)
	{
        super(manager);
    	texture = new ResourceLocation(RivalRebels.MODID, "textures/entity/rhodes.png");
    	flametex = new ResourceLocation(RivalRebels.MODID, "textures/entity/flame.png");
        try {
            head = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/head.obj"));
            torso = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/torso.obj"));
            flag = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/flag.obj"));
            upperarm = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/upperarm.obj"));
            lowerarm = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/lowerarm.obj"));
            flamethrower = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/flamethrower.obj"));
            rocketlauncher = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/rocketlauncher.obj"));
            thigh = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/thigh.obj"));
            shin = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/shin.obj"));
            flame = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/flame.obj"));
            laser = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/laser.obj"));
            booster = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/booster.obj"));
            ffhead = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/ffhead.obj"));
            fftorso = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/fftorso.obj"));
            ffupperarm = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/ffupperarm.obj"));
            fflowerarm = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/fflowerarm.obj"));
            ffthigh = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/ffthigh.obj"));
            ffshin = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/rhodes/ffshin.obj"));
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

	public void renderRhodes(EntityRhodes rhodes, double x, double y, double z, float par8, float tt)
	{
		if (rhodes.health > 0)
		{
			float ptt = Math.min((rhodes.ticksSinceLastPacket + tt)/5f, 1);
			if (rhodes.ticksExisted<10) ptt = 1;
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x, (float) y, (float) z);
			GlStateManager.scale(rhodes.scale, rhodes.scale, rhodes.scale);

			FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            float f = 5F;
            float f1 = 0.016666668F * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 16, 0);
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-f1, -f1, f1);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            GlStateManager.disableTexture2D();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
            String name = rhodes.getName();
            int color = -1;
            if (rhodes.rider != null)
            {
            	name += " - " + rhodes.rider.getDisplayName();
            	RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(rhodes.rider.getGameProfile());
            	if (rrp!=null)
            	{
                    color = switch (rrp.rrteam) {
                        case OMEGA -> 0x44FF44;
                        case SIGMA -> 0x4444FF;
                        case NONE -> -1;
                    };
            	}
            }
            int j = fontrenderer.getStringWidth(name) / 2;
            buffer.pos(-j - 1, -1, 0.0D).color(0, 0, 0, 0.25F).endVertex();
            buffer.pos(-j - 1, 8, 0.0D).color(0, 0, 0, 0.25F).endVertex();
            buffer.pos(j + 1, 8, 0.0D).color(0, 0, 0, 0.25F).endVertex();
            buffer.pos(j + 1, -1, 0.0D).color(0, 0, 0, 0.25F).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, color);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, color);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();

			if (rhodes.colorType == 16)
			{
				GlStateManager.pushMatrix();
				GlStateManager.rotate(rhodes.getbodyyaw(ptt), 0, 1, 0);
				GlStateManager.translate(0, 10f, 0);
				Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etbooster);
		    	//booster.renderAll();
				Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etb2spirit);
				GlStateManager.pushMatrix();
				GlStateManager.rotate(-90f, 1, 0, 0);
				GlStateManager.translate(0, 4, -2);
				GlStateManager.scale(2.2f, 2.2f, 2.2f);
				if (rhodes.b2energy > 0) RenderB2Spirit.shuttle.render();
				GlStateManager.popMatrix();
				GlStateManager.popMatrix();
			}
			else {
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				GlStateManager.disableCull();
				GlStateManager.rotate(rhodes.getbodyyaw(ptt), 0, 1, 0);

				float leftlegheight = 7.26756f - 15
						+ (MathHelper.cos((rhodes.getleftthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (MathHelper.cos((rhodes.getleftthighpitch(ptt)+rhodes.getleftshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);
				float rightlegheight = 7.26756f - 15
						+ (MathHelper.cos((rhodes.getrightthighpitch(ptt)+11.99684962f)*0.01745329252f) * 7.331691240f)
						+ (MathHelper.cos((rhodes.getrightthighpitch(ptt)+rhodes.getrightshinpitch(ptt)-12.2153067f)*0.01745329252f) * 8.521366426f);

				//TORSO
				GlStateManager.pushMatrix();
				GlStateManager.color(colors[rhodes.colorType*3], colors[rhodes.colorType*3+1], colors[rhodes.colorType*3+2]);
				GlStateManager.translate(0, (leftlegheight > rightlegheight) ? leftlegheight : rightlegheight, 0);

				Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etb2spirit);
				GlStateManager.pushMatrix();
				GlStateManager.rotate(-90f, 1, 0, 0);
				GlStateManager.translate(0, 4, -2);
				if (rhodes.b2energy > 0) md.render();
	    		GlStateManager.enableBlend();
	    		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
	    		Minecraft.getMinecraft().renderEngine.bindTexture(flametex);
				GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				if (rhodes.jet && rhodes.b2energy > 0) b2jet.render();
	    		GlStateManager.disableBlend();
				GlStateManager.popMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		    	//torso.renderAll();


					//RIGHT UPPERARM
					GlStateManager.pushMatrix();
					GlStateManager.translate(-6.4f, 0, 0);
					GlStateManager.rotate(rhodes.getrightarmyaw(ptt), 0, 1, 0);
					GlStateManager.scale(-1, 1, 1);
			    	//upperarm.renderAll();

				    	//RIGHT LOWERARM
						GlStateManager.pushMatrix();
						GlStateManager.translate(0, -1.5f, 0);
						GlStateManager.rotate(rhodes.getrightarmpitch(ptt), 1, 0, 0);
				    	//lowerarm.renderAll();
						GlStateManager.scale(-1, 1, 1);
				    	//flamethrower.renderAll();
						GlStateManager.popMatrix();

					GlStateManager.popMatrix();

					//LEFT UPPERARM
					GlStateManager.pushMatrix();
					GlStateManager.translate(6.4f, 0, 0);
					GlStateManager.rotate(rhodes.getleftarmyaw(ptt), 0, 1, 0);
			    	//upperarm.renderAll();

				    	//LEFT LOWERARM
						GlStateManager.pushMatrix();
						GlStateManager.translate(0, -1.5f, 0);
						GlStateManager.rotate(rhodes.getleftarmpitch(ptt), 1, 0, 0);
				    	//lowerarm.renderAll();
				    	//rocketlauncher.renderAll();
						GlStateManager.popMatrix();

					GlStateManager.popMatrix();

					//RIGHT THIGH
					GlStateManager.pushMatrix();
					GlStateManager.translate(0, -7.26756f, -0.27904f);
					GlStateManager.rotate(rhodes.getrightthighpitch(ptt), 1, 0, 0);
					GlStateManager.scale(-1, 1, 1);
			    	//thigh.renderAll();

				    	//RIGHT SHIN
						GlStateManager.pushMatrix();
						GlStateManager.translate(0, -7.17156f, -1.52395f);
						GlStateManager.rotate(rhodes.getrightshinpitch(ptt), 1, 0, 0);
				    	//shin.renderAll();
				    	if (rhodes.fire)
				    	{
				    		GlStateManager.enableBlend();
				    		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
				    		Minecraft.getMinecraft().renderEngine.bindTexture(flametex);
							GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
							GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
					    	//flame.renderAll();
				    		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				    		GlStateManager.disableBlend();
				    	}
						GlStateManager.popMatrix();

					GlStateManager.popMatrix();

					//LEFT THIGH
					GlStateManager.pushMatrix();
					GlStateManager.translate(0, -7.26756f, -0.27904f);
					GlStateManager.rotate(rhodes.getleftthighpitch(ptt), 1, 0, 0);
			    	//thigh.renderAll();

				    	//LEFT SHIN
						GlStateManager.pushMatrix();
						GlStateManager.translate(0, -7.17156f, -1.52395f);
						GlStateManager.rotate(rhodes.getleftshinpitch(ptt), 1, 0, 0);
				    	//shin.renderAll();
				    	if (rhodes.fire)
				    	{
				    		GlStateManager.enableBlend();
				    		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
				    		Minecraft.getMinecraft().renderEngine.bindTexture(flametex);
					    	//flame.renderAll();
				    		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				    		GlStateManager.disableBlend();
				    	}
						GlStateManager.popMatrix();

					GlStateManager.popMatrix();

					//HEAD
					GlStateManager.pushMatrix();
					GlStateManager.translate(0, 5.23244f, 0);
					GlStateManager.rotate(rhodes.getheadpitch(ptt), 1, 0, 0);
					GlStateManager.rotate(rhodes.getheadyaw(ptt), 0, 1, 0);
			    	//head.renderAll();
					GlStateManager.enableCull();
		    		GlStateManager.enableBlend();
		    		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		    		GlStateManager.disableTexture2D();
		    		GlStateManager.color(1f, 0f, 0f, 0.5f);
			    	if ((rhodes.laserOn & 1) == 1)
			    	{
			    		//laser.renderAll();
			    	}
			    	if ((rhodes.laserOn & 2) == 2)
			    	{
						GlStateManager.scale(1, -1, 1);
						GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
			    		//laser.renderAll();
						GlStateManager.cullFace(GlStateManager.CullFace.BACK);
			    	}
		    		GlStateManager.enableTexture2D();
			    	GlStateManager.disableCull();
		    		GlStateManager.disableBlend();
					GlStateManager.popMatrix();

				GlStateManager.popMatrix();
				//TORSO
				GlStateManager.pushMatrix();
					GlStateManager.color(1,1,1);
					GlStateManager.translate(0, (leftlegheight > rightlegheight) ? leftlegheight : rightlegheight, 0);
					if (rhodes.itexfolder != -1)
					{
						try
						{
							Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RivalRebels.MODID, "textures/" + texfolders[rhodes.itexfolder] + rhodes.itexloc + ".png"));
					    	//flag.renderAll();
						}
						catch (Exception e)
						{
						}
					}
			    	if (rhodes.forcefield)
			    	{
				    	GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
				    	GlStateManager.enableBlend();
						GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
						GlStateManager.disableLighting();
			    		//fftorso.renderAll();
						//RIGHT UPPERARM
						GlStateManager.pushMatrix();
						GlStateManager.translate(-6.4f, 0, 0);
						GlStateManager.rotate(rhodes.getrightarmyaw(ptt), 0, 1, 0);
						GlStateManager.scale(-1, 1, 1);
				    	//ffupperarm.renderAll();
					    	//RIGHT LOWERARM
							GlStateManager.pushMatrix();
							GlStateManager.translate(0, -1.5f, 0);
							GlStateManager.rotate(rhodes.getrightarmpitch(ptt), 1, 0, 0);
					    	//fflowerarm.renderAll();
							GlStateManager.popMatrix();
						GlStateManager.popMatrix();
						//LEFT UPPERARM
						GlStateManager.pushMatrix();
						GlStateManager.translate(6.4f, 0, 0);
						GlStateManager.rotate(rhodes.getleftarmyaw(ptt), 0, 1, 0);
				    	//ffupperarm.renderAll();
					    	//LEFT LOWERARM
							GlStateManager.pushMatrix();
							GlStateManager.translate(0, -1.5f, 0);
							GlStateManager.rotate(rhodes.getleftarmpitch(ptt), 1, 0, 0);
					    	//fflowerarm.renderAll();
							GlStateManager.popMatrix();
						GlStateManager.popMatrix();
						//RIGHT THIGH
						GlStateManager.pushMatrix();
						GlStateManager.translate(0, -7.26756f, -0.27904f);
						GlStateManager.rotate(rhodes.getrightthighpitch(ptt), 1, 0, 0);
						GlStateManager.scale(-1, 1, 1);
				    	//ffthigh.renderAll();
					    	//RIGHT SHIN
							GlStateManager.pushMatrix();
							GlStateManager.translate(0, -7.17156f, -1.52395f);
							GlStateManager.rotate(rhodes.getrightshinpitch(ptt), 1, 0, 0);
					    	//ffshin.renderAll();
							GlStateManager.popMatrix();
						GlStateManager.popMatrix();
						//LEFT THIGH
						GlStateManager.pushMatrix();
						GlStateManager.translate(0, -7.26756f, -0.27904f);
						GlStateManager.rotate(rhodes.getleftthighpitch(ptt), 1, 0, 0);
				    	//ffthigh.renderAll();
					    	//LEFT SHIN
							GlStateManager.pushMatrix();
							GlStateManager.translate(0, -7.17156f, -1.52395f);
							GlStateManager.rotate(rhodes.getleftshinpitch(ptt), 1, 0, 0);
					    	//ffshin.renderAll();
							GlStateManager.popMatrix();
						GlStateManager.popMatrix();
						//HEAD
						GlStateManager.pushMatrix();
						GlStateManager.translate(0, 5.23244f, 0);
						GlStateManager.rotate(rhodes.getheadpitch(ptt), 1, 0, 0);
						GlStateManager.rotate(rhodes.getheadyaw(ptt), 0, 1, 0);
				    	//ffhead.renderAll();
						GlStateManager.popMatrix();
						GlStateManager.disableBlend();
						GlStateManager.enableLighting();
			    	}
				GlStateManager.popMatrix();
			}
	    	GlStateManager.popMatrix();
		}
		if (rhodes.health < 1)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x, (float) y, (float) z);
			GlStateManager.disableCull();
    		GlStateManager.enableBlend();
    		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    		GlStateManager.disableTexture2D();
			double elev = Math.sin((rhodes.health-tt)*-0.0314159265359)*15;
			GlStateManager.pushMatrix();
			GlStateManager.rotate((float) (elev * 2), 0, 1, 0);
			GlStateManager.rotate((float) (elev * 3), 1, 0, 0);
			modelsphere.renderModel((float) elev, 1, 0.25f, 0, 1f);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.rotate((float) (elev * -2), 0, 1, 0);
			GlStateManager.rotate((float) (elev * 4), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.2f), 1, 0.5f, 0, 1f);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.rotate((float) (elev * -3), 1, 0, 0);
			GlStateManager.rotate((float) (elev * 2), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.4f), 1, 0, 0, 1f);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.rotate((float) (elev * -1), 0, 1, 0);
			GlStateManager.rotate((float) (elev * 3), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.6f), 1, 1, 0, 1);
			GlStateManager.popMatrix();
    		GlStateManager.enableTexture2D();
    		GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}

	@Override
	public void doRender(EntityRhodes entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		renderRhodes(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRhodes entity)
	{
		return null;
	}
}
