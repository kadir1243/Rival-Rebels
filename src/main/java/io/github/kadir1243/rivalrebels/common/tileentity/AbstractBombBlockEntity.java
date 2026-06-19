package io.github.kadir1243.rivalrebels.common.tileentity;

import com.mojang.authlib.GameProfile;
import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class AbstractBombBlockEntity extends BaseContainerBlockEntity {
    public GameProfile player = null;
    public RivalRebelsTeam rrteam = null;
    private NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);
    public int countdown = RRConfig.SERVER.getNuclearBombCountdown() * 20;
    public float megaton = 0;
    public boolean hasTrollface	= false;
    public boolean hasExplosive	= false;
    public boolean hasFuse = false;
    public boolean hasChip = false;

    protected AbstractBombBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        ContainerHelper.saveAllItems(output, this.items);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        hasFuse = getItem(0).is(RRItems.fuse);
    }

    @Override
    protected Component getDefaultName() {
        return this.getBlockState().getBlock().getName();
    }

    public static void explodeEmpty(Level level, BlockPos pos) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SKIP_BLOCK_ENTITY_SIDEEFFECTS);
        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 4, Level.ExplosionInteraction.NONE);
    }
}
