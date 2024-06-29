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
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.BinocularData;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.item.weapon.ItemBinoculars;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import assets.rivalrebels.mixin.client.DrawContextAccessor;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static org.lwjgl.glfw.GLFW.*;

public class RivalRebelsRenderOverlay
{
	public int		tic	= 0;
	public boolean	r	= false;
	public EntityRhodes rhodes = null;
	public long counter = 0;

	public void init() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            renderItems(drawContext);

            if (rhodes != null) {
                renderRhodes(drawContext, Minecraft.getInstance().player, rhodes, tickDelta.getRealtimeDeltaTicks());
            }
        });
	}
	private void renderItems(GuiGraphics context) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        ItemStack stack = player.getInventory().getSelected();
        if (stack.isEmpty()) return;
        if (stack.getItem() instanceof ItemBinoculars) renderBinoculars(stack, context, client.getWindow(), player);
	}

	private void renderRhodes(GuiGraphics graphics, Player player, EntityRhodes rhodes, float tickDelta) {
        counter--;
        if (counter <= 0)
        {
            counter = 0;
            RivalRebels.rrro.rhodes = null;
        }
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        Minecraft client = Minecraft.getInstance();
        Font fr = client.font;
        int w = client.getWindow().getGuiScaledWidth();
        int h = client.getWindow().getGuiScaledHeight();

        RenderSystem.defaultBlendFunc();
        ((DrawContextAccessor) graphics).callDrawTexturedQuad(
            RRIdentifiers.guirhodesline,
            0,
            w,
            h,
            0,
            -90,
            0,
            1,
            0,
            1,
            1.0F, 0.0F, 0.0F, 0.5F
        );

        RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
        ((DrawContextAccessor) graphics).callDrawTexturedQuad(
            RRIdentifiers.guirhodesout,
            0,
            w,
            h,
            0,
            -90,
            1,
            0,
            1,
            0,
            0.0F, 0.0F, 0.0F, 0.333f
        );

        if (glfwGetKey(client.getWindow().getWindow(), GLFW_KEY_H) == GLFW_PRESS) {
            RenderSystem.defaultBlendFunc();
            ((DrawContextAccessor) graphics).callDrawTexturedQuad(
                RRIdentifiers.guirhodeshelp,
                    Mth.floor(w*0.25F),
                    Mth.floor(w*0.75F),
                    Mth.floor(h*0.25F),
                    Mth.floor(h*0.75F),
                -90,
                1,
                0,
                1,
                0,
                1F,
                1F,
                1F,
                1F
            );
        }

        if (rhodes.itexfolder > -1 && rhodes.itexfolder < 4) {
            RenderSystem.defaultBlendFunc();
            float s = 8;
            float wl = w*0.5f;
            float hl = h*0.05f;
            ((DrawContextAccessor) graphics).callDrawTexturedQuad(
                RRIdentifiers.create("textures/" + RenderRhodes.texfolders[rhodes.itexfolder] + rhodes.itexloc + ".png"),
                    Mth.floor(wl-s),
                    Mth.floor(wl+s),
                    Mth.floor(hl-s),
                    Mth.floor(hl+s),
                -90,
                1,
                0,
                1,
                0, 1F, 1F, 1F, 1F
            );
        }

        Component text = Component.nullToEmpty("Rival Rebels");
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.05), 0xffffff, false);
        text = Component.literal("Robot: ").append(rhodes.getName());
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.1), 0xffffff, false);
        text = RRBlocks.reactor.getName().append(": " + rhodes.health);
        float val = (rhodes.health / (float) RivalRebels.rhodesHealth);
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.15), (((int)((1-val)*255)&255)<<16) | (((int)(val*255)&255)<<8), false);
        float yaw = (player.getYRot() + 360000) % 360;
        text = (yaw >= 315 || yaw < 45) ? Component.translatable("RivalRebels.binoculars.south") : (yaw >= 45 && yaw < 135) ? Component.translatable("RivalRebels.binoculars.west") : (yaw >= 135 && yaw < 225) ? Component.translatable("RivalRebels.binoculars.north") : (yaw >= 225 && yaw < 315) ? Component.translatable("RivalRebels.binoculars.east") : Component.nullToEmpty("Whut");
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.2), 0xffffff, false);

        text = RRItems.einsten.getDescription().copy().append(": " + rhodes.energy);
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.05), (rhodes.laserOn>0)?0xff3333:0xffffff, false);
        text = Component.nullToEmpty("Jet: " + rhodes.energy);
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.1), RivalRebels.proxy.spacebar()?0x6666ff:0xffffff, false);
        text = RRBlocks.forcefieldnode.getName().append(": " + rhodes.energy);
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.15), rhodes.forcefield?0xBB88FF:0xffffff, false);
        text = RRItems.seekm202.getDescription().copy().append(": " + rhodes.rocketcount);
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.2), 0xffffff, false);
        text = (rhodes.isPlasma()?Component.nullToEmpty("Plasma: " + rhodes.flamecount) : (((MutableComponent)RRItems.fuel.getDescription()).append(": " + rhodes.flamecount)));
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.25), 0xffffff, false);
        graphics.drawString(fr, RRBlocks.nuclearBomb.getName().copy().append(": " + rhodes.nukecount), (int) (w * 0.8), (int) (h * 0.3), 0xffffff, false);
        graphics.drawString(fr, Component.nullToEmpty("Guard"), (int) (w * 0.8), (int) (h * 0.35), RivalRebels.proxy.g() ? 0xffff00 : 0xffffff, false);
        text = rhodes.getName().copy().append(" ").append(RRBlocks.controller.getName()).append(": H");
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.95), glfwGetKey(client.getWindow().getWindow(), GLFW_KEY_H) == GLFW_PRESS ? 0xffff00 : 0xffffff, false);
        if (rhodes.forcefield) {
            Tesselator t = Tesselator.getInstance();
            RenderSystem.bindTexture(RivalRebelsCellularNoise.getCurrentRandomId());
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            BufferBuilder buffer = t.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            buffer.addVertex(graphics.pose().last(), 0, h, -90).setColor(1, 1, 1, 0.7F).setUv(0, h*0.003f);
            buffer.addVertex(graphics.pose().last(), w, h, -90).setColor(1, 1, 1, 0.7F).setUv(w*0.003f, h*0.003f);
            buffer.addVertex(graphics.pose().last(), w, 0, -90).setColor(1, 1, 1, 0.7F).setUv(w*0.003f, 0);
            buffer.addVertex(graphics.pose().last(), 0, 0, -90).setColor(1, 1, 1, 0.7F).setUv(0, 0);
            BufferUploader.drawWithShader(buffer.buildOrThrow());
            RenderSystem.disableBlend();
        }
    }

	private void renderBinoculars(ItemStack stack, GuiGraphics context, Window window, Player player) {
        if (!stack.has(RRComponents.BINOCULAR_DATA)) return;
        if (Minecraft.getInstance().mouseHandler.isRightPressed()) {
            tic++;
            RenderSystem.depthMask(false);
            Font tr = Minecraft.getInstance().font;
            int w = window.getGuiScaledWidth();
            int h = window.getGuiScaledHeight();

            RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableBlend();
            ((DrawContextAccessor) context).callDrawTexturedQuad(
                RRIdentifiers.guibinoculars,
                0,
                w,
                h,
                0,
                -90,
                0,
                1,
                0,
                1,
                1.0F, 1.0F, 1.0F, 1.0F
            );

            ((DrawContextAccessor) context).callDrawTexturedQuad(
                RRIdentifiers.guibinocularsoverlay,
                0,
                w,
                h,
                0,
                -90,
                0,
                1,
                0,
                1,
                0.333F, 0.333F, 0.333F, 0.5F
            );

            // Tessellator t = Tessellator.getInstance();
            // BufferBuilder buffer = buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION);
            // RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
            // RenderSystem.disableBlend();
            // buffer.addVertex(1, 100, -90).setColor(CommonColors.WHITE);
            // buffer.addVertex(100, 100, -90).setColor(CommonColors.WHITE);
            // buffer.addVertex(100, 1, -90).setColor(CommonColors.WHITE);
            // buffer.addVertex(1, 1, -90).setColor(CommonColors.WHITE);
            // t.draw();
            // RenderSystem.enableBlend();
            BinocularData binocularData = stack.get(RRComponents.BINOCULAR_DATA);
            BlockPos tpos = binocularData.tpos();
            int tasks = binocularData.tasks();
            int carpet = binocularData.carpet();
            double dist = binocularData.dist();
            Block id = player.level().getBlockState(tpos).getBlock();
            String disp;
            Component text;
            text = Component.nullToEmpty("X");
            if (id != Blocks.AIR) text = id.getName();
            context.drawString(tr, text, (int) ((w * 0.50) - (tr.width(text) / 2f)), (int) (h * 0.18), 0x00ff00, false);
            if (!ItemBinoculars.tooFar)
                disp = "(" + tpos.getX() + ", " + tpos.getY() + ", " + tpos.getZ() + ")";
            else disp = "";
            context.drawString(tr, disp, (int) ((w * 0.50) - (tr.width(disp) / 2f)), (int) (h * 0.13), 0x00ff00, false);
            if (tic % 30 == 0) r = !r;
            //if (nbt.getInt("ty") != -1 && nbt.getInt("cooldowntime") > 0) disp = ">" + nbt.getInt("tx") + ", " + nbt.getInt("ty") + ", " + nbt.getInt("tz") + "<";
            //else if (r) disp = ">                    <";
            //context.drawText(tr, disp, (int) ((w * 0.50) - (tr.getWidth(disp) / 2f)), (int) (h * 0.85), 0xff0000);
            text = Component.nullToEmpty("LTD RR");
            context.drawString(tr, text, (int) ((w * 0.50) - (tr.width(text) / 2f)), (int) (h * 0.80), 0xffffff, false);
            disp = ((int) ItemBinoculars.distblock) + "m";
            context.drawString(tr, disp, (int) ((w * 0.637) - (tr.width(disp) / 2f)), (int) (h * 0.205), 0xffffff, false);
            float yaw = (player.getYRot() + 360000) % 360;
            disp = (yaw >= 315 || yaw < 45) ? I18n.get("RivalRebels.binoculars.south") : (yaw >= 45 && yaw < 135) ? I18n.get("RivalRebels.binoculars.west") : (yaw >= 135 && yaw < 225) ? I18n.get("RivalRebels.binoculars.north") : (yaw >= 225 && yaw < 315) ? I18n.get("RivalRebels.binoculars.east") : "Whut";
            context.drawString(tr, disp, (int) ((w * 0.370) - (tr.width(disp) / 2f)), (int) (h * 0.205), 0xffffff, false);
            if (ItemBinoculars.tooFar)
                context.drawString(tr, I18n.get("RivalRebels.controller.range"), (int) ((w * 0.5) - (tr.width(I18n.get("RivalRebels.controller.range")) / 2f)), (int) (h * 0.85), 0xff0000, false);
            else if (ItemBinoculars.tooClose)
                context.drawString(tr, I18n.get("RivalRebels.nextbattle.no"), (int) ((w * 0.5) - (tr.width(I18n.get("RivalRebels.nextbattle.no")) / 2f)), (int) (h * 0.85), 0xff0000, false);
                //else if (dist2 < 40)
                //{
                //	disp = I18n.translate("RivalRebels.nextbattle.no");
                //	context.drawText(tr, disp, (int) ((w * 0.5) - (tr.getWidth(disp) / 2f)), (int) (h * 0.90), 0xff0000, false);
                //	disp = (team == RivalRebelsTeam.OMEGA ? RivalRebels.omegaobj.getLocalizedName() : RivalRebels.omegaobj.getLocalizedName());
                //	context.drawText(tr, disp, (int) ((w * 0.5) - (tr.getWidth(disp) / 2f)), (int) (h * 0.94), 0xff0000, false);
                //}
            else if (ItemBinoculars.ready)
                context.drawString(tr, I18n.get("RivalRebels.binoculars.target"), (int) ((w * 0.5) - (tr.width(I18n.get("RivalRebels.binoculars.target")) / 2f)), (int) (h * 0.85), 0xff0000, false);

            context.drawString(tr, I18n.get("RivalRebels.message.use") + " " + I18n.get("RivalRebels.sneak") + " B-83 x2", (int) (w * 0.05), (int) (h * 0.95), 0xff0000, false);
            context.drawString(tr, "Press C to select bomb type", (int) (w * 0.60), (int) (h * 0.95), 0xff0000, false);

            if ((tasks > 0 || carpet > 0) && dist < 10) {
                RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
                float col = (float) (1 - dist / 10);
                ((DrawContextAccessor) context).callDrawTexturedQuad(
                    ItemBinoculars.c ? RRIdentifiers.guicarpet : RRIdentifiers.ittaskb83,
                    Mth.floor(w * 0.72),
                    Mth.floor(w * 0.72 + 16),
                    Mth.floor(h * 0.85 + 16),
                    Mth.floor(h * 0.85),
                    -90,
                    0,
                    1,
                    0,
                    1,
                    col, col, col, 1.0F
                );

                text = Component.nullToEmpty("x" + tasks);
                context.drawString(tr, text, (int) (w * 0.76), (int) (h * 0.85), ItemBinoculars.c ? 0xffff00 : 0xff0000, false);
                text = Component.nullToEmpty("x" + carpet);
                context.drawString(tr, text, (int) (w * 0.76), (int) (h * 0.9), ItemBinoculars.c ? 0xff0000 : 0xffff00, false);
                text = Component.translatable("RivalRebels.tacticalnuke.name");
                if (!r)
                    context.drawString(tr, text, (int) ((w * 0.5) - (tr.width(text) / 2f)), (int) (h * 0.71), 0x00ff00, false);
            } else if ((tasks > 0 || carpet > 0) && ItemBinoculars.hasLaptop) {
                context.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                text = RRBlocks.controller.getName().append(" ").append(Component.translatable("RivalRebels.controller.range"));
                context.drawString(tr, text, (int) (w * 0.63), (int) (h * 0.87), 0xffff00, false);
            }
        }
	}
}
