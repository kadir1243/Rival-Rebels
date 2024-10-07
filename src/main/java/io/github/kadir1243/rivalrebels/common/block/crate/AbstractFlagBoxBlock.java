package io.github.kadir1243.rivalrebels.common.block.crate;

import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Arrays;

public abstract class AbstractFlagBoxBlock extends Block {
    public AbstractFlagBoxBlock(Properties properties) {
        super(properties);
    }

    public abstract ItemStack[] getItems();

    public abstract BlockState getStateToReplace();

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isShiftKeyDown() && !level.isClientSide()) {
            ItemStack[] items = getItems();
            NonNullList<ItemStack> list = NonNullList.createWithCapacity(items.length);
            list.addAll(Arrays.asList(items));
            Containers.dropContents(level, pos, list);
            level.destroyBlock(pos, false, player);
            return InteractionResult.PASS;
        }
        if (!player.isShiftKeyDown() && !level.isClientSide()) {
            player.displayClientMessage(Translations.orders().append(" ").append(Component.translatable(Translations.SHIFT_CLICK.toLanguageKey())), false);
            level.setBlockAndUpdate(pos, getStateToReplace());
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}
