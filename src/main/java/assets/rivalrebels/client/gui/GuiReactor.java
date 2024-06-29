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
import assets.rivalrebels.client.guihelper.GuiCustomButton;
import assets.rivalrebels.client.guihelper.GuiScroll;
import assets.rivalrebels.client.guihelper.Rectangle;
import assets.rivalrebels.common.block.BlockReactive;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.machine.BlockForceFieldNode;
import assets.rivalrebels.common.container.ContainerReactor;
import assets.rivalrebels.common.item.ItemCore;
import assets.rivalrebels.common.item.ItemRod;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.noise.RivalRebelsSimplexNoise;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector2i;

import java.text.DecimalFormat;

@Environment(EnvType.CLIENT)
public class GuiReactor extends AbstractContainerScreen<ContainerReactor>
{
    private static final DecimalFormat	df					= new DecimalFormat("0.0");
    private static final RivalRebelsSimplexNoise SIMPLEX_NOISE = new RivalRebelsSimplexNoise(RandomSource.create());
    private float					frame				= 0;
	private float					resolution			= 4f;
    private Block[]					machineslist		= { RRBlocks.forcefieldnode, RRBlocks.reactive };
	private Block[]					machines			= { Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR };
	private boolean[]				onmachines			= new boolean[machines.length];
	private boolean[]				enabledmachines		= new boolean[machineslist.length];
	private boolean[]				prevenabledmachines	= new boolean[machineslist.length];
	private GuiScroll				scroll;
	private GuiCustomButton			power;
	private GuiCustomButton			eject;
	private float					melttick			= 30;

    public GuiReactor(ContainerReactor containerReactor, Inventory playerInventory, Component title) {
		super(containerReactor, playerInventory, title);
        this.imageHeight = 200;
    }

    @Override
    protected void init() {
        super.init();
		int posX = (this.width - 256) / 2;
		int posY = (this.height - 256) / 2;
		scroll = new GuiScroll(posX + 236, posY + 127, 60);
		power = new GuiCustomButton(new Rectangle(posX + 70, posY + 164, 22, 22), RRIdentifiers.guittokamak, new Vector2i(212, 0), true, button -> {
            menu.toggleOn();
        });
		eject = new GuiCustomButton(new Rectangle(posX + 164, posY + 164, 22, 22), RRIdentifiers.guittokamak, new Vector2i(234, 0), false, button -> {
            menu.ejectCore();
        });
		power.isPressed = menu.isOn();
		this.addRenderableOnly(scroll);
		this.addRenderableOnly(power);
		this.addRenderableOnly(eject);
	}

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        PoseStack matrices = context.pose();
        matrices.pushPose();
		matrices.scale(1.25f, 1f, 1f);

        menu.core.locked = menu.isOn();
        menu.fuel.locked = !menu.fuel.getItem().isEmpty() && menu.isOn();

		context.drawString(font, "ToKaMaK", 10, 8, 0x444444, false);
		matrices.popPose();
        context.drawString(font, "Teslas: " + df.format(menu.getPower() - menu.getConsumed()), 120, 8, 0xffffff, false);
        context.drawString(font, "Output/t: " + df.format(menu.getLastTickConsumed()), 140, 18, 0xffffff, false);
	}

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		context.blit(RRIdentifiers.guittokamak, width / 2 - 89, height / 2 - 103, 0, 0, 212, 208);

		float s = (float) scroll.scroll / (float) scroll.limit;
		int off = (int) Math.floor((machineslist.length - 2) * s);
		if (off < 0) off = 0;
		int X = 89 + width / 2;
		int Y = 0 + height / 2;
		for (int i = 0; i < 4; i++)
		{
			if (off + i >= machineslist.length)
			{
				machines[i] = Blocks.AIR;
				continue;
			}
			boolean current = new Rectangle(X, Y, 15, 15).isVecInside(new Vector2i(mouseX, mouseY));
			if (off + i < machineslist.length && off + i >= 0)
			{
				machines[i] = machineslist[off + i];
				onmachines[i] = enabledmachines[off + i];
				if (minecraft.mouseHandler.isLeftPressed() && current) enabledmachines[off + i] = !prevenabledmachines[off + i];
				else prevenabledmachines[off + i] = enabledmachines[off + i];
			}
			else
			{
				machines[i] = Blocks.AIR;
				onmachines[i] = false;
			}
			Y += 18;
		}

		drawDock(context);
		long time = System.currentTimeMillis();
		// 1f, 1f, 1f, 0.7f, 0f, 1f, HYDROGEN
		// 1f, 0.8f, 0f, 1f, 0f, 0f REDSTONE
        if (menu.isOn() && menu.fuel.hasItem() && menu.core.hasItem())
		{
			float radius = 10;
			if (menu.fuel.getItem().has(RRComponents.REACTOR_FUEL_LEFT)) radius += (((((ItemRod) menu.fuel.getItem().getItem()).power * ((ItemCore) menu.core.getItem().getItem()).timemult) - menu.fuel.getItem().getOrDefault(RRComponents.REACTOR_FUEL_LEFT, 0)) / (((ItemRod) menu.fuel.getItem().getItem()).power * ((ItemCore) menu.core.getItem().getItem()).timemult)) * 30;
			melttick = 30;
			float brightness = 0;
			if (menu.core.getItem().is(RRItems.core1)) brightness = -0.4f;
			if (menu.core.getItem().is(RRItems.core2)) brightness = -0.25f;
			if (menu.core.getItem().is(RRItems.core3)) brightness = -0.1f;
			if (menu.fuel.getItem().is(RRItems.nuclearelement)) drawNoiseSphere(context, 0.9f, 1f, 0.1f, 0f, 1f, 0.1f, frame, 4, (int) radius, (int) (50 - radius), resolution, 0.02f, brightness);
			if (menu.fuel.getItem().is(RRItems.hydrod)) drawNoiseSphere(context, 1f, 1f, 1f, 0.7f, 0f, 1f, frame, 4, (int) radius, (int) (50 - radius), resolution, 0.02f, brightness);
			if (menu.fuel.getItem().is(RRItems.redrod)) drawNoiseSphere(context, 1f, 0.8f, 0f, 1f, 0f, 0f, frame, 4, (int) radius, (int) (50 - radius), resolution, 0.02f, brightness);
		}
		else if (menu.isMelt() || (menu.isOn() && !menu.fuel.hasItem()))
		{
			if (melttick > 1) melttick -= 0.03f;
			drawNoiseSphere(context, 1f, 1f, 1f, 0f, 0f, 0f, frame, 4, (int) (20 + Math.sin(frame / melttick) * 20), 10, resolution, 0.02f, 0);
		}
		else if (menu.fuel.hasItem() && menu.core.hasItem())
		{
			drawInfographic(context, resolution, 15, 8, 5, 20, 0.666f, 0.25f, 0.32f);
		}
		long elapsed = System.currentTimeMillis() - time;
		if (elapsed > 30)
		{
			if (resolution == 0.25) resolution = 0.125f;
			if (resolution == 0.5) resolution = 0.25f;
			if (resolution == 1) resolution = 0.5f;
			if (resolution == 2) resolution = 1;
			if (resolution == 4) resolution = 2;
		}
		frame += 0.75f + (menu.getLastTickConsumed() / 100);
		power.isPressed = menu.isOn();
	}

    private static BlockState setState(Block block, int meta) {
        BlockState state = block.defaultBlockState();

        if (block == RRBlocks.forcefieldnode) {
            state = state.setValue(BlockForceFieldNode.META, meta);
        } else if (block == RRBlocks.reactive) {
            state = state.setValue(BlockReactive.META, meta);
        }

        return state;
    }

	protected void drawDock(GuiGraphics graphics) {
        PoseStack pose = graphics.pose();
        int X = 89 + width / 2;
		int Y = 0 + height / 2;
		for (int i = 0; i < 4; i++) {
			if (machines[i] == Blocks.AIR) return;

			Block display = machines[i];
            int meta = 2;
            BlockState state = setState(display, meta);
            BakedModel modelForState = minecraft.getBlockRenderer().getBlockModelShaper().getBlockModel(state);
            ResourceLocation toppath = ResourceLocation.withDefaultNamespace("textures/block/" + /*display.getIcon(1, meta).getIconName() +*/ ".png");
			String lsidepath = "minecraft:textures/block/" + /*display.getIcon(4, meta).getIconName() +*/ ".png";
			String rsidepath = "minecraft:textures/block/" + /*display.getIcon(2, meta).getIconName() +*/ ".png";

			Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder buffer;

			float alpha = 0.5f;
			if (onmachines[i]) alpha = 1;

			RenderSystem.setShaderTexture(0, toppath);
			buffer = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			buffer.addVertex(pose.last(), X + 1, Y + 3.5F, 0).setUv(0, 1).setColor(1F, 1F, 1F, alpha);
			buffer.addVertex(pose.last(), X + 8, Y + 7, 0).setUv(0, 0).setColor(1F, 1F, 1F, alpha);
			buffer.addVertex(pose.last(), X + 15, Y + 3.5F, 0).setUv(1, 0).setColor(1F, 1F, 1F, alpha);
			buffer.addVertex(pose.last(), X + 8, Y, 0).setUv(1, 1).setColor(1F, 1F, 1F, alpha);
            BufferUploader.drawWithShader(buffer.buildOrThrow());

			RenderSystem.setShaderTexture(0, ResourceLocation.parse(lsidepath));
            buffer = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			buffer.addVertex(pose.last(), X + 1, Y + 3.5F, 0).setUv(0, 0).setColor(0.666F, 0.666F, 0.666F, alpha);
			buffer.addVertex(pose.last(), X + 1, Y + 12.5F, 0).setUv(0, 1).setColor(0.666F, 0.666F, 0.666F, alpha);
			buffer.addVertex(pose.last(), X + 8, Y + 16, 0).setUv(1, 1).setColor(0.666F, 0.666F, 0.666F, alpha);
			buffer.addVertex(pose.last(), X + 8, Y + 7, 0).setUv(1, 0).setColor(0.666F, 0.666F, 0.666F, alpha);
            BufferUploader.drawWithShader(buffer.buildOrThrow());

			RenderSystem.setShaderTexture(0, ResourceLocation.parse(rsidepath));
            buffer = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			buffer.addVertex(pose.last(), X + 15, Y + 12.5F, 0).setUv(1, 1).setColor(0.5F, 0.5F, 0.5F, alpha);
			buffer.addVertex(pose.last(), X + 15, Y + 3.5F, 0).setUv(1, 0).setColor(0.5F, 0.5F, 0.5F, alpha);
			buffer.addVertex(pose.last(), X + 8, Y + 7, 0).setUv(0, 0).setColor(0.5F, 0.5F, 0.5F, alpha);
			buffer.addVertex(pose.last(), X + 8, Y + 16, 0).setUv(0, 1).setColor(0.5F, 0.5F, 0.5F, alpha);
            BufferUploader.drawWithShader(buffer.buildOrThrow());

			Y += 18;
		}
	}

    /**
     * Method that draws the noise sphere.
     *
     * @param red        Vein Color Red
     * @param grn        Vein Color Blue
     * @param blu        Vein Color Green
     * @param red1       Fade Color Red
     * @param grn1       Fade Color Blue
     * @param blu1       Fade Color Green
     * @param frame      Time Value
     * @param o          Octaves of Noise
     * @param radius     Radius of inner Sphere
     * @param outer      Radius of Corona
     * @param resolution Resolution in pixels of Noise Sphere
     * @param sscale     Noise Scale
     */
    protected void drawNoiseSphere(GuiGraphics context, float red, float grn, float blu, float red1, float grn1, float blu1, float frame, int o, int radius, int outer, float resolution, float sscale, float startcol) {
        Tesselator t = Tesselator.getInstance();
        BufferBuilder buffer = t.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        PoseStack matrices = context.pose();
        matrices.pushPose();
        float pointSize = (float) (minecraft.getWindow().getGuiScale() / resolution);
        radius *= resolution;
        int outerR = (int) (radius + (outer * resolution));
        int maxdist = outerR - radius;
        float hheight = (height / 2) - 45;
        float hwidth = (width / 2);
        float rradius = radius * radius;
        for (int x = 1 - outerR; x < outerR; x++) {
            int xs = x * x;
            float X = (float) x / resolution + hwidth;
            for (int y = 1 - outerR; y < outerR; y++) {
                int ys = y * y + xs;
                float Y = (float) y / resolution + hheight;
                float fdist = Mth.sqrt(ys);
                if (fdist >= radius && fdist < outerR) {
                    float v = 0;
                    float a = 1f;
                    float s = sscale;
                    for (int e = 0; e < o; e++) {
                        v += (float) ((1 + SIMPLEX_NOISE.noise(X * s, Y * s, frame * s)) / 2) * a;
                        s *= 2;
                        a /= 2;
                    }
                    v *= 1f - (fdist - radius) / maxdist;
                    drawPoint(context.pose(), buffer, pointSize, X, Y, 4, FastColor.ARGB32.colorFromFloat(lerp(red, red1, v), lerp(grn, grn1, v), lerp(blu, blu1, v), v));
                } else {
                    float Z = Mth.sqrt(rradius - ys) / resolution;
                    float v = startcol;
                    float a = 1f;
                    float s = sscale;
                    for (int e = 0; e < o; e++) {
                        v += (float) ((1 + SIMPLEX_NOISE.noise(X * s, Y * s, Z * s, frame * s)) / 2) * a;
                        s *= 2;
                        a /= 2;
                    }


                    drawPoint(context.pose(), buffer, pointSize, X, Y, 4, FastColor.ARGB32.colorFromFloat(lerp(red, red1, v), lerp(grn, grn1, v), lerp(blu, blu1, v), v));
                }
            }
        }
        MeshData data = buffer.build();
        if (data != null) { // FIXME: This is empty for now because there is no point is uploaded to buffer
            BufferUploader.drawWithShader(data);
        }
        matrices.popPose();
    }

    private static void drawPoint(PoseStack pose, BufferBuilder buffer, float pointSize, float x, float y, float z, int color) {
        // FIXME: draw point
    }

	protected float lerp(float f1, float f2, float f3) {
		return f1 * f3 + f2 * (1 - f3);
	}

	protected void drawInfographic(GuiGraphics graphics, float resolution, int radius, int sep, int width1, int width2, float outerRatio, float innerRatio1, float innerRatio2) {
        PoseStack pose = graphics.pose();
        Tesselator t = Tesselator.getInstance();
        pose.pushPose();
        float pointSize = 4 / resolution;
        BufferBuilder buffer = t.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        radius *= resolution;
		sep *= resolution;
		width1 *= resolution;
		width2 *= resolution;
		innerRatio1 *= outerRatio;
		innerRatio2 *= outerRatio;
		innerRatio2 += outerRatio;
		outerRatio *= Math.PI * 2;
		innerRatio1 *= Math.PI * 2;
		innerRatio2 *= Math.PI * 2;
		int midR1 = (radius + width1);
		int midR2 = (midR1 + sep);
		int outerR = (midR2 + width2);
		float hheight = (height / 2);
		float hwidth = (width / 2);
		for (int x = 1 - outerR; x < outerR; x++) {
			int xs = x * x;
			float X = x / resolution;
			for (int y = 1 - outerR; y < outerR; y++) {
				int ys = y * y;
				float fdist = Mth.sqrt(xs + ys);
				if (fdist >= radius && fdist < midR1) {
					float Y = y / resolution;
					float angle = (float) (Math.PI + Math.atan2(X, Y));
                    int color;
                    if (angle <= outerRatio) {
						if (angle <= innerRatio1) {
							color = FastColor.ARGB32.colorFromFloat(0.25f, 0.25f, 1, 1);
						} else {
                            color = FastColor.ARGB32.colorFromFloat(0.75f, 0.75f, 1, 1);
						}
					} else {
						if (angle <= innerRatio2) {
                            color = FastColor.ARGB32.colorFromFloat(1, 0.25f, 0.25f, 1);
						} else {
                            color = FastColor.ARGB32.colorFromFloat(1, 0.75f, 0.75f, 1);
						}
					}
                    drawPoint(pose, buffer, pointSize, hwidth + X, hheight + Y - 45, 4, color);
				} else if (fdist >= midR2 && fdist < outerR) {
					float Y = y / resolution;
					float angle = (float) (Math.PI + Math.atan2(X, Y));
                    int color;
                    if (angle <= outerRatio) {
						color = CommonColors.BLUE;
					} else {
                        color = CommonColors.RED;
					}
                    drawPoint(pose, buffer, pointSize, hwidth + X, hheight + Y - 45, 4, color);
				}
			}
		}
        BufferUploader.drawWithShader(buffer.buildOrThrow());
		pose.popPose();
	}
}
