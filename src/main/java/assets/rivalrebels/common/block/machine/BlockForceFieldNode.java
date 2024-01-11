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
import assets.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockForceFieldNode extends BlockContainer {
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	public BlockForceFieldNode()
	{
		super(Material.IRON);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(META, 0));
    }
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta);
    }    @Override
    public BlockStateContainer getBlockState() {
        return new BlockStateContainer(this, META);
    }
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityForceFieldNode teffn && !world.isRemote)
		{
            ItemStack stack = player.getHeldItem(hand);
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
		return false;
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int l = MathHelper.floor((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0)
		{
			world.setBlockState(pos, state.withProperty(META, 2), 2);
		}

		if (l == 1)
		{
            world.setBlockState(pos, state.withProperty(META, 5), 2);
		}

		if (l == 2)
		{
            world.setBlockState(pos, state.withProperty(META, 3), 2);
		}

		if (l == 3)
		{
            world.setBlockState(pos, state.withProperty(META, 4), 2);
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var)
	{
		return new TileEntityForceFieldNode();
	}

	/*@SideOnly(Side.CLIENT)
	IIcon	icon;
	@SideOnly(Side.CLIENT)
	IIcon	icon2;
	@SideOnly(Side.CLIENT)
	IIcon	icontop1;
	@SideOnly(Side.CLIENT)
	IIcon	icontop2;
	@SideOnly(Side.CLIENT)
	IIcon	icontop3;
	@SideOnly(Side.CLIENT)
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
