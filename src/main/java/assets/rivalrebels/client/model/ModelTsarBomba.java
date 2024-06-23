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
// Copyrighted Rodolian Material
package assets.rivalrebels.client.model;

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ModelTsarBomba {
    private static final Material TSAR_SHELL_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettsarshell);
    private static final Material TSAR_FINS_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettsarfins);
    private static final float[]	tsarx		= { 0.5f, 0.5f, 0.875f, 1f, 1f, 0.875f, 0.5f, 0f };
	private static final float[]	tsary		= { -5f, -3.5f, -2f, -1f, 1f, 2f, 2.75f, 3f };
	private static final float[]	tsart		= { 1f, 0.8125f, 0.625f, 0.5f, 0.25f, 0.125f, 0.03125f, 0f };
	private static final int		segments	= 20;
	private static final float	deg			= (float) Math.PI * 2f / segments;
	private static final float	sin			= (float) Math.sin(deg);
	private static final float	cos			= (float) Math.cos(deg);
	private static final float	add			= 360 / segments;

	public static void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay)
	{
		matrices.pushPose();
		matrices.scale(RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale());
		matrices.pushPose();

        VertexConsumer tsarShellTextureVertexConsumer = TSAR_SHELL_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
        for (float i = 0; i < segments; i++)
		{
			matrices.pushPose();
			matrices.mulPose(new Quaternionf(add * i, 0, 1, 0));
			for (int f = 1; f < tsarx.length; f++)
			{
				TextureVertice t1 = new TextureVertice((1f / segments) * i, tsart[f]);
				TextureVertice t2 = new TextureVertice((1f / segments) * i, tsart[f - 1]);
				TextureVertice t3 = new TextureVertice((1f / segments) * (i + 1), tsart[f - 1]);
				TextureVertice t4 = new TextureVertice((1f / segments) * (i + 1), tsart[f]);
				RenderHelper.addFace(tsarShellTextureVertexConsumer, new Vector3f(0f, tsary[f], tsarx[f]),
						new Vector3f(0f, tsary[f - 1], tsarx[f - 1]),
						new Vector3f(tsarx[f - 1] * sin, tsary[f - 1], tsarx[f - 1] * cos),
						new Vector3f(tsarx[f] * sin, tsary[f], tsarx[f] * cos), t1, t2, t3, t4, light, overlay);
			}
			matrices.popPose();
		}
		matrices.popPose();

        VertexConsumer tsarFinsTextureVertexConsumer = TSAR_FINS_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);

        matrices.pushPose();

		TextureVertice t5 = new TextureVertice(70f / 256f, 0f);
		TextureVertice t6 = new TextureVertice(134f / 256f, 0f);
		TextureVertice t7 = new TextureVertice(134f / 256f, 64f / 256f);
		TextureVertice t8 = new TextureVertice(70 / 256f, 64f / 256f);

		RenderHelper.addFace(tsarFinsTextureVertexConsumer, new Vector3f(0.5f, -5f, 0.5f),
				new Vector3f(-0.5f, -5f, 0.5f),
				new Vector3f(-0.5f, -5f, -0.5f),
				new Vector3f(0.5f, -5f, -0.5f), t5, t6, t7, t8, light, overlay);

		matrices.popPose();

		matrices.pushPose();

		TextureVertice t1 = new TextureVertice(0f, 0f);
		TextureVertice t2 = new TextureVertice(70f / 256f, 0f);
		TextureVertice t3 = new TextureVertice(70f / 256f, 96f / 256f);
		TextureVertice t4 = new TextureVertice(0, 96f / 256f);

		RenderHelper.addFace(tsarFinsTextureVertexConsumer, new Vector3f(0f, -5f, -1.4f),
				new Vector3f(0f, -5f, -0.5f),
				new Vector3f(0f, -3.5f, -0.5f),
				new Vector3f(0f, -3.5f, -1.4f), t1, t2, t3, t4, light, overlay);

		matrices.mulPose(new Quaternionf(120, 0, 1, 0));
		RenderHelper.addFace(tsarFinsTextureVertexConsumer, new Vector3f(0f, -5f, -1.4f),
				new Vector3f(0f, -5f, -0.5f),
				new Vector3f(0f, -3.5f, -0.5f),
				new Vector3f(0f, -3.5f, -1.4f), t1, t2, t3, t4, light, overlay);

		matrices.mulPose(new Quaternionf(120, 0, 1, 0));
		RenderHelper.addFace(tsarFinsTextureVertexConsumer, new Vector3f(0f, -5f, -1.4f),
				new Vector3f(0f, -5f, -0.5f),
				new Vector3f(0f, -3.5f, -0.5f),
				new Vector3f(0f, -3.5f, -1.4f), t1, t2, t3, t4, light, overlay);

		matrices.popPose();
		matrices.popPose();
	}
}
