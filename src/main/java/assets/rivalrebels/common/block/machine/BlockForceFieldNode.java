/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package assets.rivalrebels.common.block.machine;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.ItemChip;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.Tickable;
import assets.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import assets.rivalrebels.common.tileentity.TileEntityMachineBase;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockForceFieldNode extends BlockWithEntity {
    public static final MapCodec<BlockForceFieldNode> CODEC = createCodec(BlockForceFieldNode::new);
    public static final IntProperty META = IntProperty.of("meta", 0, 15);
	public BlockForceFieldNode(Settings settings)
	{
		super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(META, 0));
    }

    @Override
    protected MapCodec<BlockForceFieldNode> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BlockEntity te = world.getBlockEntity(pos);
		if (te instanceof TileEntityForceFieldNode teffn && !world.isClient)
		{
            ItemStack stack = player.getStackInHand(hand);
			if (!stack.isEmpty() && stack.getItem() instanceof ItemChip && teffn.uuid == null && (teffn.rrteam == null || teffn.rrteam == RivalRebelsTeam.NONE))
			{
				teffn.rrteam = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrteam;
				if (teffn.rrteam == RivalRebelsTeam.NONE || teffn.rrteam == null)
				{
					teffn.uuid = player.getGameProfile().getId();
					teffn.rrteam = null;
				}
				RivalRebelsSoundPlayer.playSound(world, 10, 5, pos);
			}
		}
		return ActionResult.PASS;
	}

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);

        return switch (MathHelper.floor((ctx.getPlayerYaw() * 4.0F / 360.0F) + 0.5D) & 3) {
            case 0 -> state.with(META, 2);
            case 1 -> state.with(META, 5);
            case 2 -> state.with(META, 3);
            case 3 -> state.with(META, 4);
            default -> state;
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityForceFieldNode(pos, state);
	}

	/*@Environment(EnvType.CLIENT)
	IIcon	icon;
	@Environment(EnvType.CLIENT)
	IIcon	icon2;
	@Environment(EnvType.CLIENT)
	IIcon	icontop1;
	@Environment(EnvType.CLIENT)
	IIcon	icontop2;
	@Environment(EnvType.CLIENT)
	IIcon	icontop3;
	@Environment(EnvType.CLIENT)
	IIcon	icontop4;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (meta == 0)
		{
			if (side == 0 || side == 1) return icontop2;
			if (side == 4) return icon2;
			return icon;
		}
		if (side == 0 || side == 1) return meta == 3 ? icontop1 : meta == 4 ? icontop2 : meta == 2 ? icontop3 : icontop4;
		if (side == meta) return icon2;
		return icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon = iconregister.registerIcon("RivalRebels:cf");
		icon2 = iconregister.registerIcon("RivalRebels:cg");
		icontop1 = iconregister.registerIcon("RivalRebels:cj");
		icontop2 = iconregister.registerIcon("RivalRebels:ck");
		icontop3 = iconregister.registerIcon("RivalRebels:cl");
		icontop4 = iconregister.registerIcon("RivalRebels:cm");
	}*/
}
