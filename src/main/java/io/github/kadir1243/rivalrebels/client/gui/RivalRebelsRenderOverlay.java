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
package io.github.kadir1243.rivalrebels.client.gui;

import io.github.kadir1243.rivalrebels.RRClient;
import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.BinocularData;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.item.weapon.ItemBinoculars;
import io.github.kadir1243.rivalrebels.common.noise.RivalRebelsCellularNoise;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import io.github.kadir1243.rivalrebels.mixin.client.GuiGraphicsAccessor;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import static org.lwjgl.glfw.GLFW.*;

@OnlyIn(Dist.CLIENT)
public class RivalRebelsRenderOverlay {
	public int		tic	= 0;
	public boolean	r	= false;
	public EntityRhodes rhodes = null;
	public float counter = 0;

	public void init(IEventBus bus) {
        bus.addListener(RegisterGuiLayersEvent.class, event -> {
            event.registerAboveAll(RRIdentifiers.create("render_binoculars"), (guiGraphics, deltaTracker) -> renderItems(guiGraphics));
            event.registerAboveAll(RRIdentifiers.create("render_rhodes"), (guiGraphics, deltaTracker) -> renderRhodes(guiGraphics, Minecraft.getInstance().player, rhodes, deltaTracker));
        });
	}

    public void setOverlay(EntityRhodes rhodes) {
        if (rhodes.rider == Minecraft.getInstance().player) {
            this.counter = 10;
            this.rhodes = rhodes;
        }
    }

	private void renderItems(GuiGraphics graphics) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        ItemStack stack = player.getInventory().getSelected();
        if (stack.isEmpty()) return;
        if (stack.getItem() instanceof ItemBinoculars) renderBinoculars(stack, graphics, player);
	}

	private void renderRhodes(GuiGraphics graphics, Player player, EntityRhodes rhodes, DeltaTracker tracker) {
        if (rhodes == null) return;
        float deltaTicks = tracker.getGameTimeDeltaPartialTick(true);
        counter = Mth.lerp(deltaTicks, counter, counter - 1);
        if (counter <= 0) {
            counter = 0;
            RRClient.rrro.rhodes = null;
        }
        RenderSystem.depthMask(false);
        Minecraft client = Minecraft.getInstance();
        Font fr = client.font;
        int w = graphics.guiWidth();
        int h = graphics.guiHeight();

        RenderSystem.defaultBlendFunc();
        ((GuiGraphicsAccessor) graphics).blit(
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
        ((GuiGraphicsAccessor) graphics).blit(
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
            ((GuiGraphicsAccessor) graphics).blit(
                RRIdentifiers.guirhodeshelp,
                    Mth.floor(w*0.25F),
                    Mth.floor(w*0.75F),
                    Mth.floor(h*0.25F),
                    Mth.floor(h*0.75F),
                -90,
                1,
                0,
                1,
                0
            );
        }

        if (!rhodes.getFlagTextureLocation().isBlank()) {
            RenderSystem.defaultBlendFunc();
            float s = 8;
            float wl = w*0.5f;
            float hl = h*0.05f;
            ((GuiGraphicsAccessor) graphics).blit(
                RRIdentifiers.create("textures/" + rhodes.getFlagTextureLocation() + ".png"),
                    Mth.floor(wl-s),
                    Mth.floor(wl+s),
                    Mth.floor(hl-s),
                    Mth.floor(hl+s),
                -90,
                1,
                0,
                1,
                0
            );
        }

        Component text = Component.literal("Rival Rebels");
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.05), 0xffffff, false);
        text = Component.literal("Robot: ").append(rhodes.getName());
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.1), 0xffffff, false);
        text = RRBlocks.reactor.get().getName().append(": " + rhodes.getHealth());
        float val = (rhodes.getHealth() / (float) RRConfig.SERVER.getRhodesHealth());
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.15), (((int)((1-val)*255)&255)<<16) | (((int)(val*255)&255)<<8), false);
        float yaw = (player.getYRot() + 360000) % 360;
        text = (yaw >= 315 || yaw < 45) ? Component.translatable("RivalRebels.binoculars.south") : (yaw >= 45 && yaw < 135) ? Component.translatable("RivalRebels.binoculars.west") : (yaw >= 135 && yaw < 225) ? Component.translatable("RivalRebels.binoculars.north") : (yaw >= 225 && yaw < 315) ? Component.translatable("RivalRebels.binoculars.east") : Component.nullToEmpty("Whut");
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.2), 0xffffff, false);

        text = RRItems.einsten.asItem().getDescription().copy().append(": " + rhodes.getEnergy());
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.05), rhodes.isAnyLaserEnabled()?0xff3333:0xffffff, false);
        text = Component.nullToEmpty("Jet: " + rhodes.getEnergy());
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.1), RRClient.RHODES_JUMP_KEY.isDown() ?0x6666ff:0xffffff, false);
        text = RRBlocks.forcefieldnode.get().getName().append(": " + rhodes.getEnergy());
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.15), rhodes.isForceFieldEnabled()?0xBB88FF:0xffffff, false);
        text = RRItems.seekm202.asItem().getDescription().copy().append(": " + rhodes.getRocketCount());
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.2), 0xffffff, false);
        text = (rhodes.isPlasma()?Component.nullToEmpty("Plasma: " + rhodes.getFlameCount()) : (((MutableComponent)RRItems.fuel.asItem().getDescription()).append(": " + rhodes.getFlameCount())));
        graphics.drawString(fr, text, (int) (w * 0.8), (int) (h * 0.25), 0xffffff, false);
        graphics.drawString(fr, RRBlocks.nuclearBomb.get().getName().copy().append(": " + rhodes.getNukeCount()), (int) (w * 0.8), (int) (h * 0.3), 0xffffff, false);
        graphics.drawString(fr, Component.nullToEmpty("Guard"), (int) (w * 0.8), (int) (h * 0.35), RRClient.RHODES_GUARD_KEY.isDown() ? 0xffff00 : 0xffffff, false);
        text = rhodes.getName().copy().append(" ").append(RRBlocks.controller.get().getName()).append(": H");
        graphics.drawString(fr, text, (int) (w * 0.05), (int) (h * 0.95), glfwGetKey(client.getWindow().getWindow(), GLFW_KEY_H) == GLFW_PRESS ? 0xffff00 : 0xffffff, false);
        if (rhodes.isForceFieldEnabled()) {
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
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

	private void renderBinoculars(ItemStack stack, GuiGraphics graphics, Player player) {
        if (!stack.has(RRComponents.BINOCULAR_DATA)) return;
        if (Minecraft.getInstance().mouseHandler.isRightPressed()) {
            tic++;
            RenderSystem.depthMask(false);
            Font tr = Minecraft.getInstance().font;
            int w = graphics.guiWidth();
            int h = graphics.guiHeight();

            RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
            ((GuiGraphicsAccessor) graphics).blit(
                RRIdentifiers.guibinoculars,
                0,
                w,
                h,
                0,
                -90,
                0,
                1,
                0,
                1
            );

            ((GuiGraphicsAccessor) graphics).blit(
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
            // BufferBuilder buffer = t.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION);
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
            BlockState id = player.level().getBlockState(tpos);
            Component text;
            text = Component.nullToEmpty("X");
            if (!id.isAir()) text = id.getBlock().getName();
            graphics.drawString(tr, text, (int) ((w * 0.50) - (tr.width(text) / 2f)), (int) (h * 0.18), 0x00ff00, false);
            if (!ItemBinoculars.tooFar)
                text = Component.literal("(" + tpos.getX() + ", " + tpos.getY() + ", " + tpos.getZ() + ")");
            else text = Component.empty();
            graphics.drawString(tr, text, (int) ((w * 0.50) - (tr.width(text) / 2f)), (int) (h * 0.13), 0x00ff00, false);
            if (tic % 30 == 0) r = !r;
            //if (nbt.getInt("ty") != -1 && nbt.getInt("cooldowntime") > 0) text = Component.literal(">" + nbt.getInt("tx") + ", " + nbt.getInt("ty") + ", " + nbt.getInt("tz") + "<");
            //else if (r) text = Component.literal(">                    <");
            //graphics.drawText(tr, text, (int) ((w * 0.50) - (tr.getWidth(text) / 2f)), (int) (h * 0.85), 0xff0000);
            text = Component.nullToEmpty("LTD RR");
            graphics.drawString(tr, text, (int) ((w * 0.50) - (tr.width(text) / 2f)), (int) (h * 0.80), 0xffffff, false);
            text = Component.literal(((int) ItemBinoculars.distblock) + "m");
            graphics.drawString(tr, text, (int) ((w * 0.637) - (tr.width(text) / 2f)), (int) (h * 0.205), 0xffffff, false);
            float yaw = (player.getYRot() + 360000) % 360;
            text = (yaw >= 315 || yaw < 45) ? Component.translatable("RivalRebels.binoculars.south") : (yaw >= 45 && yaw < 135) ? Component.translatable("RivalRebels.binoculars.west") : (yaw >= 135 && yaw < 225) ? Component.translatable("RivalRebels.binoculars.north") : (yaw >= 225 && yaw < 315) ? Component.translatable("RivalRebels.binoculars.east") : Component.literal("Whut");
            graphics.drawString(tr, text, (int) ((w * 0.370) - (tr.width(text) / 2f)), (int) (h * 0.205), 0xffffff, false);
            if (ItemBinoculars.tooFar)
                graphics.drawString(tr, Component.translatable("RivalRebels.controller.range"), (int) ((w * 0.5) - (tr.width(Component.translatable("RivalRebels.controller.range")) / 2f)), (int) (h * 0.85), 0xff0000, false);
            else if (ItemBinoculars.tooClose)
                graphics.drawString(tr, Component.translatable("RivalRebels.nextbattle.no"), (int) ((w * 0.5) - (tr.width(Component.translatable("RivalRebels.nextbattle.no")) / 2f)), (int) (h * 0.85), 0xff0000, false);
                //else if (dist2 < 40)
                //{
                //	text = Component.translatable("RivalRebels.nextbattle.no");
                //	graphics.drawText(tr, text, (int) ((w * 0.5) - (tr.getWidth(text) / 2f)), (int) (h * 0.90), 0xff0000, false);
                //	text = Component.literal(team == RivalRebelsTeam.OMEGA ? RivalRebels.omegaobj.getLocalizedName() : RivalRebels.omegaobj.getLocalizedName());
                //	graphics.drawText(tr, text, (int) ((w * 0.5) - (tr.getWidth(text) / 2f)), (int) (h * 0.94), 0xff0000, false);
                //}
            else if (ItemBinoculars.ready)
                graphics.drawString(tr, Component.translatable("RivalRebels.binoculars.target"), (int) ((w * 0.5) - (tr.width(Component.translatable("RivalRebels.binoculars.target")) / 2f)), (int) (h * 0.85), 0xff0000, false);

            graphics.drawString(tr, Component.translatable("RivalRebels.message.use").append(" ").append(Component.translatable(Translations.SHIFT_CLICK.toLanguageKey())).append(" B-83 x2"), (int) (w * 0.05), (int) (h * 0.95), 0xff0000, false);
            graphics.drawString(tr, "Press C to select bomb type", (float) (w * 0.60), (float) (h * 0.95), 0xff0000, false);

            if ((tasks > 0 || carpet > 0) && dist < 10) {
                RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
                float col = (float) (1 - dist / 10);
                ((GuiGraphicsAccessor) graphics).blit(
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
                graphics.drawString(tr, text, (int) (w * 0.76), (int) (h * 0.85), ItemBinoculars.c ? 0xffff00 : 0xff0000, false);
                text = Component.nullToEmpty("x" + carpet);
                graphics.drawString(tr, text, (int) (w * 0.76), (int) (h * 0.9), ItemBinoculars.c ? 0xff0000 : 0xffff00, false);
                text = Component.translatable("RivalRebels.tacticalnuke.name");
                if (!r)
                    graphics.drawString(tr, text, (int) ((w * 0.5) - (tr.width(text) / 2f)), (int) (h * 0.71), 0x00ff00, false);
            } else if ((tasks > 0 || carpet > 0) && ItemBinoculars.hasLaptop) {
                text = RRBlocks.controller.get().getName().append(" ").append(Component.translatable("RivalRebels.controller.range"));
                graphics.drawString(tr, text, (int) (w * 0.63), (int) (h * 0.87), 0xffff00, false);
            }
        }
	}
}
