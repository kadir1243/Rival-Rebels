package assets.rivalrebels.client.tileentityrender;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.ApiStatus;

public interface CustomRenderBoxExtension<T extends BlockEntity> {
    AABB getRenderBoundingBox(T blockEntity);

    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    static <T extends BlockEntity> boolean isBlockEntityRendererVisible(BlockEntityRenderDispatcher dispatcher, BlockEntity blockEntity, Frustum frustum) {
        BlockEntityRenderer<T> renderer = (BlockEntityRenderer<T>) dispatcher.getRenderer(blockEntity);
        if (renderer instanceof CustomRenderBoxExtension<?> extension) {
            return frustum.isVisible(((CustomRenderBoxExtension<T>)extension).getRenderBoundingBox((T) blockEntity));
        }
        return frustum.isVisible(new AABB(blockEntity.getBlockPos()));
    }
}
