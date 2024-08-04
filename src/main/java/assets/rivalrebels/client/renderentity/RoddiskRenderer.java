package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelDisk;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.entity.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RoddiskRenderer extends EntityRenderer<RoddiskBase> {
    private float er = 0;

    public RoddiskRenderer(EntityRendererProvider.Context dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(RoddiskBase entity, float yaw, float partialTick, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        er = Mth.lerp(partialTick, er, er + 13.46F);
        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0f + er));
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.pushPose();

        ModelDisk.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(getTextureLocation(entity))), light, OverlayTexture.NO_OVERLAY);

        matrices.popPose();
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(RoddiskBase entity) {
        if (entity instanceof EntityRoddiskRegular)
            return RRIdentifiers.etdisk0;
        if (entity instanceof EntityRoddiskRebel)
            return RRIdentifiers.etdisk1;
        if (entity instanceof EntityRoddiskOfficer)
            return RRIdentifiers.etdisk2;
        if (entity instanceof EntityRoddiskLeader)
            return RRIdentifiers.etdisk3;
        return null;
    }


}
