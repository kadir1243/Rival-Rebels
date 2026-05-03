package io.github.kadir1243.rivalrebels.client.renderhelper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.CommonColors;

public class GuiTrayModelRenderer extends PictureInPictureRenderer<TrayModelPIPRenderState> {
    private final QuadCollection armModel;
    private final QuadCollection trayModel;
    private final QuadCollection adsdragonModel;
    public GuiTrayModelRenderer(MultiBufferSource.BufferSource bufferSource, ModelManager modelManager) {
        super(bufferSource);

        armModel = modelManager.getStandaloneModel(ObjModels.ARM_MODEL);
        trayModel = modelManager.getStandaloneModel(ObjModels.TRAY_MODEL);
        adsdragonModel = modelManager.getStandaloneModel(ObjModels.ADS_DRAGON_MODEL);
    }

    @Override
    public Class<TrayModelPIPRenderState> getRenderStateClass() {
        return TrayModelPIPRenderState.class;
    }

    @Override
    protected void renderToTexture(TrayModelPIPRenderState renderState, PoseStack pose) {
        pose.pushPose();
        pose.translate(renderState.translation());
        pose.scale(-renderState.scale(), renderState.scale(), renderState.scale());
        pose.mulPose(Axis.ZP.rotationDegrees(180));
        pose.mulPose(Axis.YP.rotationDegrees(135.0F));
        pose.mulPose(Axis.YP.rotationDegrees(-135.0F));
        pose.mulPose(Axis.XP.rotationDegrees(20));
        if (!renderState.hasWeapon()) {
            pose.translate(0, 0, -0.5f);
            pose.mulPose(Axis.YP.rotationDegrees(renderState.spinfac()));
            pose.translate(0, 0, 0.5f);
        }
        // pose.mulPose(Axis.XP.rotationDegrees(Math.sin(spinfac/(40 * Math.PI)) * 10));

        // entity.pitch = -((float)Math.atan((double)(py / 40.0F))) * 40.0F;
        // entity.headYaw = (float)Math.atan((double)(px / 40.0F)) * 40.0F;
        // + (Math.sin(spinfac/(40 * Math.PI)) * 10)
        // - (spinfac * 0.5)

        pose.mulPose(Axis.YP.rotationDegrees(180));
        // pose.mulPose((spinfac * 0.5), 0, 1, 0);
        pose.translate(0, -0.5 * 1.5, (-0.5 - 0.34) * -1.5);
        ObjModels.render(trayModel, bufferSource.getBuffer(RenderTypes.entitySolid(RRIdentifiers.etreciever)), pose, CommonColors.WHITE, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        if (renderState.hasWeapon()) {
            pose.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
            pose.mulPose(Axis.YP.rotationDegrees((float) (-Math.atan(renderState.x1() / 40.0F) * 40.0F)));
            ObjModels.render(armModel, bufferSource.getBuffer(RenderTypes.entitySolid(RRIdentifiers.etreciever)), pose, CommonColors.WHITE, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
            pose.mulPose(Axis.XP.rotationDegrees((float) (Math.atan(renderState.y1() / 40.0F) * 40.0F + 20)));
            ObjModels.render(adsdragonModel, bufferSource.getBuffer(RenderTypes.entitySolid(RRIdentifiers.etadsdragon)), pose, CommonColors.WHITE, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        }
        pose.popPose();
    }

    @Override
    protected String getTextureLabel() {
        return "Tray Model";
    }
}
