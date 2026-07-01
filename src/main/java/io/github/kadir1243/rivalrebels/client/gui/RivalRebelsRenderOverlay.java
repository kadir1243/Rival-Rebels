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

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import io.github.kadir1243.rivalrebels.RRClient;
import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.BinocularData;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.item.weapon.ItemBinoculars;
import io.github.kadir1243.rivalrebels.common.noise.RivalRebelsCellularNoise;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import org.jspecify.annotations.Nullable;
import org.joml.Matrix3x2f;

@OnlyIn(Dist.CLIENT)
public class RivalRebelsRenderOverlay {
	public int		tic	= 0;
	public boolean	r	= false;
	public EntityRhodes rhodes = null;
	public float counter = 0;

	public void init(IEventBus bus) {
        bus.addListener(RegisterGuiLayersEvent.class, event -> {
            event.registerBelowAll(RRIdentifiers.create("render_binoculars"), (guiGraphics, deltaTracker) -> renderItems(guiGraphics));
            event.registerAboveAll(RRIdentifiers.create("render_rhodes"), (guiGraphics, deltaTracker) -> renderRhodes(guiGraphics, Minecraft.getInstance().player, rhodes, deltaTracker));
        });
	}

    public void setOverlay(EntityRhodes rhodes) {
        if (rhodes.rider == Minecraft.getInstance().player) {
            this.counter = 10;
            this.rhodes = rhodes;
        }
    }

	private void renderItems(GuiGraphicsExtractor graphics) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        ItemStack stack = player.getInventory().getSelectedItem();
        if (stack.is(RRItems.binoculars)) renderBinoculars(stack, graphics, player);
	}

	private void renderRhodes(GuiGraphicsExtractor graphics, Player player, EntityRhodes rhodes, DeltaTracker tracker) {
        if (rhodes == null) return;
        float deltaTicks = tracker.getGameTimeDeltaPartialTick(true);
        counter = Mth.lerp(deltaTicks, counter, counter - 1);
        if (counter <= 0) {
            counter = 0;
            RRClient.rrro.rhodes = null;
        }
        Minecraft client = Minecraft.getInstance();
        Font fr = client.font;
        int w = graphics.guiWidth();
        int h = graphics.guiHeight();

        graphics.blit(
            RenderPipelines.GUI_TEXTURED,
            RRTextures.guirhodesline.location(),
            0,
            h,
            0,
            0,
            w,
            -h,
            1600,
            900,
            1600,
            900,
            ARGB.colorFromFloat(0.5F, 1.0F, 0.0F, 0.0F)
        );

        graphics.blit(
            RenderPipelines.GUI_TEXTURED,
            RRTextures.guirhodesout,
            0,
            h,
            1600,
            900,
            w,
            -h,
            -1600,
            -900,
            1600,
            900,
            ARGB.colorFromFloat(0.333F, 0.0F, 0.0F, 0.0F)
        );

        if (InputConstants.isKeyDown(client.getWindow(), InputConstants.KEY_H)) {
            graphics.blit(
                RRTextures.guirhodeshelp,
                Mth.floor(w*0.25F),
                Mth.floor(h*0.25F),
                Mth.floor(w*0.75F),
                Mth.floor(h*0.75F),
                -90,
                1,
                0,
                1
            );
        }

        if (!rhodes.getFlagTextureLocation().isBlank()) {
            float s = 8;
            float wl = w*0.5f;
            float hl = h*0.05f;
            graphics.blit(
                RRIdentifiers.create("textures/" + rhodes.getFlagTextureLocation() + ".png"),
                Mth.floor(wl-s),
                Mth.floor(hl-s),
                Mth.floor(wl+s),
                Mth.floor(hl+s),
                -90,
                1,
                0,
                1
            );
        }

        Component text = Component.literal("Rival Rebels");
        graphics.text(fr, text, (int) (w * 0.05), (int) (h * 0.05), CommonColors.WHITE, false);
        text = Component.literal("Robot: ").append(rhodes.getName());
        graphics.text(fr, text, (int) (w * 0.05), (int) (h * 0.1), CommonColors.WHITE, false);
        text = RRBlocks.reactor.get().getName().append(": " + rhodes.getHealth());
        float val = (rhodes.getHealth() / (float) RRConfig.SERVER.getRhodesHealth());
        int col = ARGB.color((int) ((1-val)*255), (int) (val*255), 0);
        graphics.text(fr, text, (int) (w * 0.05), (int) (h * 0.15), col, false);
        float yaw = (player.getYRot() + 360000) % 360;
        text = (yaw >= 315 || yaw < 45) ? Translations.BINOCULARS_SOUTH.translate() : (yaw >= 45 && yaw < 135) ? Translations.BINOCULARS_WEST.translate() : (yaw >= 135 && yaw < 225) ? Translations.BINOCULARS_NORTH.translate() : (yaw >= 225 && yaw < 315) ? Translations.BINOCULARS_EAST.translate() : Component.literal("Whut");
        graphics.text(fr, text, (int) (w * 0.05), (int) (h * 0.2), CommonColors.WHITE, false);

        text = RRItems.einsten.toStack().getItemName().copy().append(": " + rhodes.getEnergy());
        graphics.text(fr, text, (int) (w * 0.8), (int) (h * 0.05), rhodes.isAnyLaserEnabled()?0xffff3333:CommonColors.WHITE, false);
        text = Component.literal("Jet: " + rhodes.getEnergy());
        graphics.text(fr, text, (int) (w * 0.8), (int) (h * 0.1), RRClient.RHODES_JUMP_KEY.isDown() ?0xff6666ff:CommonColors.WHITE, false);
        text = RRBlocks.forcefieldnode.get().getName().append(": " + rhodes.getEnergy());
        graphics.text(fr, text, (int) (w * 0.8), (int) (h * 0.15), rhodes.isForceFieldEnabled()?0xffBB88FF:CommonColors.WHITE, false);
        text = RRItems.seekm202.toStack().getItemName().copy().append(": " + rhodes.getRocketCount());
        graphics.text(fr, text, (int) (w * 0.8), (int) (h * 0.2), CommonColors.WHITE, false);
        text = (rhodes.isPlasma()?Component.literal("Plasma: " + rhodes.getFlameCount()) : (RRItems.fuel.toStack().getItemName().copy().append(": " + rhodes.getFlameCount())));
        graphics.text(fr, text, (int) (w * 0.8), (int) (h * 0.25), CommonColors.WHITE, false);
        graphics.text(fr, RRBlocks.nuclearBomb.get().getName().copy().append(": " + rhodes.getNukeCount()), (int) (w * 0.8), (int) (h * 0.3), CommonColors.WHITE, false);
        graphics.text(fr, Component.literal("Guard"), (int) (w * 0.8), (int) (h * 0.35), RRClient.RHODES_GUARD_KEY.isDown() ? CommonColors.YELLOW : CommonColors.WHITE, false);
        text = rhodes.getName().copy().append(" ").append(RRBlocks.controller.get().getName()).append(": H");
        graphics.text(fr, text, (int) (w * 0.05), (int) (h * 0.95), InputConstants.isKeyDown(client.getWindow(), InputConstants.KEY_H) ? CommonColors.YELLOW : CommonColors.WHITE, false);
        if (rhodes.isForceFieldEnabled()) {
            graphics.submitGuiElementRenderState(new GuiElementRenderState() {
                private final Matrix3x2f pose = new Matrix3x2f(graphics.pose());
                @Nullable
                private final ScreenRectangle scissorArea = graphics.peekScissorStack();
                private final ScreenRectangle bounds = getBounds(0, 0, w, h, pose, scissorArea);

                @Override
                public void buildVertices(VertexConsumer consumer) {
                    consumer.addVertexWith2DPose(pose, 0, h).setColor(1, 1, 1, 0.7F).setUv(0, h*0.003f).setLight(LightCoordsUtil.FULL_BRIGHT);
                    consumer.addVertexWith2DPose(pose, w, h).setColor(1, 1, 1, 0.7F).setUv(w*0.003f, h*0.003f).setLight(LightCoordsUtil.FULL_BRIGHT);
                    consumer.addVertexWith2DPose(pose, w, 0).setColor(1, 1, 1, 0.7F).setUv(w*0.003f, 0).setLight(LightCoordsUtil.FULL_BRIGHT);
                    consumer.addVertexWith2DPose(pose, 0, 0).setColor(1, 1, 1, 0.7F).setUv(0, 0).setLight(LightCoordsUtil.FULL_BRIGHT);
                }

                @Override
                public RenderPipeline pipeline() {
                    return RRRenderTypes.CELLULAR_NOISE_PIPELINE;
                }

                @Override
                public TextureSetup textureSetup() {
                    return TextureSetup.singleTexture(RivalRebelsCellularNoise.getCurrentRandomId().getTextureView(), RenderSystem.getSamplerCache().getRepeat(FilterMode.NEAREST));
                }

                @Override
                public @Nullable ScreenRectangle scissorArea() {
                    return scissorArea;
                }

                @Override
                public @Nullable ScreenRectangle bounds() {
                    return bounds;
                }

                @Nullable
                private static ScreenRectangle getBounds(
                    int x0, int y0, int x1, int y1, Matrix3x2f pose, @Nullable ScreenRectangle scissorArea
                ) {
                    ScreenRectangle screenrectangle = new ScreenRectangle(x0, y0, x1 - x0, y1 - y0).transformMaxBounds(pose);
                    return scissorArea != null ? scissorArea.intersection(screenrectangle) : screenrectangle;
                }
            });
        }
    }

    private void renderBinoculars(ItemStack stack, GuiGraphicsExtractor graphics, Player player) {
        if (!stack.has(RRComponents.BINOCULAR_DATA)) return;
        if (Minecraft.getInstance().mouseHandler.isRightPressed()) {
            tic++;
            Font tr = Minecraft.getInstance().font;
            int w = graphics.guiWidth();
            int h = graphics.guiHeight();

            graphics.blit(
                RRTextures.guibinoculars,
                0,
                h,
                w,
                0,
                0,
                1,
                0,
                1
            );

            graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                RRTextures.guibinocularsoverlay,
                0,
                h,
                0,
                0,
                w,
                -h,
                1600,
                900,
                1600,
                900,
                ARGB.colorFromFloat(0.5F, 0.333F, 0.333F, 0.333F)
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
            text = Component.literal("X");
            if (!id.isAir()) text = id.getBlock().getName();
            graphics.text(tr, text, (int) ((w * 0.50) - (tr.width(text) / 2f)), (int) (h * 0.18), CommonColors.GREEN, false);
            if (!ItemBinoculars.tooFar)
                text = Component.literal("(" + tpos.getX() + ", " + tpos.getY() + ", " + tpos.getZ() + ")");
            else text = Component.empty();
            graphics.text(tr, text, (int) ((w * 0.50) - (tr.width(text) / 2f)), (int) (h * 0.13), CommonColors.GREEN, false);
            if (tic % 30 == 0) r = !r;
            //if (nbt.getInt("ty") != -1 && nbt.getInt("cooldowntime") > 0) text = Component.literal(">" + nbt.getInt("tx") + ", " + nbt.getInt("ty") + ", " + nbt.getInt("tz") + "<");
            //else if (r) text = Component.literal(">                    <");
            //graphics.drawText(tr, text, (int) ((w * 0.50) - (tr.getWidth(text) / 2f)), (int) (h * 0.85), CommonColors.RED);
            text = Component.literal("LTD RR");
            graphics.text(tr, text, (int) ((w * 0.50) - (tr.width(text) / 2f)), (int) (h * 0.80), CommonColors.WHITE, false);
            text = Component.literal(((int) ItemBinoculars.distblock) + "m");
            graphics.text(tr, text, (int) ((w * 0.637) - (tr.width(text) / 2f)), (int) (h * 0.205), CommonColors.WHITE, false);
            float yaw = (player.getYRot() + 360000) % 360;
            text = (yaw >= 315 || yaw < 45) ? Translations.BINOCULARS_SOUTH.translate() : (yaw >= 45 && yaw < 135) ? Translations.BINOCULARS_WEST.translate() : (yaw >= 135 && yaw < 225) ? Translations.BINOCULARS_NORTH.translate() : (yaw >= 225 && yaw < 315) ? Translations.BINOCULARS_EAST.translate() : Component.literal("Whut");
            graphics.text(tr, text, (int) ((w * 0.370) - (tr.width(text) / 2f)), (int) (h * 0.205), CommonColors.WHITE, false);
            if (ItemBinoculars.tooFar)
                graphics.text(tr, Translations.CONTROLLER_OUT_OF_RANGE.translate(), (int) ((w * 0.5) - (tr.width(Translations.CONTROLLER_OUT_OF_RANGE.translate()) / 2f)), (int) (h * 0.85), CommonColors.RED, false);
            else if (ItemBinoculars.tooClose)
                graphics.text(tr, Translations.NEXT_BATTLE_NO.translate(), (int) ((w * 0.5) - (tr.width(Translations.NEXT_BATTLE_NO.translate()) / 2f)), (int) (h * 0.85), CommonColors.RED, false);
                //else if (dist2 < 40)
                //{
                //	text = Component.translatable("RivalRebels.nextbattle.no");
                //	graphics.drawText(tr, text, (int) ((w * 0.5) - (tr.getWidth(text) / 2f)), (int) (h * 0.90), CommonColors.RED, false);
                //	text = Component.literal(team == RivalRebelsTeam.OMEGA ? RivalRebels.omegaobj.getLocalizedName() : RivalRebels.omegaobj.getLocalizedName());
                //	graphics.drawText(tr, text, (int) ((w * 0.5) - (tr.getWidth(text) / 2f)), (int) (h * 0.94), CommonColors.RED, false);
                //}
            else if (ItemBinoculars.ready)
                graphics.text(tr, Translations.BINOCULARS_TARGET.translate(), (int) ((w * 0.5) - (tr.width(Translations.BINOCULARS_TARGET.translate()) / 2f)), (int) (h * 0.85), CommonColors.RED, false);

            graphics.text(tr, Translations.USE_MESSAGE.translate().append(" ").append(Translations.SHIFT_CLICK.translate()).append(" B-83 x2"), (int) (w * 0.05), (int) (h * 0.95), CommonColors.RED, false);
            graphics.text(tr, Translations.PRESS_TO_SELECT_BOMB_TYPE.translate(), (int) (w * 0.60), (int) (h * 0.95), CommonColors.RED, false);

            if ((tasks > 0 || carpet > 0) && dist < 10) {
                float col = (float) (1 - dist / 10);

                graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    ItemBinoculars.c ? RRTextures.guicarpet : RRIdentifiers.ittaskb83,
                    Mth.floor(w * 0.72),
                    Mth.floor(h * 0.85 + 16),
                    0,
                    0,
                    16,
                    -16,
                    16,
                    16,
                    16,
                    16,
                    ARGB.colorFromFloat(1, col, col, col)
                );

                text = Component.literal("x" + tasks);
                graphics.text(tr, text, (int) (w * 0.76), (int) (h * 0.85), ItemBinoculars.c ? CommonColors.YELLOW : CommonColors.RED, false);
                text = Component.literal("x" + carpet);
                graphics.text(tr, text, (int) (w * 0.76), (int) (h * 0.9), ItemBinoculars.c ? CommonColors.RED : CommonColors.YELLOW, false);
                text = Translations.TACTICAL_NUKE_NAME.translate();
                if (!r)
                    graphics.text(tr, text, (int) ((w * 0.5) - (tr.width(text) / 2f)), (int) (h * 0.71), CommonColors.GREEN, false);
            } else if ((tasks > 0 || carpet > 0) && ItemBinoculars.hasLaptop) {
                text = RRBlocks.controller.get().getName().append(" ").append(Translations.CONTROLLER_OUT_OF_RANGE.translate());
                graphics.text(tr, text, (int) (w * 0.63), (int) (h * 0.87), CommonColors.YELLOW, false);
            }
        }
	}
}
