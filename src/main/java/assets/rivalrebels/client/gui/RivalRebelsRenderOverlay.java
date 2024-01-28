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
package assets.rivalrebels.client.gui;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.renderentity.RenderRhodes;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.weapon.ItemBinoculars;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;

public class RivalRebelsRenderOverlay
{
	public int		tic	= 0;
	public boolean	r	= false;
	public EntityRhodes rhodes = null;
	public long counter = 0;

	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent event)
	{
		if (rhodes != null)
		{
		// FIXME:	renderRhodes(event, MinecraftClient.getInstance().player, rhodes);
		}
		renderItems(event);
	}
	private void renderItems(RenderGameOverlayEvent event)
	{
		/*if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
		{ FIXME
			PlayerEntity player = MinecraftClient.getInstance().player;
			ItemStack stack = player.getInventory().getMainHandStack();
			if (stack.isEmpty()) return;
			if (stack.getItem() instanceof ItemBinoculars) renderBinoculars(stack, event, player);
		}*/
	}

	private void renderRhodes(RenderGameOverlayEvent event, PlayerEntity player, EntityRhodes rhodes)
	{
        MatrixStack matrices = event.getMatrixStack();

        //if (event.getType() == ElementType.HOTBAR) FIXME
		{
			counter--;
			if (counter <= 0)
			{
				counter = 0;
				RivalRebels.rrro.rhodes = null;
			}
			/*for (int i = 0; i < 65536; i++)
			{
				RenderSystem.bindTexture(i);
				RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			}*/
			Tessellator t = Tessellator.getInstance();
            BufferBuilder buffer = t.getBuffer();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
			TextRenderer fr = MinecraftClient.getInstance().textRenderer;
			int w = event.getWindow().getScaledWidth();
			int h = event.getWindow().getScaledHeight();

			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
			MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guirhodesline);
			RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, 0.5F);
            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
			buffer.vertex(0, h, -90).texture(0, 1).next();
			buffer.vertex(w, h, -90).texture(1, 1).next();
			buffer.vertex(w, 0, -90).texture(1, 0).next();
			buffer.vertex(0, 0, -90).texture(0, 0).next();
			t.draw();

			RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
			MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guirhodesout);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.333f);
            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
			buffer.vertex(0, h, -90).texture(0, 1).next();
			buffer.vertex(w, h, -90).texture(1, 1).next();
			buffer.vertex(w, 0, -90).texture(1, 0).next();
			buffer.vertex(0, 0, -90).texture(0, 0).next();
			t.draw();

			if (glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_H) == GLFW_PRESS)
			{
                RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
				MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guirhodeshelp);
				RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
				buffer.vertex(w*0.25f, h*0.75f, -90).texture(0, 1).next();
				buffer.vertex(w*0.75f, h*0.75f, -90).texture(1, 1).next();
				buffer.vertex(w*0.75f, h*0.25f, -90).texture(1, 0).next();
				buffer.vertex(w*0.25f, h*0.25f, -90).texture(0, 0).next();
				t.draw();
			}

			if (rhodes.itexfolder > -1 && rhodes.itexfolder < 4)
			{
				RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
				MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier(RivalRebels.MODID, "textures/" + RenderRhodes.texfolders[rhodes.itexfolder] + rhodes.itexloc + ".png"));
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);
                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
				float s = 8;
				float wl = w*0.5f;
				float hl = h*0.05f;
				buffer.vertex(wl-s, hl+s, -90).texture(0, 1).next();
				buffer.vertex(wl+s, hl+s, -90).texture(1, 1).next();
				buffer.vertex(wl+s, hl-s, -90).texture(1, 0).next();
				buffer.vertex(wl-s, hl-s, -90).texture(0, 0).next();
				t.draw();
			}
            RenderSystem.disableBlend();

            Text text;
			String disp = "Rival Rebels";
			fr.draw(matrices, disp, (int) (w * 0.05), (int) (h * 0.05), 0xffffff);
			disp = "Robot: " + rhodes.getName();
			fr.draw(matrices, disp, (int) (w * 0.05), (int) (h * 0.1), 0xffffff);
			text = RRBlocks.reactor.getName().append(": " + rhodes.health);
			float val = (rhodes.health / (float) RivalRebels.rhodesHealth);
			fr.draw(matrices, text, (int) (w * 0.05), (int) (h * 0.15), (((int)((1-val)*255)&255)<<16) | (((int)(val*255)&255)<<8));
			float yaw = (player.getYaw() + 360000) % 360;
			disp = (yaw >= 315 || yaw < 45) ? I18n.translate("RivalRebels.binoculars.south") : (yaw >= 45 && yaw < 135) ? I18n.translate("RivalRebels.binoculars.west") : (yaw >= 135 && yaw < 225) ? I18n.translate("RivalRebels.binoculars.north") : (yaw >= 225 && yaw < 315) ? I18n.translate("RivalRebels.binoculars.east") : "Whut";
			fr.draw(matrices, disp, (int) (w * 0.05), (int) (h * 0.2), 0xffffff);

			text = RRItems.einsten.getName().copy().append(": " + rhodes.energy);
			fr.draw(matrices, text, (int) (w * 0.8), (int) (h * 0.05), (rhodes.laserOn>0)?0xff3333:0xffffff);
			disp = "Jet: " + rhodes.energy;
			fr.draw(matrices, disp, (int) (w * 0.8), (int) (h * 0.1), RivalRebels.proxy.spacebar()?0x6666ff:0xffffff);
            text = RRBlocks.forcefieldnode.getName().append(": " + rhodes.energy);
			fr.draw(matrices, text, (int) (w * 0.8), (int) (h * 0.15), rhodes.forcefield?0xBB88FF:0xffffff);
			text = RRItems.seekm202.getName().copy().append(": " + rhodes.rocketcount);
			fr.draw(matrices, text, (int) (w * 0.8), (int) (h * 0.2), 0xffffff);
			text = (rhodes.isPlasma()?Text.of("Plasma: " + rhodes.flamecount) : (((MutableText)RRItems.fuel.getName()).append(": " + rhodes.flamecount)));
			fr.draw(matrices, text, (int) (w * 0.8), (int) (h * 0.25), 0xffffff);
			fr.draw(matrices, RRBlocks.nuclearBomb.getName().copy().append(": " + rhodes.nukecount), (int) (w * 0.8), (int) (h * 0.3), 0xffffff);
			fr.draw(matrices, Text.of("Guard"), (int) (w * 0.8), (int) (h * 0.35), RivalRebels.proxy.g() ? 0xffff00 : 0xffffff);
			disp = rhodes.getName() + " " + RRBlocks.controller.getName() + ": H";
			fr.draw(matrices, disp, (int) (w * 0.05), (int) (h * 0.95), glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_H) == GLFW_PRESS ? 0xffff00 : 0xffffff);
			if (rhodes.forcefield)
			{
                RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
                RenderSystem.enableBlend();
				RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.7f);
                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
				buffer.vertex(0, h, -90).texture(0, h*0.003f).next();
				buffer.vertex(w, h, -90).texture(w*0.003f, h*0.003f).next();
				buffer.vertex(w, 0, -90).texture(w*0.003f, 0).next();
				buffer.vertex(0, 0, -90).texture(0, 0).next();
				t.draw();
                RenderSystem.disableBlend();
			}
		}
	}

	private void renderBinoculars(ItemStack stack, RenderGameOverlayEvent event, PlayerEntity player)
	{
        MatrixStack matrices = event.getMatrixStack();
        if (MinecraftClient.getInstance().mouse.wasRightButtonClicked())
		{
			if (event.isCancelable()) event.setCanceled(true);
			//FIXME: if (event.getType() == ElementType.HOTBAR)
			{
				Tessellator t = Tessellator.getInstance();
                BufferBuilder buffer = t.getBuffer();
                tic++;
				RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
				MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guibinoculars);
				TextRenderer fr = MinecraftClient.getInstance().textRenderer;
				int w = event.getWindow().getScaledWidth();
				int h = event.getWindow().getScaledHeight();

                RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
                RenderSystem.enableBlend();
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
				buffer.vertex(0, h, -90).texture(0, 1).next();
				buffer.vertex(w, h, -90).texture(1, 1).next();
				buffer.vertex(w, 0, -90).texture(1, 0).next();
				buffer.vertex(0, 0, -90).texture(0, 0).next();
				t.draw();

				MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guibinocularsoverlay);
				RenderSystem.setShaderColor(0.333F, 0.333F, 0.333F, 0.5F);
                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
				buffer.vertex(0, h, -90).texture(0, 1).next();
				buffer.vertex(w, h, -90).texture(1, 1).next();
				buffer.vertex(w, 0, -90).texture(1, 0).next();
				buffer.vertex(0, 0, -90).texture(0, 0).next();
				t.draw();

				// RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
				// GL11.glDisable(GL11.GL_BLEND);
				// RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				// t.startDrawing(GL11.GL_LINES);
				// t.addVertex(1, 100, -90);
				// t.addVertex(100, 100, -90);
				// t.addVertex(100, 1, -90);
				// t.addVertex(1, 1, -90);
				// t.draw();
				// RenderSystem.enableBlend();
				Block id = player.world.getBlockState(ItemBinoculars.tpos).getBlock();
                String disp;
                Text text;
				text = Text.of("X");
				if (id != Blocks.AIR) text = id.getName();
				fr.draw(matrices, text, (int) ((w * 0.50) - (fr.getWidth(text) / 2f)), (int) (h * 0.18), 0x00ff00);
				if (!ItemBinoculars.tooFar) disp = "(" + ItemBinoculars.tpos.getX() + ", " + ItemBinoculars.tpos.getY() + ", " + ItemBinoculars.tpos.getZ() + ")";
				else disp = "";
				fr.draw(matrices, disp, (int) ((w * 0.50) - (fr.getWidth(disp) / 2f)), (int) (h * 0.13), 0x00ff00);
                if (tic % 30 == 0) r = !r;
				//if (nbt.getInt("ty") != -1 && nbt.getInt("cooldowntime") > 0) disp = ">" + nbt.getInt("tx") + ", " + nbt.getInt("ty") + ", " + nbt.getInt("tz") + "<";
				//else if (r) disp = ">                    <";
				//fr.draw(matrices, disp, (int) ((w * 0.50) - (fr.getWidth(disp) / 2f)), (int) (h * 0.85), 0xff0000);
				text = Text.of("LTD RR");
				fr.draw(matrices, text, (int) ((w * 0.50) - (fr.getWidth(text) / 2f)), (int) (h * 0.80), 0xffffff);
				disp = ((int)ItemBinoculars.distblock) + "m";
				fr.draw(matrices, disp, (int) ((w * 0.637) - (fr.getWidth(disp) / 2f)), (int) (h * 0.205), 0xffffff);
				float yaw = (player.getYaw() + 360000) % 360;
				disp = (yaw >= 315 || yaw < 45) ? I18n.translate("RivalRebels.binoculars.south") : (yaw >= 45 && yaw < 135) ? I18n.translate("RivalRebels.binoculars.west") : (yaw >= 135 && yaw < 225) ? I18n.translate("RivalRebels.binoculars.north") : (yaw >= 225 && yaw < 315) ? I18n.translate("RivalRebels.binoculars.east") : "Whut";
				fr.draw(matrices, disp, (int) ((w * 0.370) - (fr.getWidth(disp) / 2f)), (int) (h * 0.205), 0xffffff);
				if (ItemBinoculars.tooFar) fr.draw(matrices, I18n.translate("RivalRebels.controller.range"), (int) ((w * 0.5) - (fr.getWidth(I18n.translate("RivalRebels.controller.range")) / 2f)), (int) (h * 0.85), 0xff0000);
				else if (ItemBinoculars.tooClose) fr.draw(matrices, I18n.translate("RivalRebels.nextbattle.no"), (int) ((w * 0.5) - (fr.getWidth(I18n.translate("RivalRebels.nextbattle.no")) / 2f)), (int) (h * 0.85), 0xff0000);
				//else if (dist2 < 40)
				//{
				//	disp = I18n.translate("RivalRebels.nextbattle.no");
				//	fr.draw(matrices, disp, (int) ((w * 0.5) - (fr.getWidth(disp) / 2f)), (int) (h * 0.90), 0xff0000);
				//	disp = (team == RivalRebelsTeam.OMEGA ? RivalRebels.omegaobj.getLocalizedName() : RivalRebels.omegaobj.getLocalizedName());
				//	fr.draw(matrices, disp, (int) ((w * 0.5) - (fr.getWidth(disp) / 2f)), (int) (h * 0.94), 0xff0000);
				//}
				else if (ItemBinoculars.ready) fr.draw(matrices, I18n.translate("RivalRebels.binoculars.target"), (int) ((w * 0.5) - (fr.getWidth(I18n.translate("RivalRebels.binoculars.target")) / 2f)), (int) (h * 0.85), 0xff0000);

				fr.draw(matrices, I18n.translate("RivalRebels.message.use")+" "+I18n.translate("RivalRebels.sneak")+" B-83 x2", (int) (w * 0.05), (int) (h * 0.95), 0xff0000);
				fr.draw(matrices, "Press C to select bomb type", (int) (w * 0.60), (int) (h * 0.95), 0xff0000);

				if ((ItemBinoculars.tasks > 0 || ItemBinoculars.carpet > 0) && ItemBinoculars.dist < 10f)
				{
					MinecraftClient.getInstance().getTextureManager().bindTexture(ItemBinoculars.c? RRIdentifiers.guicarpet: RRIdentifiers.ittaskb83);

                    RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
					float col = 1.0f - ItemBinoculars.dist / 10f;
					RenderSystem.setShaderColor(col, col, col, 1.0f);
                    buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
					buffer.vertex(w * 0.72, h * 0.85 + 16, -90).texture(0, 1).next();
					buffer.vertex(w * 0.72 + 16, h * 0.85 + 16, -90).texture(1, 1).next();
					buffer.vertex(w * 0.72 + 16, h * 0.85, -90).texture(1, 0).next();
					buffer.vertex(w * 0.72, h * 0.85, -90).texture(0, 0).next();
					t.draw();

					disp = "x" + ItemBinoculars.tasks;
					fr.draw(matrices, disp, (int) (w * 0.76), (int) (h * 0.85), ItemBinoculars.c?0xffff00:0xff0000);
					disp = "x" + ItemBinoculars.carpet;
					fr.draw(matrices, disp, (int) (w * 0.76), (int) (h * 0.9), ItemBinoculars.c?0xff0000:0xffff00);
					text = new TranslatableText("RivalRebels.tacticalnuke.name");
					if (!r) fr.draw(matrices, text, (int) ((w * 0.5) - (fr.getWidth(text) / 2f)), (int) (h * 0.71), 0x00ff00);
				}
				else if ((ItemBinoculars.tasks > 0 || ItemBinoculars.carpet > 0) && ItemBinoculars.hasLaptop)
				{
					RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    text = RRBlocks.controller.getName().append(" ").append(new TranslatableText("RivalRebels.controller.range"));
					fr.draw(matrices, text, (int) (w * 0.63), (int) (h * 0.87), 0xffff00);
				}
			}
		}
	}
}
