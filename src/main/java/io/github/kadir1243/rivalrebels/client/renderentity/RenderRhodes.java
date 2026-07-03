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
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.entity.RhodesType;
import io.github.kadir1243.rivalrebels.common.entity.RhodesTypes;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

@OnlyIn(Dist.CLIENT)
public class RenderRhodes extends EntityRenderer<EntityRhodes, RenderRhodes.State> {
    private static final Identifier flame = RRIdentifiers.create("textures/entity/flame.png");
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
    private final QuadCollection blastSphereModel;

    public RenderRhodes(EntityRendererProvider.Context context) {
        super(context);
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
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
        blastSphereModel = modelManager.getStandaloneModel(ObjModels.BLAST_SPHERE_MODEL);
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
    protected void submitNameDisplay(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        int color = 0;
        if (renderState.rider != null) {
            RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(renderState.rider.getGameProfile());
            color = switch (rrp.rrteam) {
                case OMEGA -> 0xFF44FF44;
                case SIGMA -> 0xFF4444FF;
                case NONE -> CommonColors.WHITE;
            };
        }
        poseStack.pushPose();
        poseStack.translate(0, 16, 0);
        poseStack.mulPose(cameraRenderState.orientation);
        poseStack.scale(0.025F, -0.025F, 0.025F);
        float f = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int)(f * 255.0F) << 24;
        Font font = this.getFont();
        float x = (float)(-font.width(renderState.displayName) / 2);
        nodeCollector.submitText(poseStack, x, 0, renderState.displayName.getVisualOrderText(), false, Font.DisplayMode.SEE_THROUGH, renderState.lightCoords, color, j, CommonColors.WHITE);
        nodeCollector.submitText(poseStack, x, 0, renderState.displayName.getVisualOrderText(), false, Font.DisplayMode.NORMAL, renderState.lightCoords, color, 0, CommonColors.WHITE);

        poseStack.popPose();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.health > 0) {
			poseStack.pushPose();
			poseStack.scale(renderState.scale, renderState.scale, renderState.scale);

            this.submitNameDisplay(renderState, poseStack, nodeCollector, cameraRenderState);

            RhodesType rhodesType = renderState.variant.value();
            int packedLight = renderState.lightCoords;
            if (rhodesType == RhodesTypes.Space) {
                {
                    poseStack.pushPose();
                    poseStack.mulPose(Axis.YP.rotationDegrees(renderState.bodyyaw));
                    poseStack.translate(0, 10f, 0);
                    ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etbooster), boosterModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                    {
                        poseStack.pushPose();
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                        poseStack.translate(0, 4, -2);
                        poseStack.scale(2.2f, 2.2f, 2.2f);
                        if (renderState.b2Energy > 0) {
                            ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etb2spirit), shuttleModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                        }
                        poseStack.popPose();
                    }
                    poseStack.popPose();
                }
			} else {
                RenderType textureRenderType = RenderTypes.entitySolid(rhodesType.getTexture());
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
                        ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etb2spirit), b2ForSpiritModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                        poseStack.popPose();
                    }
                    if (renderState.jet && renderState.b2Energy > 0) {
                        poseStack.pushPose();
                        poseStack.scale(2.5F, 2.5F, 2.5F);
                        ObjModels.submit(nodeCollector, RenderTypes.entityTranslucent(flame), b2jetForRhodesModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                        poseStack.popPose();
                    }
                    poseStack.popPose();

                    ObjModels.submit(nodeCollector, textureRenderType, torsoModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);


                    {//RIGHT UPPERARM
                        poseStack.pushPose();
                        poseStack.translate(-6.4f, 0, 0);
                        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rightarmyaw));
                        poseStack.scale(-1, 1, 1);
                        ObjModels.submit(nodeCollector, textureRenderType, upperArmModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        //RIGHT LOWERARM
                        {
                            poseStack.pushPose();
                            poseStack.translate(0, -1.5f, 0);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightarmpitch));
                            ObjModels.submit(nodeCollector, textureRenderType, lowerArmModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            poseStack.scale(-1, 1, 1);
                            ObjModels.submit(nodeCollector, textureRenderType, flameThrowerModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            poseStack.popPose();
                        }

                        poseStack.popPose();
                    }

                    {//LEFT UPPERARM
                        poseStack.pushPose();
                        poseStack.translate(6.4f, 0, 0);
                        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.leftarmyaw));
                        ObjModels.submit(nodeCollector, textureRenderType, upperArmModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        {//LEFT LOWERARM
                            poseStack.pushPose();
                            poseStack.translate(0, -1.5f, 0);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftarmpitch));
                            ObjModels.submit(nodeCollector, textureRenderType, lowerArmModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            ObjModels.submit(nodeCollector, textureRenderType, rocketLauncherModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            poseStack.popPose();
                        }

                        poseStack.popPose();
                    }

                    {//RIGHT THIGH
                        poseStack.pushPose();
                        poseStack.translate(0, -7.26756f, -0.27904f);
                        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightthighpitch));
                        poseStack.scale(-1, 1, 1);
                        ObjModels.submit(nodeCollector, textureRenderType, thighModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        {//RIGHT SHIN
                            poseStack.pushPose();
                            poseStack.translate(0, -7.17156f, -1.52395f);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightshinpitch));
                            ObjModels.submit(nodeCollector, textureRenderType, shinModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            if (renderState.isBurning) {
                                ObjModels.submit(nodeCollector, RenderTypes.entityCutout(flame), flameModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            }
                            poseStack.popPose();
                        }

                        poseStack.popPose();
                    }

                    {//LEFT THIGH
                        poseStack.pushPose();
                        poseStack.translate(0, -7.26756f, -0.27904f);
                        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftthighpitch));
                        ObjModels.submit(nodeCollector, textureRenderType, thighModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        //LEFT SHIN
                        {
                            poseStack.pushPose();
                            poseStack.translate(0, -7.17156f, -1.52395f);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftshinpitch));
                            ObjModels.submit(nodeCollector, textureRenderType, shinModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
                            if (renderState.isBurning) {
                                ObjModels.submit(nodeCollector, RenderTypes.entityCutout(flame), flameModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);
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
                        ObjModels.submit(nodeCollector, textureRenderType, headModel, poseStack, colorOfRhodes, packedLight, OverlayTexture.NO_OVERLAY);

                        {
                            int color = ARGB.colorFromFloat(0.5F, 1, 0, 0);
                            if (renderState.topLaserEnabled) {
                                ObjModels.submit(nodeCollector, RRRenderTypes.LASER_RENDER_TYPE, laserModel, poseStack, color, packedLight, OverlayTexture.NO_OVERLAY);
                            } else if (renderState.bottomLaserEnabled) {
                                poseStack.scale(1, -1, 1);
                                //GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
                                ObjModels.submit(nodeCollector, RRRenderTypes.LASER_RENDER_TYPE, laserModel, poseStack, color, packedLight, OverlayTexture.NO_OVERLAY);
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
                            ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.create(renderState.flagTextureLocation + ".png")), flagModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                        } catch (Exception ignored) {
                        }
                    }
                    if (renderState.forceFieldEnabled) {
                        ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffTorsoModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                        {//RIGHT UPPERARM
                            poseStack.pushPose();
                            poseStack.translate(-6.4f, 0, 0);
                            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rightarmyaw));
                            poseStack.scale(-1, 1, 1);
                            ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffUpperArmModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            {//RIGHT LOWERARM
                                poseStack.pushPose();
                                poseStack.translate(0, -1.5f, 0);
                                poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightarmpitch));
                                ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffLowerArmModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                                poseStack.popPose();
                            }
                            poseStack.popPose();
                        }
                        {//LEFT UPPERARM
                            poseStack.pushPose();
                            poseStack.translate(6.4f, 0, 0);
                            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.leftarmyaw));
                            ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffUpperArmModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            {//LEFT LOWERARM
                                poseStack.pushPose();
                                poseStack.translate(0, -1.5f, 0);
                                poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftarmpitch));
                                ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffLowerArmModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                                poseStack.popPose();
                            }
                            poseStack.popPose();
                        }
                        {//RIGHT THIGH
                            poseStack.pushPose();
                            poseStack.translate(0, -7.26756f, -0.27904f);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightthighpitch));
                            poseStack.scale(-1, 1, 1);
                            ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffThighModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            {//RIGHT SHIN
                                poseStack.pushPose();
                                poseStack.translate(0, -7.17156f, -1.52395f);
                                poseStack.mulPose(Axis.XP.rotationDegrees(renderState.rightshinpitch));
                                ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffShinModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                                poseStack.popPose();
                            }
                            poseStack.popPose();
                        }
                        {//LEFT THIGH
                            poseStack.pushPose();
                            poseStack.translate(0, -7.26756f, -0.27904f);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftthighpitch));
                            ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffThighModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            {//LEFT SHIN
                                poseStack.pushPose();
                                poseStack.translate(0, -7.17156f, -1.52395f);
                                poseStack.mulPose(Axis.XP.rotationDegrees(renderState.leftshinpitch));
                                ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffShinModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                                poseStack.popPose();
                            }
                            poseStack.popPose();
                        }
                        {//HEAD
                            poseStack.pushPose();
                            poseStack.translate(0, 5.23244f, 0);
                            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.headpitch));
                            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.viewYRot));
                            ObjModels.submit(nodeCollector, RRRenderTypes.CELLULAR_NOISE, ffheadModel, poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
                            poseStack.popPose();
                        }
                    }
                    poseStack.popPose();
                }
			}
	    	poseStack.popPose();
		}
		if (renderState.health < 1) {
            poseStack.pushPose();

			float elev = Mth.sin((renderState.health-renderState.partialTick)*-(Mth.PI / 100))*15;

            {
                poseStack.pushPose();
                poseStack.mulPose(Axis.YP.rotationDegrees(elev * 2));
                poseStack.mulPose(Axis.XP.rotationDegrees(elev * 3));
                renderBlastSphereWithLightningRenderType(renderState, poseStack, nodeCollector, elev, ARGB.colorFromFloat(1F, 1F, 0.25f, 0));
                poseStack.popPose();
            }

            {
                poseStack.pushPose();
                poseStack.mulPose(Axis.YP.rotationDegrees(elev * -2));
                poseStack.mulPose(Axis.ZP.rotationDegrees(elev * 4));
                renderBlastSphereWithLightningRenderType(renderState, poseStack, nodeCollector, elev - 0.2f, ARGB.colorFromFloat(1F, 1, 0.5f, 0));
                poseStack.popPose();
            }

            {
                poseStack.pushPose();
                poseStack.mulPose(Axis.XP.rotationDegrees(elev * -3));
                poseStack.mulPose(Axis.ZP.rotationDegrees(elev * 2));
                renderBlastSphereWithLightningRenderType(renderState, poseStack, nodeCollector, elev - 0.4f, CommonColors.RED);
                poseStack.popPose();
            }

            {
                poseStack.pushPose();
                poseStack.mulPose(Axis.YP.rotationDegrees(elev * -1));
                poseStack.mulPose(Axis.ZP.rotationDegrees(elev * 3));
                renderBlastSphereWithLightningRenderType(renderState, poseStack, nodeCollector, elev - 0.6f, CommonColors.YELLOW);
                poseStack.popPose();
            }

            poseStack.popPose();
		}
	}

    private void renderBlastSphereWithLightningRenderType(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, float scale, int color) {
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        ObjModels.submit(nodeCollector, RRRenderTypes.RHODES_LIGHTNING, blastSphereModel, poseStack, color, renderState.lightCoords, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

    @Override
    public void extractRenderState(EntityRhodes entity, State reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        float ptt = Math.min((entity.ticksSinceLastPacket + partialTick)/5f, 1);
        if (entity.tickCount<10) ptt = 1;
        reusedState.ptt = ptt;
        reusedState.rider = entity.rider;
        reusedState.ticksSinceLastPacket = entity.ticksSinceLastPacket;
        reusedState.health = entity.getHealth();
        reusedState.scale = entity.getScale();
        reusedState.displayName = entity.getDisplayName();
        reusedState.variant = entity.getVariant();
        reusedState.flagTextureLocation = entity.getFlagTextureLocation();
        reusedState.leftthighpitch = entity.getleftthighpitch(ptt);
        reusedState.rightthighpitch = entity.getrightthighpitch(ptt);
        reusedState.leftshinpitch = entity.getleftshinpitch(ptt);
        reusedState.rightshinpitch = entity.getrightshinpitch(ptt);
        reusedState.rightarmpitch = entity.getrightarmpitch(ptt);
        reusedState.rightarmyaw = entity.getrightarmyaw(ptt);
        reusedState.leftarmpitch = entity.getleftarmpitch(ptt);
        reusedState.leftarmyaw = entity.getleftarmyaw(ptt);
        reusedState.headpitch = entity.getheadpitch(ptt);
        reusedState.bodyyaw = entity.getbodyyaw(ptt);
        reusedState.viewYRot = entity.getViewYRot(ptt);
        reusedState.b2Energy = entity.getB2Energy();
        reusedState.isBurning = entity.isFire();
        reusedState.topLaserEnabled = entity.isTopLaserEnabled();
        reusedState.bottomLaserEnabled = entity.isBottomLaserEnabled();
        reusedState.forceFieldEnabled = entity.isForceFieldEnabled();
        reusedState.jet = entity.jet;

        Component name = reusedState.displayName;
        if (reusedState.rider != null) {
            name = name.copy().append(" - ").append(reusedState.rider.getDisplayName());
        }
        reusedState.displayName = name;
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
