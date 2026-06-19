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
package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.client.model.ModelBlastSphere;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.entity.RhodesType;
import io.github.kadir1243.rivalrebels.common.entity.RhodesTypes;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class RenderRhodes extends EntityRenderer<EntityRhodes, RenderRhodes.State> {
    private static final ResourceLocation flame = RRIdentifiers.create("textures/entity/flame.png");
    private final QuadCollection headModel;
    private final QuadCollection b2jetForRhodesModel;
    private final QuadCollection torsoModel;
    private final QuadCollection flagModel;
    private final QuadCollection upperArmModel;
    private final QuadCollection lowerArmModel;
    private final QuadCollection flameThrowerModel;
    private final QuadCollection rocketLauncherModel;
    private final QuadCollection thighModel;
    private final QuadCollection shinModel;
    private final QuadCollection flameModel;
    private final QuadCollection boosterModel;
    private final QuadCollection laserModel;
    private final QuadCollection b2ForSpiritModel;
    private final QuadCollection ffheadModel;
    private final QuadCollection ffTorsoModel;
    private final QuadCollection ffUpperArmModel;
    private final QuadCollection ffLowerArmModel;
    private final QuadCollection ffThighModel;
    private final QuadCollection ffShinModel;
    private final QuadCollection shuttleModel;

    public RenderRhodes(EntityRendererProvider.Context context) {
        super(context);
        ModelManager modelManager = context.getModelManager();
        headModel = modelManager.getStandaloneModel(ObjModels.HEAD_MODEL);
        b2jetForRhodesModel = modelManager.getStandaloneModel(ObjModels.B2_JET_FOR_RHODES_MODEL);
        torsoModel = modelManager.getStandaloneModel(ObjModels.TORSO_MODEL);
        flagModel = modelManager.getStandaloneModel(ObjModels.FLAG_MODEL);
        upperArmModel = modelManager.getStandaloneModel(ObjModels.UPPER_ARM_MODEL);
        lowerArmModel = modelManager.getStandaloneModel(ObjModels.LOWER_ARM_MODEL);
        flameThrowerModel = modelManager.getStandaloneModel(ObjModels.RHODES_FLAMETHROWER_MODEL);
        rocketLauncherModel = modelManager.getStandaloneModel(ObjModels.RHODES_ROCKET_LAUNCHER_MODEL);
        thighModel = modelManager.getStandaloneModel(ObjModels.THIGH_MODEL);
        shinModel = modelManager.getStandaloneModel(ObjModels.SHIN_MODEL);
        flameModel = modelManager.getStandaloneModel(ObjModels.RHODES_FLAME_MODEL);
        boosterModel = modelManager.getStandaloneModel(ObjModels.BOOSTER_MODEL);
        laserModel = modelManager.getStandaloneModel(ObjModels.RHODES_LASER_MODEL);
        b2ForSpiritModel = modelManager.getStandaloneModel(ObjModels.B2_FOR_SPIRIT_MODEL);
        ffheadModel = modelManager.getStandaloneModel(ObjModels.FF_HEAD_MODEL);
        ffTorsoModel = modelManager.getStandaloneModel(ObjModels.FF_TORSO_MODEL);
        ffUpperArmModel = modelManager.getStandaloneModel(ObjModels.FF_UPPER_ARM_MODEL);
        ffLowerArmModel = modelManager.getStandaloneModel(ObjModels.FF_LOWER_ARM_MODEL);
        ffThighModel = modelManager.getStandaloneModel(ObjModels.FF_THIGH_MODEL);
        ffShinModel = modelManager.getStandaloneModel(ObjModels.FF_SHIN_MODEL);
        shuttleModel = modelManager.getStandaloneModel(ObjModels.SHUTTLE_MODEL);
    }

    @Override
    public boolean shouldRender(EntityRhodes livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityRhodes entity, BlockPos pos) {
        return super.getBlockLightLevel(entity, pos.atY(255));
    }

    @Override
    protected int getSkyLightLevel(EntityRhodes entity, BlockPos pos) {
        return super.getSkyLightLevel(entity, pos.atY(255));
    }

    @Override
    protected void renderNameTag(State renderState, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        int color = 0;
        if (renderState.rider != null) {
            RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(renderState.rider.getGameProfile());
            color = switch (rrp.rrteam) {
                case OMEGA -> 0x44FF44;
                case SIGMA -> 0x4444FF;
                case NONE -> CommonColors.WHITE;
            };
        }
        poseStack.pushPose();
        poseStack.translate(0, 16, 0);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.scale(0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = poseStack.last().pose();
        float f = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int)(f * 255.0F) << 24;
        Font font = this.getFont();
        float x = (float)(-font.width(displayName) / 2);
        font.drawInBatch(
            displayName, x, 0, color, false, matrix4f, bufferSource, Font.DisplayMode.SEE_THROUGH, j, packedLight
        );
        font.drawInBatch(displayName, x, 0, color, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, packedLight);

        poseStack.popPose();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (renderState.health > 0) {
			poseStack.pushPose();
			poseStack.scale(renderState.scale, renderState.scale, renderState.scale);

            {
                Component name = renderState.displayName;
                if (renderState.rider != null) {
                    name = name.copy().append(" - ").append(renderState.rider.getDisplayName());
                }
                this.renderNameTag(renderState, name, poseStack, bufferSource, packedLight);
            }

            RhodesType rhodesType = renderState.variant.value();
            if (rhodesType == RhodesTypes.Space) {
                {
                    poseStack.pushPose();
                    poseStack.mulPose(Axis.YP.rotationDegrees(renderState.bodyyaw));
                    poseStack.translate(0, 10f, 0);
                    ObjModels.render(boosterModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etbooster)), poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                    {
                        poseStack.pushPose();
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                        poseStack.translate(0, 4, -2);
                        poseStack.scale(2.2f, 2.2f, 2.2f);
                        if (renderState.b2Energy > 0) {
                            ObjModels.render(shuttleModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etb2spirit)), poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                        }
                        poseStack.popPose();
                    }
                    poseStack.popPose();
                }
			} else {
                VertexConsumer noiseBuffer = bufferSource.getBuffer(RenderTypes.CELLULAR_NOISE);
                VertexConsumer textureBuffer = bufferSource.getBuffer(RenderType.entitySolid(rhodesType.getTexture()));
                poseStack.mulPose(Axis.YP.rotationDegrees(renderState.bodyyaw));

				float leftlegheight = 7.26756f - 15
						+ (Mth.cos((renderState.leftthighpitch+11.99684962f)*Mth.DEG_TO_RAD) * 7.331691240f)
						+ (Mth.cos((renderState.leftthighpitch+renderState.leftshinpitch-12.2153067f)*Mth.DEG_TO_RAD) * 8.521366426f);
				float rightlegheight = 7.26756f - 15
						+ (Mth.cos((renderState.rightthighpitch+11.99684962f)*Mth.DEG_TO_RAD) * 7.331691240f)
						+ (Mth.cos((renderState.rightthighpitch+renderState.rightshinpitch-12.2153067f)*Mth.DEG_TO_RAD) * 8.521366426f);

                {//TORSO
                    poseStack.pushPose();
                    int colorOfRhodes = rhodesType.getColor();
                    poseStack.translate(0, Math.max(leftlegheight, rightlegheight), 0);

                    poseStack.pushPose();
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                    poseStack.translate(0, 4, -2);
                    if (renderState.b2Energy > 0) {
                        poseStack.pushPose();
                        poseStack.scale(2.5F, 2.5F, 2.5F);
                        ObjModels.render(b2ForSpiritModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etb2spirit)), poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                        poseStack.popPose();
                    }
                    if (renderState.jet && renderState.b2Energy > 0) {
                        poseStack.pushPose();
                        poseStack.scale(2.5F, 2.5F, 2.5F);
                        ObjModels.render(b2jetForRhodesModel, bufferSource.getBuffer(RenderType.entityTranslucent(flame)), poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                        poseStack.popPose();
                    }
                    poseStack.popPose();

                    ObjModels.render(torsoModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);


                    {//RIGHT UPPERARM
                        poseStack.pushPose();
                        poseStack.translate(-6.4f, 0, 0);
                        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rightarmyaw));
                        poseStack.scale(-1, 1, 1);
                        ObjModels.render(upperArmModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        //RIGHT LOWERARM
                        {
                            poseStack.pushPose();
                            poseStack.translate(0, -1.5f, 0);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightarmpitch));
                            ObjModels.render(lowerArmModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            poseStack.scale(-1, 1, 1);
                            ObjModels.render(flameThrowerModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            poseStack.popPose();
                        }

                        poseStack.popPose();
                    }

                    {//LEFT UPPERARM
                        poseStack.pushPose();
                        poseStack.translate(6.4f, 0, 0);
                        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.leftarmyaw));
                        ObjModels.render(upperArmModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        {//LEFT LOWERARM
                            poseStack.pushPose();
                            poseStack.translate(0, -1.5f, 0);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftarmpitch));
                            ObjModels.render(lowerArmModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            ObjModels.render(rocketLauncherModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            poseStack.popPose();
                        }

                        poseStack.popPose();
                    }

                    {//RIGHT THIGH
                        poseStack.pushPose();
                        poseStack.translate(0, -7.26756f, -0.27904f);
                        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightthighpitch));
                        poseStack.scale(-1, 1, 1);
                        ObjModels.render(thighModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        {//RIGHT SHIN
                            poseStack.pushPose();
                            poseStack.translate(0, -7.17156f, -1.52395f);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightshinpitch));
                            ObjModels.render(shinModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            if (renderState.isBurning) {
                                ObjModels.render(flameModel, bufferSource.getBuffer(RenderType.entityCutout(flame)), poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            }
                            poseStack.popPose();
                        }

                        poseStack.popPose();
                    }

                    {//LEFT THIGH
                        poseStack.pushPose();
                        poseStack.translate(0, -7.26756f, -0.27904f);
                        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftthighpitch));
                        ObjModels.render(thighModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        //LEFT SHIN
                        {
                            poseStack.pushPose();
                            poseStack.translate(0, -7.17156f, -1.52395f);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftshinpitch));
                            ObjModels.render(shinModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            if (renderState.isBurning) {
                                ObjModels.render(flameModel, bufferSource.getBuffer(RenderType.entityCutout(flame)), poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            }
                            poseStack.popPose();
                        }

                        poseStack.popPose();
                    }

                    {//HEAD
                        poseStack.pushPose();
                        poseStack.translate(0, 5.23244f, 0);
                        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.headpitch));
                        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.viewYRot));
                        ObjModels.render(headModel, textureBuffer, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        {
                            int color = ARGB.colorFromFloat(0.5F, 1, 0, 0);
                            if (renderState.topLaserEnabled) {
                                ObjModels.render(laserModel, bufferSource.getBuffer(RenderTypes.LASER_RENDER_TYPE), poseStack, color, packedLight, OverlayTexture.NO_OVERLAY);
                            } else if (renderState.bottomLaserEnabled) {
                                poseStack.scale(1, -1, 1);
                                //GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
                                ObjModels.render(laserModel, bufferSource.getBuffer(RenderTypes.LASER_RENDER_TYPE), poseStack, color, packedLight, OverlayTexture.NO_OVERLAY);
                                //GlStateManager.cullFace(GlStateManager.CullFace.BACK);
                            }
                        }
                        poseStack.popPose();
                    }

                    poseStack.popPose();
                }
                {//TORSO
                    poseStack.pushPose();
                    poseStack.translate(0, Math.max(leftlegheight, rightlegheight), 0);
                    if (!renderState.flagTextureLocation.isBlank()) {
                        try {
                            ObjModels.render(flagModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.create(renderState.flagTextureLocation + ".png"))), poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                        } catch (Exception ignored) {
                        }
                    }
                    if (renderState.forceFieldEnabled) {
                        ObjModels.render(ffTorsoModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                        {//RIGHT UPPERARM
                            poseStack.pushPose();
                            poseStack.translate(-6.4f, 0, 0);
                            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rightarmyaw));
                            poseStack.scale(-1, 1, 1);
                            ObjModels.render(ffUpperArmModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            {//RIGHT LOWERARM
                                poseStack.pushPose();
                                poseStack.translate(0, -1.5f, 0);
                                poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightarmpitch));
                                ObjModels.render(ffLowerArmModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                                poseStack.popPose();
                            }
                            poseStack.popPose();
                        }
                        {//LEFT UPPERARM
                            poseStack.pushPose();
                            poseStack.translate(6.4f, 0, 0);
                            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.leftarmyaw));
                            ObjModels.render(ffUpperArmModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            {//LEFT LOWERARM
                                poseStack.pushPose();
                                poseStack.translate(0, -1.5f, 0);
                                poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftarmpitch));
                                ObjModels.render(ffLowerArmModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                                poseStack.popPose();
                            }
                            poseStack.popPose();
                        }
                        {//RIGHT THIGH
                            poseStack.pushPose();
                            poseStack.translate(0, -7.26756f, -0.27904f);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightthighpitch));
                            poseStack.scale(-1, 1, 1);
                            ObjModels.render(ffThighModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            {//RIGHT SHIN
                                poseStack.pushPose();
                                poseStack.translate(0, -7.17156f, -1.52395f);
                                poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightshinpitch));
                                ObjModels.render(ffShinModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                                poseStack.popPose();
                            }
                            poseStack.popPose();
                        }
                        {//LEFT THIGH
                            poseStack.pushPose();
                            poseStack.translate(0, -7.26756f, -0.27904f);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftthighpitch));
                            ObjModels.render(ffThighModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            {//LEFT SHIN
                                poseStack.pushPose();
                                poseStack.translate(0, -7.17156f, -1.52395f);
                                poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftshinpitch));
                                ObjModels.render(ffShinModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                                poseStack.popPose();
                            }
                            poseStack.popPose();
                        }
                        {//HEAD
                            poseStack.pushPose();
                            poseStack.translate(0, 5.23244f, 0);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.headpitch));
                            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.viewYRot));
                            ObjModels.render(ffheadModel, noiseBuffer, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            poseStack.popPose();
                        }
                    }
                    poseStack.popPose();
                }
			}
	    	poseStack.popPose();
		}
		if (renderState.health < 1) {
            VertexConsumer lightning = bufferSource.getBuffer(RenderTypes.RHODES_LIGHTNING);
            poseStack.pushPose();

			float elev = Mth.sin((renderState.health-renderState.partialTick)*-(Mth.PI / 100))*15;

            poseStack.pushPose();
			poseStack.mulPose(Axis.YP.rotationDegrees(elev * 2));
			poseStack.mulPose(Axis.XP.rotationDegrees(elev * 3));
			ModelBlastSphere.renderModel(poseStack, lightning, elev, ARGB.colorFromFloat(1F, 1F, 0.25f, 0));
			poseStack.popPose();

            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(elev * -2));
            poseStack.mulPose(Axis.ZP.rotationDegrees(elev * 4));
			ModelBlastSphere.renderModel(poseStack, lightning, elev - 0.2f, ARGB.colorFromFloat(1F, 1, 0.5f, 0));
			poseStack.popPose();

            poseStack.pushPose();
			poseStack.mulPose(Axis.XP.rotationDegrees(elev * -3));
			poseStack.mulPose(Axis.ZP.rotationDegrees(elev * 2));
			ModelBlastSphere.renderModel(poseStack, lightning, elev - 0.4f, CommonColors.RED);
			poseStack.popPose();

            poseStack.pushPose();
			poseStack.mulPose(Axis.YP.rotationDegrees(elev * -1));
			poseStack.mulPose(Axis.ZP.rotationDegrees(elev * 3));
			ModelBlastSphere.renderModel(poseStack, lightning, elev - 0.6f, CommonColors.YELLOW);
			poseStack.popPose();

            poseStack.popPose();
		}
	}

    @Override
    public void extractRenderState(EntityRhodes p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        float ptt = Math.min((p_entity.ticksSinceLastPacket + partialTick)/5f, 1);
        if (p_entity.tickCount<10) ptt = 1;
        reusedState.ptt = ptt;
        reusedState.rider = p_entity.rider;
        reusedState.ticksSinceLastPacket = p_entity.ticksSinceLastPacket;
        reusedState.health = p_entity.getHealth();
        reusedState.scale = p_entity.getScale();
        reusedState.displayName = p_entity.getDisplayName();
        reusedState.variant = p_entity.getVariant();
        reusedState.flagTextureLocation = p_entity.getFlagTextureLocation();
        reusedState.leftthighpitch = p_entity.getleftthighpitch(ptt);
        reusedState.rightthighpitch = p_entity.getrightthighpitch(ptt);
        reusedState.leftshinpitch = p_entity.getleftshinpitch(ptt);
        reusedState.rightshinpitch = p_entity.getrightshinpitch(ptt);
        reusedState.rightarmpitch = p_entity.getrightarmpitch(ptt);
        reusedState.rightarmyaw = p_entity.getrightarmyaw(ptt);
        reusedState.leftarmpitch = p_entity.getleftarmpitch(ptt);
        reusedState.leftarmyaw = p_entity.getleftarmyaw(ptt);
        reusedState.headpitch = p_entity.getheadpitch(ptt);
        reusedState.bodyyaw = p_entity.getbodyyaw(ptt);
        reusedState.viewYRot = p_entity.getViewYRot(ptt);
        reusedState.b2Energy = p_entity.getB2Energy();
        reusedState.isBurning = p_entity.isFire();
        reusedState.topLaserEnabled = p_entity.isTopLaserEnabled();
        reusedState.bottomLaserEnabled = p_entity.isBottomLaserEnabled();
        reusedState.forceFieldEnabled = p_entity.isForceFieldEnabled();
        reusedState.jet = p_entity.jet;
    }

    public static class State extends EntityRenderState {
        public Player rider;
        public float health;
        public float scale;
        public int ticksSinceLastPacket;
        public Component displayName;
        public Holder<RhodesType> variant;
        public float ptt;
        public float leftthighpitch;
        public float rightthighpitch;
        public float leftshinpitch;
        public float rightshinpitch;
        public float rightarmpitch;
        public float rightarmyaw;
        public float leftarmpitch;
        public float leftarmyaw;
        public float headpitch;
        public float bodyyaw;
        public float viewYRot;
        public String flagTextureLocation;
        public int b2Energy;
        public boolean isBurning;
        public boolean jet;
        public boolean forceFieldEnabled;
        public boolean bottomLaserEnabled;
        public boolean topLaserEnabled;
    }
}
