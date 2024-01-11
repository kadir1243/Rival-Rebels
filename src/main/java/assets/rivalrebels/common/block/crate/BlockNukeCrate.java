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
package assets.rivalrebels.common.block.crate;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.TextPacket;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockNukeCrate extends BlockContainer {
    public static final PropertyDirection DIRECTION = PropertyDirection.create("direction");
	public BlockNukeCrate()
	{
		super(Material.WOOD);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(DIRECTION, EnumFacing.UP));
	}

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(DIRECTION).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DIRECTION, EnumFacing.byIndex(meta));
    }
    @Override
    public BlockStateContainer getBlockState() {
        return new BlockStateContainer(this, DIRECTION);
    }
	public EnumFacing determineOrientation(World world, BlockPos pos) {
        EnumFacing targetFacing = EnumFacing.UP;
		if (this == RivalRebels.nukeCrateTop) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos offset = pos.offset(facing);
                Block block = world.getBlockState(offset).getBlock();
                if (block == RivalRebels.nukeCrateBottom) {
                    targetFacing = facing.getOpposite();
                }
            }
		} else if (this == RivalRebels.nukeCrateBottom) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos offset = pos.offset(facing);
                Block block = world.getBlockState(offset).getBlock();
                if (block == RivalRebels.nukeCrateTop) {
                    targetFacing = facing;
                }
            }
		}
		return targetFacing;
	}

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		world.setBlockState(pos, state.withProperty(DIRECTION, determineOrientation(world, pos)));
	}

    private void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        neighborChanged(world.getBlockState(new BlockPos(x, y, z)), world, new BlockPos(x, y, z), neighbor, BlockPos.ORIGIN);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        world.setBlockState(pos, state.withProperty(DIRECTION, determineOrientation(world, pos)));

        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos offset = pos.offset(facing);
            Block block = world.getBlockState(offset).getBlock();
            if (block == RivalRebels.nukeCrateBottom) {
                onNeighborBlockChange(world, x, y, z, this);
            } else if (block == Blocks.LAVA) {
                world.setBlockToAir(pos);
                world.createExplosion(null, x, y, z, 3, false);
            }
        }
	}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        ItemStack stack = player.getHeldItem(hand);

        if (this == RivalRebels.nukeCrateTop)
		{
			if (!stack.isEmpty())
			{
				if (stack.getItem() == RivalRebels.pliers)
				{
					int orientation;
					if (	getBlock(world, x + 1, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x + 1, y - 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x + 1, y, z, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x + 1, y - 1, z, RivalRebels.antimatterbombblock);
						setBlock(world, x + 1, y - 1, z, 4);
						return true;
					}
					else if (getBlock(world, x - 1, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x - 1, y - 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x - 1, y, z, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x - 1, y - 1, z, RivalRebels.antimatterbombblock);
						setBlock(world, x - 1, y - 1, z, 5);
						return true;
					}
					else if (getBlock(world, x, y, z + 1) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y - 1, z + 1) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z + 1, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x, y - 1, z + 1, RivalRebels.antimatterbombblock);
						setBlock(world, x, y - 1, z + 1, 2);
						return true;
					}
					else if (getBlock(world, x, y, z - 1) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y - 1, z - 1) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z - 1, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x, y - 1, z - 1, RivalRebels.antimatterbombblock);
						setBlock(world, x, y - 1, z - 1, 3);
						return true;
					}
					if (	getBlock(world, x + 1, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x + 1, y + 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x + 1, y + 1, z, Blocks.AIR);
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x + 1, y, z, RivalRebels.tachyonbombblock);
						setBlock(world, x + 1, y, z, 4);
						return true;
					}
					else if (getBlock(world, x - 1, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x - 1, y + 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x - 1, y + 1, z, Blocks.AIR);
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x - 1, y, z, RivalRebels.tachyonbombblock);
						setBlock(world, x - 1, y, z, 5);
						return true;
					}
					else if (getBlock(world, x, y, z + 1) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y + 1, z + 1) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x, y + 1, z + 1, Blocks.AIR);
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z + 1, RivalRebels.tachyonbombblock);
						setBlock(world, x, y, z + 1, 2);
						return true;
					}
					else if (getBlock(world, x, y, z - 1) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y + 1, z - 1) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x, y + 1, z - 1, Blocks.AIR);
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z - 1, RivalRebels.tachyonbombblock);
						setBlock(world, x, y, z - 1, 3);
						return true;
					}
					else if (getBlock(world, x + 1, y, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x + 2, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x + 3, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x + 1, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x + 2, y - 1, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x + 3, y - 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x + 1, y, z, Blocks.AIR);
						setBlock(world, x + 2, y, z, Blocks.AIR);
						setBlock(world, x + 3, y, z, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x + 1, y - 1, z, RivalRebels.tsarbombablock);
						setBlock(world, x + 1, y - 1, z, 4);
						setBlock(world, x + 2, y - 1, z, Blocks.AIR);
						setBlock(world, x + 3, y - 1, z, Blocks.AIR);
						return true;
					}
					else if (getBlock(world, x - 1, y, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x - 2, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x - 3, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x - 1, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x - 2, y - 1, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x - 3, y - 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x - 1, y, z, Blocks.AIR);
						setBlock(world, x - 2, y, z, Blocks.AIR);
						setBlock(world, x - 3, y, z, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x - 1, y - 1, z, RivalRebels.tsarbombablock);
						setBlock(world, x - 1, y - 1, z, 5);
						setBlock(world, x - 2, y - 1, z, Blocks.AIR);
						setBlock(world, x - 3, y - 1, z, Blocks.AIR);
						return true;
					}
					else if (getBlock(world, x, y, z + 1) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y, z + 2) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y, z + 3) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y - 1, z + 1) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y - 1, z + 2) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z + 3) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z + 1, Blocks.AIR);
						setBlock(world, x, y, z + 2, Blocks.AIR);
						setBlock(world, x, y, z + 3, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x, y - 1, z + 1, RivalRebels.tsarbombablock);
						setBlock(world, x, y - 1, z + 1, 2);
						setBlock(world, x, y - 1, z + 2, Blocks.AIR);
						setBlock(world, x, y - 1, z + 3, Blocks.AIR);
						return true;
					}
					else if (getBlock(world, x, y, z - 1) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y, z - 2) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y, z - 3) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y - 1, z - 1) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y - 1, z - 2) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y - 1, z - 3) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z - 1, Blocks.AIR);
						setBlock(world, x, y, z - 2, Blocks.AIR);
						setBlock(world, x, y, z - 3, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						world.setBlockState(new BlockPos(x, y - 1, z - 1), RivalRebels.tsarbombablock.getStateFromMeta(3));
						setBlock(world, x, y - 1, z - 2, Blocks.AIR);
						setBlock(world, x, y - 1, z - 3, Blocks.AIR);
						return true;
					}
					else if (getBlock(world, x + 1, y, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x + 2, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x + 3, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x + 1, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x + 2, y + 1, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x + 3, y + 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x + 1, y + 1, z, Blocks.AIR);
						setBlock(world, x + 2, y, z, Blocks.AIR);
						setBlock(world, x + 3, y, z, Blocks.AIR);
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x + 1, y, z, RivalRebels.theoreticaltsarbombablock);
						setBlock(world, x + 1, y, z, 4);
						setBlock(world, x + 2, y + 1, z, Blocks.AIR);
						setBlock(world, x + 3, y + 1, z, Blocks.AIR);
						return true;
					}
					else if (getBlock(world, x - 1, y, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x - 2, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x - 3, y, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x - 1, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x - 2, y + 1, z) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x - 3, y + 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x - 1, y + 1, z, Blocks.AIR);
						setBlock(world, x - 2, y, z, Blocks.AIR);
						setBlock(world, x - 3, y, z, Blocks.AIR);
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x - 1, y, z, RivalRebels.theoreticaltsarbombablock);
						setBlock(world, x - 1, y, z, 5);
						setBlock(world, x - 2, y + 1, z, Blocks.AIR);
						setBlock(world, x - 3, y + 1, z, Blocks.AIR);
						return true;
					}
					else if (getBlock(world, x, y, z + 1) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y, z + 2) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y, z + 3) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y + 1, z + 1) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y + 1, z + 2) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z + 3) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y + 1, z + 1, Blocks.AIR);
						setBlock(world, x, y, z + 2, Blocks.AIR);
						setBlock(world, x, y, z + 3, Blocks.AIR);
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x, y, z + 1, RivalRebels.theoreticaltsarbombablock);
						setBlock(world, x, y, z + 1, 2);
						setBlock(world, x, y + 1, z + 2, Blocks.AIR);
						setBlock(world, x, y + 1, z + 3, Blocks.AIR);
						return true;
					}
					else if (getBlock(world, x, y, z - 1) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y, z - 2) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y, z - 3) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y + 1, z - 1) == RivalRebels.nukeCrateTop &&
							getBlock(world, x, y + 1, z - 2) == RivalRebels.nukeCrateBottom &&
							getBlock(world, x, y + 1, z - 3) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y + 1, z - 1, Blocks.AIR);
						setBlock(world, x, y, z - 2, Blocks.AIR);
						setBlock(world, x, y, z - 3, Blocks.AIR);
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x, y, z - 1, RivalRebels.theoreticaltsarbombablock);
						setBlock(world, x, y, z - 1, 3);
						setBlock(world, x, y + 1, z - 2, Blocks.AIR);
						setBlock(world, x, y + 1, z - 3, Blocks.AIR);
						return true;
					}
					else if (getBlock(world, x, y + 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						orientation = 0;
					}
					else if (getBlock(world, x, y - 1, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y - 1, z, Blocks.AIR);
						orientation = 1;
					}
					else if (getBlock(world, x, y, z + 1) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z + 1, Blocks.AIR);
						orientation = 2;
					}
					else if (getBlock(world, x, y, z - 1) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x, y, z - 1, Blocks.AIR);
						orientation = 3;
					}
					else if (getBlock(world, x + 1, y, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x + 1, y, z, Blocks.AIR);
						orientation = 4;
					}
					else if (getBlock(world, x - 1, y, z) == RivalRebels.nukeCrateBottom)
					{
						setBlock(world, x - 1, y, z, Blocks.AIR);
						orientation = 5;
					}
					else
					{
						return false;
					}
                    world.setBlockState(new BlockPos(x, y, z), RivalRebels.nuclearBomb.getStateFromMeta(orientation));
					return true;
				}
				else if (!world.isRemote)
				{
                    player.sendMessage(new TextComponentTranslation("RivalRebels.Orders").appendText(" ").appendSibling(new TextComponentTranslation("RivalRebels.message")).appendText(" ").appendSibling(new TextComponentTranslation(RivalRebels.pliers.getTranslationKey() + ".name")));
				}
			}
			else if (!world.isRemote)
			{
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Orders RivalRebels.message.use " + RivalRebels.pliers.getTranslationKey() + ".name"), (EntityPlayerMP) player);
			}
		}
		return false;
	}

    private static void setBlock(World world, int x, int y, int z, Block block) {
        world.setBlockState(new BlockPos(x, y, z), block.getDefaultState());
    }

    private static void setBlock(World world, int x, int y, int z, int meta) {
        world.setBlockState(new BlockPos(x, y, z), getBlock(world, x, y, z).getStateFromMeta(meta));
    }

    private static Block getBlock(World world, int x, int y, int z) {
        return world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var)
	{
		return new TileEntityNukeCrate();
	}

	/*@SideOnly(Side.CLIENT)
	IIcon	icon;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		return icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		if (this == RivalRebels.nukeCrateTop) icon = iconregister.registerIcon("RivalRebels:ay");
		if (this == RivalRebels.nukeCrateBottom) icon = iconregister.registerIcon("RivalRebels:ax");
	}*/
}
