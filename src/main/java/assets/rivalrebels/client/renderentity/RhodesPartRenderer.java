package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.common.entity.EntityRhodesPiece;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public abstract class RhodesPartRenderer<T extends EntityRhodesPiece> extends EntityRenderer<T> {
    public RhodesPartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return RenderRhodes.texture;
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
