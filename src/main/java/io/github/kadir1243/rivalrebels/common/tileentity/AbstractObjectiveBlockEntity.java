package io.github.kadir1243.rivalrebels.common.tileentity;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractObjectiveBlockEntity extends BaseContainerBlockEntity implements Tickable {
    private NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
    private final BaseCommandBlock commandExecutor = new BaseCommandBlock() {
        @Override
        public void setCommand(String command) {
            super.setCommand(command);
            setChanged();
        }

        @Override
        public ServerLevel getLevel() {
            return (ServerLevel)level;
        }

        @Override
        public void onUpdated() {
            BlockState lv = level.getBlockState(worldPosition);
            this.getLevel().sendBlockUpdated(worldPosition, lv, lv, 3);
        }

        @Override
        public Vec3 getPosition() {
            return Vec3.atCenterOf(worldPosition);
        }

        @Override
        public CommandSourceStack createCommandSourceStack() {
            return new CommandSourceStack(
                this,
                getPosition(),
                Vec2.ZERO,
                this.getLevel(),
                2,
                this.getName().getString(),
                this.getName(),
                this.getLevel().getServer(),
                null
            );
        }

        @Override
        public boolean isValid() {
            return !isRemoved();
        }
    };
    public double slide = 0;
    private float test = Mth.PI - 0.05F;

    public AbstractObjectiveBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public int getContainerSize()
    {
        return 16;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return null;
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

        ContainerHelper.loadAllItems(nbt, this.items, provider);
        commandExecutor.load(nbt, provider);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);

        ContainerHelper.saveAllItems(nbt, this.items, provider);
        commandExecutor.save(nbt, provider);
    }

    @Override
    public void tick() {
        slide = (Mth.cos(test) + 1) / 32 * 10;

        boolean i = level.hasNearbyAlivePlayer(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 9);
        if (i) {
            if (slide < 0.621) test += 0.05F;
        } else {
            if (slide > 0.004) test -= 0.05F;
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player, 64);
    }

}
