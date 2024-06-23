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
import assets.rivalrebels.common.item.weapon.ItemBinoculars;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import assets.rivalrebels.mixin.client.DrawContextAccessor;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

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
                renderRhodes(drawContext, MinecraftClient.getInstance().player, rhodes, tickDelta);
            }
        });
	}
	private void renderItems(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        ItemStack stack = player.getInventory().getMainHandStack();
        if (stack.isEmpty()) return;
        if (stack.getItem() instanceof ItemBinoculars) renderBinoculars(stack, context, client.getWindow(), player);
	}

	private void renderRhodes(DrawContext context, PlayerEntity player, EntityRhodes rhodes, float tickDelta) {
        counter--;
        if (counter <= 0)
        {
            counter = 0;
            RivalRebels.rrro.rhodes = null;
        }
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer fr = client.textRenderer;
        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
        ((DrawContextAccessor) context).callDrawTexturedQuad(
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

        RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
        ((DrawContextAccessor) context).callDrawTexturedQuad(
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

        if (glfwGetKey(client.getWindow().getHandle(), GLFW_KEY_H) == GLFW_PRESS) {
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
            ((DrawContextAccessor) context).callDrawTexturedQuad(
                RRIdentifiers.guirhodeshelp,
                    MathHelper.floor(w*0.25F),
                    MathHelper.floor(w*0.75F),
                    MathHelper.floor(h*0.25F),
                    MathHelper.floor(h*0.75F),
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
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
            float s = 8;
            float wl = w*0.5f;
            float hl = h*0.05f;
            ((DrawContextAccessor) context).callDrawTexturedQuad(
                new Identifier(RivalRebels.MODID, "textures/" + RenderRhodes.texfolders[rhodes.itexfolder] + rhodes.itexloc + ".png"),
                    MathHelper.floor(wl-s),
                    MathHelper.floor(wl+s),
                    MathHelper.floor(hl-s),
                    MathHelper.floor(hl+s),
                -90,
                1,
                0,
                1,
                0, 1F, 1F, 1F, 1F
            );
        }

        Text text = Text.of("Rival Rebels");
        context.drawText(fr, text, (int) (w * 0.05), (int) (h * 0.05), 0xffffff, false);
        text = Text.literal("Robot: ").append(rhodes.getName());
        context.drawText(fr, text, (int) (w * 0.05), (int) (h * 0.1), 0xffffff, false);
        text = RRBlocks.reactor.getName().append(": " + rhodes.health);
        float val = (rhodes.health / (float) RivalRebels.rhodesHealth);
        context.drawText(fr, text, (int) (w * 0.05), (int) (h * 0.15), (((int)((1-val)*255)&255)<<16) | (((int)(val*255)&255)<<8), false);
        float yaw = (player.getYaw() + 360000) % 360;
        text = (yaw >= 315 || yaw < 45) ? Text.translatable("RivalRebels.binoculars.south") : (yaw >= 45 && yaw < 135) ? Text.translatable("RivalRebels.binoculars.west") : (yaw >= 135 && yaw < 225) ? Text.translatable("RivalRebels.binoculars.north") : (yaw >= 225 && yaw < 315) ? Text.translatable("RivalRebels.binoculars.east") : Text.of("Whut");
        context.drawText(fr, text, (int) (w * 0.05), (int) (h * 0.2), 0xffffff, false);

        text = RRItems.einsten.getName().copy().append(": " + rhodes.energy);
        context.drawText(fr, text, (int) (w * 0.8), (int) (h * 0.05), (rhodes.laserOn>0)?0xff3333:0xffffff, false);
        text = Text.of("Jet: " + rhodes.energy);
        context.drawText(fr, text, (int) (w * 0.8), (int) (h * 0.1), RivalRebels.proxy.spacebar()?0x6666ff:0xffffff, false);
        text = RRBlocks.forcefieldnode.getName().append(": " + rhodes.energy);
        context.drawText(fr, text, (int) (w * 0.8), (int) (h * 0.15), rhodes.forcefield?0xBB88FF:0xffffff, false);
        text = RRItems.seekm202.getName().copy().append(": " + rhodes.rocketcount);
        context.drawText(fr, text, (int) (w * 0.8), (int) (h * 0.2), 0xffffff, false);
        text = (rhodes.isPlasma()?Text.of("Plasma: " + rhodes.flamecount) : (((MutableText)RRItems.fuel.getName()).append(": " + rhodes.flamecount)));
        context.drawText(fr, text, (int) (w * 0.8), (int) (h * 0.25), 0xffffff, false);
        context.drawText(fr, RRBlocks.nuclearBomb.getName().copy().append(": " + rhodes.nukecount), (int) (w * 0.8), (int) (h * 0.3), 0xffffff, false);
        context.drawText(fr, Text.of("Guard"), (int) (w * 0.8), (int) (h * 0.35), RivalRebels.proxy.g() ? 0xffff00 : 0xffffff, false);
        text = rhodes.getName().copy().append(" ").append(RRBlocks.controller.getName()).append(": H");
        context.drawText(fr, text, (int) (w * 0.05), (int) (h * 0.95), glfwGetKey(client.getWindow().getHandle(), GLFW_KEY_H) == GLFW_PRESS ? 0xffff00 : 0xffffff, false);
        if (rhodes.forcefield) {
            Tessellator t = Tessellator.getInstance();
            BufferBuilder buffer = t.getBuffer();
            RenderSystem.bindTexture(RivalRebelsCellularNoise.getCurrentRandomId());
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            buffer.vertex(0, h, -90).color(1, 1, 1, 0.7F).texture(0, h*0.003f).next();
            buffer.vertex(w, h, -90).color(1, 1, 1, 0.7F).texture(w*0.003f, h*0.003f).next();
            buffer.vertex(w, 0, -90).color(1, 1, 1, 0.7F).texture(w*0.003f, 0).next();
            buffer.vertex(0, 0, -90).color(1, 1, 1, 0.7F).texture(0, 0).next();
            t.draw();
            RenderSystem.disableBlend();
        }
    }

	private void renderBinoculars(ItemStack stack, DrawContext context, Window window, PlayerEntity player) {
        if (!stack.hasNbt()) return;
        if (MinecraftClient.getInstance().mouse.wasRightButtonClicked()) {
            tic++;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            TextRenderer tr = MinecraftClient.getInstance().textRenderer;
            int w = window.getScaledWidth();
            int h = window.getScaledHeight();

            RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
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
            // BufferBuilder buffer = t.getBuffer();
            // RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
            // RenderSystem.disableBlend();
            // context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            // buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION);
            // buffer.vertex(1, 100, -90);
            // buffer.vertex(100, 100, -90);
            // buffer.vertex(100, 1, -90);
            // buffer.vertex(1, 1, -90);
            // t.draw();
            // RenderSystem.enableBlend();
            NbtCompound nbt = stack.getNbt();
            BlockPos tpos = BlockPos.fromLong(nbt.getLong("tpos"));
            int tasks = nbt.getInt("tasks");
            int carpet = nbt.getInt("carpet");
            double dist = nbt.getDouble("dist");
            Block id = player.getWorld().getBlockState(tpos).getBlock();
            String disp;
            Text text;
            text = Text.of("X");
            if (id != Blocks.AIR) text = id.getName();
            context.drawText(tr, text, (int) ((w * 0.50) - (tr.getWidth(text) / 2f)), (int) (h * 0.18), 0x00ff00, false);
            if (!ItemBinoculars.tooFar)
                disp = "(" + tpos.getX() + ", " + tpos.getY() + ", " + tpos.getZ() + ")";
            else disp = "";
            context.drawText(tr, disp, (int) ((w * 0.50) - (tr.getWidth(disp) / 2f)), (int) (h * 0.13), 0x00ff00, false);
            if (tic % 30 == 0) r = !r;
            //if (nbt.getInt("ty") != -1 && nbt.getInt("cooldowntime") > 0) disp = ">" + nbt.getInt("tx") + ", " + nbt.getInt("ty") + ", " + nbt.getInt("tz") + "<";
            //else if (r) disp = ">                    <";
            //context.drawText(tr, disp, (int) ((w * 0.50) - (tr.getWidth(disp) / 2f)), (int) (h * 0.85), 0xff0000);
            text = Text.of("LTD RR");
            context.drawText(tr, text, (int) ((w * 0.50) - (tr.getWidth(text) / 2f)), (int) (h * 0.80), 0xffffff, false);
            disp = ((int) ItemBinoculars.distblock) + "m";
            context.drawText(tr, disp, (int) ((w * 0.637) - (tr.getWidth(disp) / 2f)), (int) (h * 0.205), 0xffffff, false);
            float yaw = (player.getYaw() + 360000) % 360;
            disp = (yaw >= 315 || yaw < 45) ? I18n.translate("RivalRebels.binoculars.south") : (yaw >= 45 && yaw < 135) ? I18n.translate("RivalRebels.binoculars.west") : (yaw >= 135 && yaw < 225) ? I18n.translate("RivalRebels.binoculars.north") : (yaw >= 225 && yaw < 315) ? I18n.translate("RivalRebels.binoculars.east") : "Whut";
            context.drawText(tr, disp, (int) ((w * 0.370) - (tr.getWidth(disp) / 2f)), (int) (h * 0.205), 0xffffff, false);
            if (ItemBinoculars.tooFar)
                context.drawText(tr, I18n.translate("RivalRebels.controller.range"), (int) ((w * 0.5) - (tr.getWidth(I18n.translate("RivalRebels.controller.range")) / 2f)), (int) (h * 0.85), 0xff0000, false);
            else if (ItemBinoculars.tooClose)
                context.drawText(tr, I18n.translate("RivalRebels.nextbattle.no"), (int) ((w * 0.5) - (tr.getWidth(I18n.translate("RivalRebels.nextbattle.no")) / 2f)), (int) (h * 0.85), 0xff0000, false);
                //else if (dist2 < 40)
                //{
                //	disp = I18n.translate("RivalRebels.nextbattle.no");
                //	context.drawText(tr, disp, (int) ((w * 0.5) - (tr.getWidth(disp) / 2f)), (int) (h * 0.90), 0xff0000, false);
                //	disp = (team == RivalRebelsTeam.OMEGA ? RivalRebels.omegaobj.getLocalizedName() : RivalRebels.omegaobj.getLocalizedName());
                //	context.drawText(tr, disp, (int) ((w * 0.5) - (tr.getWidth(disp) / 2f)), (int) (h * 0.94), 0xff0000, false);
                //}
            else if (ItemBinoculars.ready)
                context.drawText(tr, I18n.translate("RivalRebels.binoculars.target"), (int) ((w * 0.5) - (tr.getWidth(I18n.translate("RivalRebels.binoculars.target")) / 2f)), (int) (h * 0.85), 0xff0000, false);

            context.drawText(tr, I18n.translate("RivalRebels.message.use") + " " + I18n.translate("RivalRebels.sneak") + " B-83 x2", (int) (w * 0.05), (int) (h * 0.95), 0xff0000, false);
            context.drawText(tr, "Press C to select bomb type", (int) (w * 0.60), (int) (h * 0.95), 0xff0000, false);

            if ((tasks > 0 || carpet > 0) && dist < 10) {
                RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
                float col = (float) (1 - dist / 10);
                ((DrawContextAccessor) context).callDrawTexturedQuad(
                    ItemBinoculars.c ? RRIdentifiers.guicarpet : RRIdentifiers.ittaskb83,
                    MathHelper.floor(w * 0.72),
                    MathHelper.floor(w * 0.72 + 16),
                    MathHelper.floor(h * 0.85 + 16),
                    MathHelper.floor(h * 0.85),
                    -90,
                    0,
                    1,
                    0,
                    1,
                    col, col, col, 1.0F
                );

                text = Text.of("x" + tasks);
                context.drawText(tr, text, (int) (w * 0.76), (int) (h * 0.85), ItemBinoculars.c ? 0xffff00 : 0xff0000, false);
                text = Text.of("x" + carpet);
                context.drawText(tr, text, (int) (w * 0.76), (int) (h * 0.9), ItemBinoculars.c ? 0xff0000 : 0xffff00, false);
                text = Text.translatable("RivalRebels.tacticalnuke.name");
                if (!r)
                    context.drawText(tr, text, (int) ((w * 0.5) - (tr.getWidth(text) / 2f)), (int) (h * 0.71), 0x00ff00, false);
            } else if ((tasks > 0 || carpet > 0) && ItemBinoculars.hasLaptop) {
                context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                text = RRBlocks.controller.getName().append(" ").append(Text.translatable("RivalRebels.controller.range"));
                context.drawText(tr, text, (int) (w * 0.63), (int) (h * 0.87), 0xffff00, false);
            }
        }
	}
}
