package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelDisk;
import assets.rivalrebels.common.entity.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class RoddiskRenderer extends EntityRenderer<RoddiskBase> {
    private float er = 0;

    public RoddiskRenderer(EntityRendererProvider.Context dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(RoddiskBase entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        er += 13.46F;
        matrices.pushPose();
        matrices.mulPose(new Quaternionf(entity.getXRot(), 0.0F, 0.0F, 1.0F));
        matrices.mulPose(new Quaternionf(entity.getYRot() - 90.0f + er, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.pushPose();

        ModelDisk.render(matrices, vertexConsumers.getBuffer(RenderType.entitySolid(getTextureLocation(entity))), light, OverlayTexture.NO_OVERLAY);

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
