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
import assets.rivalrebels.client.guihelper.Vector;
import assets.rivalrebels.common.block.BlockReactive;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.machine.BlockForceFieldNode;
import assets.rivalrebels.common.container.ContainerReactor;
import assets.rivalrebels.common.item.ItemCore;
import assets.rivalrebels.common.item.ItemRod;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.noise.RivalRebelsSimplexNoise;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;

@Environment(EnvType.CLIENT)
public class GuiReactor extends HandledScreen<ContainerReactor>
{
    private static final DecimalFormat	df					= new DecimalFormat("0.0");
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
    private static final Random RANDOM = Random.create();

	public GuiReactor(ContainerReactor containerReactor, PlayerInventory playerInventory, Text title) {
		super(containerReactor, playerInventory, title);
        this.backgroundHeight = 200;
		RivalRebelsSimplexNoise.refresh(RANDOM);
	}

    @Override
    protected void init() {
        super.init();
		int posX = (this.width - 256) / 2;
		int posY = (this.height - 256) / 2;
		scroll = new GuiScroll(posX + 236, posY + 127, 60);
		power = new GuiCustomButton(new Rectangle(posX + 70, posY + 164, 22, 22), RRIdentifiers.guittokamak, new Vector(212, 0), true);
		eject = new GuiCustomButton(new Rectangle(posX + 164, posY + 164, 22, 22), RRIdentifiers.guittokamak, new Vector(234, 0), false);
		power.isPressed = handler.isOn();
		this.addDrawable(scroll);
		this.addDrawable(power);
		this.addDrawable(eject);
	}

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
		matrices.scale(1.25f, 1f, 1f);

        handler.core.locked = handler.isOn();
        handler.fuel.locked = !handler.fuel.getStack().isEmpty() && handler.isOn();

		context.drawText(textRenderer, "ToKaMaK", 10, 8, 0x444444, false);
		matrices.pop();
        context.drawText(textRenderer, "Teslas: " + df.format(handler.getPower() - handler.getConsumed()), 120, 8, 0xffffff, false);
        context.drawText(textRenderer, "Output/t: " + df.format(handler.getLastTickConsumed()), 140, 18, 0xffffff, false);

		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - backgroundWidth) / 2;
		int posy = (height - backgroundHeight) / 2;
		int coordx = posx + 53;
		int coordy = posy + 191;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			context.fillGradient(mousex, mousey, mousex + textRenderer.getWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
            context.drawText(textRenderer, "rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF, false);
			if (!buttondown && client.mouse.wasLeftButtonClicked())
			{
                Util.getOperatingSystem().open("http://rivalrebels.com");
			}
		}
		buttondown = client.mouse.wasLeftButtonClicked();
	}

	boolean	buttondown;
	boolean	buttondown2;

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		context.drawTexture(RRIdentifiers.guittokamak, width / 2 - 89, height / 2 - 103, 0, 0, 212, 208);

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
			boolean current = new Rectangle(X, Y, 15, 15).isVecInside(new Vector(mouseX, mouseY));
			if (off + i < machineslist.length && off + i >= 0)
			{
				machines[i] = machineslist[off + i];
				onmachines[i] = enabledmachines[off + i];
				if (client.mouse.wasLeftButtonClicked() && current) enabledmachines[off + i] = !prevenabledmachines[off + i];
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
        if (handler.isOn() && handler.fuel.hasStack() && handler.core.hasStack())
		{
			float radius = 10;
			if (handler.fuel.getStack().hasNbt()) radius += (((((ItemRod) handler.fuel.getStack().getItem()).power * ((ItemCore) handler.core.getStack().getItem()).timemult) - handler.fuel.getStack().getNbt().getInt("fuelLeft")) / (((ItemRod) handler.fuel.getStack().getItem()).power * ((ItemCore) handler.core.getStack().getItem()).timemult)) * 30;
			melttick = 30;
			float brightness = 0;
			if (handler.core.getStack().isOf(RRItems.core1)) brightness = -0.4f;
			if (handler.core.getStack().isOf(RRItems.core2)) brightness = -0.25f;
			if (handler.core.getStack().isOf(RRItems.core3)) brightness = -0.1f;
			if (handler.fuel.getStack().isOf(RRItems.nuclearelement)) drawNoiseSphere(context, 0.9f, 1f, 0.1f, 0f, 1f, 0.1f, frame, 4, (int) radius, (int) (50 - radius), resolution, 0.02f, brightness);
			if (handler.fuel.getStack().isOf(RRItems.hydrod)) drawNoiseSphere(context, 1f, 1f, 1f, 0.7f, 0f, 1f, frame, 4, (int) radius, (int) (50 - radius), resolution, 0.02f, brightness);
			if (handler.fuel.getStack().isOf(RRItems.redrod)) drawNoiseSphere(context, 1f, 0.8f, 0f, 1f, 0f, 0f, frame, 4, (int) radius, (int) (50 - radius), resolution, 0.02f, brightness);
		}
		else if (handler.isMelt() || (handler.isOn() && !handler.fuel.hasStack()))
		{
			if (melttick > 1) melttick -= 0.03f;
			drawNoiseSphere(context, 1f, 1f, 1f, 0f, 0f, 0f, frame, 4, (int) (20 + Math.sin(frame / melttick) * 20), 10, resolution, 0.02f, 0);
		}
		else if (handler.fuel.hasStack() && handler.core.hasStack())
		{
			// drawInfographic(resolution, 15, 8, 5, 20, 0.666f, 0.25f, 0.32f);
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
		frame += 0.75f + (handler.getLastTickConsumed() / 100);
		if (!buttondown2 && client.mouse.wasLeftButtonClicked())
		{
			if (power.mouseClicked(mouseX, mouseY, 0))
			{
                handler.toggleOn();
			}
			if (eject.mouseClicked(mouseX, mouseY, 0))
			{
				handler.ejectCore();
			}
		}
		power.isPressed = handler.isOn();
		buttondown2 = client.mouse.wasLeftButtonClicked();
	}

    private static BlockState setState(Block block, int meta) {
        BlockState state = block.getDefaultState();

        if (block == RRBlocks.forcefieldnode) {
            state = state.with(BlockForceFieldNode.META, meta);
        } else if (block == RRBlocks.reactive) {
            state = state.with(BlockReactive.META, meta);
        }

        return state;
    }

	protected void drawDock(DrawContext context)
	{
		int X = 89 + width / 2;
		int Y = 0 + height / 2;
		for (int i = 0; i < 4; i++)
		{
			if (machines[i] == Blocks.AIR) return;

			Block display = machines[i];
            int meta = 2;
            BlockState state = setState(display, meta);
            BakedModel modelForState = MinecraftClient.getInstance().getBlockRenderManager().getModels().getModel(state);
            Identifier toppath = new Identifier("textures/block/" + /*display.getIcon(1, meta).getIconName() +*/ ".png");
			String lsidepath = "minecraft:textures/block/" + /*display.getIcon(4, meta).getIconName() +*/ ".png";
			String rsidepath = "minecraft:textures/block/" + /*display.getIcon(2, meta).getIconName() +*/ ".png";

			Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();

			float alpha = 0.5f;
			if (onmachines[i]) alpha = 1;
			context.setShaderColor(1F, 1F, 1F, alpha);
			RenderSystem.setShaderTexture(0, toppath);
			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			buffer.vertex(X + 1, Y + 3.5, getZOffset()).texture(0, 1).next();
			buffer.vertex(X + 8, Y + 7, getZOffset()).texture(0, 0).next();
			buffer.vertex(X + 15, Y + 3.5, getZOffset()).texture(1, 0).next();
			buffer.vertex(X + 8, Y, getZOffset()).texture(1, 1).next();
			tessellator.draw();
            context.setShaderColor(0.666F, 0.666F, 0.666F, alpha);
			RenderSystem.setShaderTexture(0, new Identifier(lsidepath));
			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			buffer.vertex(X + 1, Y + 3.5, getZOffset()).texture(0, 0).next();
			buffer.vertex(X + 1, Y + 12.5, getZOffset()).texture(0, 1).next();
			buffer.vertex(X + 8, Y + 16, getZOffset()).texture(1, 1).next();
			buffer.vertex(X + 8, Y + 7, getZOffset()).texture(1, 0).next();
			tessellator.draw();
            context.setShaderColor(0.5F, 0.5F, 0.5F, alpha);
			RenderSystem.setShaderTexture(0, new Identifier(rsidepath));
			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			buffer.vertex(X + 15, Y + 12.5, getZOffset()).texture(1, 1).next();
			buffer.vertex(X + 15, Y + 3.5, getZOffset()).texture(1, 0).next();
			buffer.vertex(X + 8, Y + 7, getZOffset()).texture(0, 0).next();
			buffer.vertex(X + 8, Y + 16, getZOffset()).texture(0, 1).next();
			tessellator.draw();

			Y += 18;
		}
	}

    private int getZOffset() {
        return 0;//TODO: Find replacement
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
    protected void drawNoiseSphere(DrawContext context, float red, float grn, float blu, float red1, float grn1, float blu1, float frame, int o, int radius, int outer, float resolution, float sscale, float startcol) {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        GL11.glPointSize((float) (client.getWindow().getScaleFactor() / resolution));
        //FIXME: buffer.begin(GL11.GL_POINTS, VertexFormats.POSITION_COLOR);
        radius *= resolution;
        int outerR = (int) (radius + (outer * resolution));
        int maxdist = outerR - radius;
        float hheight = (height / 2) - 45;
        float hwidth = (width / 2);
        float rradius = radius * radius;
        for (int x = 1 - outerR; x < outerR; x++) {
            int xs = x * x;
            double X = (double) x / (double) resolution + hwidth;
            for (int y = 1 - outerR; y < outerR; y++) {
                int ys = y * y + xs;
                double Y = (double) y / (double) resolution + hheight;
                double fdist = Math.sqrt(ys);
                if (fdist >= radius && fdist < outerR) {
                    float v = 0;
                    float a = 1f;
                    float s = sscale;
                    for (int e = 0; e < o; e++) {
                        v += (float) ((1 + RivalRebelsSimplexNoise.noise(X * s, Y * s, frame * s)) / 2) * a;
                        s *= 2;
                        a /= 2;
                    }
                    v *= 1f - (fdist - radius) / maxdist;
                    buffer.vertex(X, Y, 4).color(lerp(red, red1, v), lerp(grn, grn1, v), lerp(blu, blu1, v), v).next();
                } else {
                    double Z = Math.sqrt(rradius - ys) / resolution;
                    float v = startcol;
                    float a = 1f;
                    float s = sscale;
                    for (int e = 0; e < o; e++) {
                        v += (float) ((1 + RivalRebelsSimplexNoise.noise(X * s, Y * s, Z * s, frame * s)) / 2) * a;
                        s *= 2;
                        a /= 2;
                    }


                    buffer.vertex(X, Y, 4).color(lerp(red, red1, v), lerp(grn, grn1, v), lerp(blu, blu1, v), v).next();
                }
            }
        }
        t.draw();
        matrices.pop();
    }

	protected float lerp(float f1, float f2, float f3)
	{
		return f1 * f3 + f2 * (1 - f3);
	}

	/*protected void drawInfographic(MatrixStack matrices, float resolution, int radius, int sep, int width1, int width2, float outerRatio, float innerRatio1, float innerRatio2)
	{
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        matrices.push();
		GL11.glPointSize(4 / resolution);
		buffer.begin(GL11.GL_POINTS, VertexFormats.POSITION_COLOR);
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
		for (int x = 1 - outerR; x < outerR; x++)
		{
			int xs = x * x;
			double X = (double) x / (double) resolution;
			for (int y = 1 - outerR; y < outerR; y++)
			{
				int ys = y * y;
				double fdist = Math.sqrt(xs + ys);
				if (fdist >= radius && fdist < midR1)
				{
					double Y = (double) y / (double) resolution;
					double angle = Math.PI + Math.atan2(X, Y);
                    buffer.vertex(hwidth + X, hheight + Y - 45, 4);
					if (angle <= outerRatio)
					{
						if (angle <= innerRatio1)
						{
							buffer.color(0.25f, 0.25f, 1, 1);
						}
						else
						{
							buffer.color(0.75f, 0.75f, 1, 1);
						}
					}
					else
					{
						if (angle <= innerRatio2)
						{
							buffer.color(1, 0.25f, 0.25f, 1);
						}
						else
						{
							buffer.color(1, 0.75f, 0.75f, 1);
						}
					}
					buffer.next();
				}
				else if (fdist >= midR2 && fdist < outerR)
				{
					double Y = (double) y / (double) resolution;
					double angle = Math.PI + Math.atan2(X, Y);
                    buffer.vertex(hwidth + X, hheight + Y - 45, 4);
                    if (angle <= outerRatio)
					{
						buffer.color(0, 0, 1, 1);
					}
					else
					{
						buffer.color(1, 0, 0, 1);
					}
					buffer.next();
				}
			}
		}
		t.draw();
		matrices.pop();
	}*/
}
