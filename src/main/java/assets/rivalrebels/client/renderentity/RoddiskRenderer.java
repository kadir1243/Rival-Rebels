package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelDisk;
import assets.rivalrebels.common.entity.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class RoddiskRenderer extends EntityRenderer<RoddiskBase> {
    private float er = 0;

    public RoddiskRenderer(EntityRendererFactory.Context dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(RoddiskBase entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        er += 13.46F;
        matrices.push();
        matrices.multiply(new Quaternionf(entity.getPitch(), 0.0F, 0.0F, 1.0F));
        matrices.multiply(new Quaternionf(entity.getYaw() - 90.0f + er, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.push();

        ModelDisk.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(getTexture(entity))), light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        matrices.pop();
    }

    @Override
    public Identifier getTexture(RoddiskBase entity) {
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
