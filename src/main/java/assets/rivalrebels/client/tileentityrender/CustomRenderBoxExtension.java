package assets.rivalrebels.client.tileentityrender;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.ApiStatus;

public interface CustomRenderBoxExtension<T extends BlockEntity> {
    Box getRenderBoundingBox(T blockEntity);

    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    static <T extends BlockEntity> boolean isBlockEntityRendererVisible(BlockEntityRenderDispatcher dispatcher, BlockEntity blockEntity, Frustum frustum) {
        BlockEntityRenderer<T> renderer = (BlockEntityRenderer<T>) dispatcher.get(blockEntity);
        if (renderer instanceof CustomRenderBoxExtension<?> extension) {
            return frustum.isVisible(((CustomRenderBoxExtension<T>)extension).getRenderBoundingBox((T) blockEntity));
        }
        return frustum.isVisible(new Box(blockEntity.getPos()));
    }
}
