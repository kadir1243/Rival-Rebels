package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.common.entity.EntityRhodesPiece;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public abstract class RhodesPartRenderer<T extends EntityRhodesPiece> extends EntityRenderer<T> {
    public RhodesPartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.getVariant().value().getTexture();
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(entity.getScale(), entity.getScale(), entity.getScale());
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        this.renderParts(entity, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    public void renderParts(T entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
