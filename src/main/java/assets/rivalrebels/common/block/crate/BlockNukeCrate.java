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
import assets.rivalrebels.common.block.trap.BlockAntimatterBomb;
import assets.rivalrebels.common.block.trap.BlockNuclearBomb;
import assets.rivalrebels.common.block.trap.BlockTachyonBomb;
import assets.rivalrebels.common.block.trap.BlockTheoreticalTsarBomba;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.TextPacket;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockNukeCrate extends BlockContainer {
	public BlockNukeCrate()
	{
		super(Material.wood);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (getBlock(world, pos.up()) == RivalRebels.nukeCrateBottom)
		{
			onNeighborBlockChange(world, pos.up(), state, this);
		}
		else if (getBlock(world, pos.down()) == RivalRebels.nukeCrateBottom)
		{
			onNeighborBlockChange(world, pos.down(), state, this);
		}
		else if (getBlock(world, pos.south()) == RivalRebels.nukeCrateBottom)
		{
			onNeighborBlockChange(world, pos.south(), state, this);
		}
		else if (getBlock(world, pos.north()) == RivalRebels.nukeCrateBottom)
		{
			onNeighborBlockChange(world, pos.north(), state, this);
		}
		else if (getBlock(world, pos.east()) == RivalRebels.nukeCrateBottom)
		{
			onNeighborBlockChange(world, pos.east(), state, this);
		}
		else if (getBlock(world, pos.west()) == RivalRebels.nukeCrateBottom)
		{
			onNeighborBlockChange(world, pos.west(), state, this);
		}
		else if (getBlock(world, pos.up()) == Blocks.lava)
		{
			world.setBlockToAir(pos);
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, false);
		}
		else if (getBlock(world, pos.down()) == Blocks.lava)
		{
			world.setBlockToAir(pos);
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, false);
		}
		else if (getBlock(world, pos.south()) == Blocks.lava)
		{
			world.setBlockToAir(pos);
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, false);
		}
		else if (getBlock(world, pos.north()) == Blocks.lava)
		{
			world.setBlockToAir(pos);
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, false);
		}
		else if (getBlock(world, pos.east()) == Blocks.lava)
		{
			world.setBlockToAir(pos);
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, false);
		}
		else if (getBlock(world, pos.west()) == Blocks.lava)
		{
			world.setBlockToAir(pos);
			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, false);
		}
	}

    private Block getBlock(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (this == RivalRebels.nukeCrateTop)
		{
			if (player.inventory.getCurrentItem() != null)
			{
				if (player.inventory.getCurrentItem().getItem() == RivalRebels.pliers)
				{
					EnumFacing orientation;
                    BlockPos east = pos.east();
                    BlockPos down = pos.down();
                    BlockPos eastDown = east.down();
                    BlockPos west = pos.west();
                    BlockPos westDown = west.down();
                    BlockPos south = pos.south();
                    BlockPos southDown = down.south();
                    BlockPos north = pos.north();
                    BlockPos up = pos.up();
                    BlockPos eastUp = east.up();
                    if (getBlock(world, east) == RivalRebels.nukeCrateBottom &&
                        getBlock(world, down) == RivalRebels.nukeCrateTop &&
                        getBlock(world, eastDown) == RivalRebels.nukeCrateBottom) {
						world.setBlockToAir(pos);
						world.setBlockToAir(east);
						world.setBlockToAir(down);
						world.setBlockState(eastDown, RivalRebels.antimatterbombblock.getDefaultState().withProperty(BlockAntimatterBomb.FACING, EnumFacing.WEST));
						return true;
					}
					else if (getBlock(world, west) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down) == RivalRebels.nukeCrateTop &&
							getBlock(world, westDown) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(west);
						world.setBlockToAir(down);
						world.setBlockState(westDown, RivalRebels.antimatterbombblock.getDefaultState().withProperty(BlockAntimatterBomb.FACING, EnumFacing.EAST));
						return true;
					}
					else if (getBlock(world, south) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down) == RivalRebels.nukeCrateTop &&
							getBlock(world, southDown) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(south);
						world.setBlockToAir(down);
						world.setBlockState(southDown, RivalRebels.antimatterbombblock.getDefaultState().withProperty(BlockAntimatterBomb.FACING, EnumFacing.NORTH));
						return true;
					}
					else if (getBlock(world, north) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down) == RivalRebels.nukeCrateTop &&
							getBlock(world, down.north()) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(north);
						world.setBlockToAir(down);
						world.setBlockState(down.north(), RivalRebels.antimatterbombblock.getDefaultState().withProperty(BlockAntimatterBomb.FACING, EnumFacing.SOUTH));
						return true;
					}
                    if (	getBlock(world, east) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up) == RivalRebels.nukeCrateTop &&
							getBlock(world, eastUp) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(up);
						world.setBlockToAir(eastUp);
						world.setBlockToAir(pos);
						world.setBlockState(east, RivalRebels.tachyonbombblock.getDefaultState().withProperty(BlockTachyonBomb.FACING, EnumFacing.WEST));
						return true;
					}
					else if (getBlock(world, west) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up) == RivalRebels.nukeCrateTop &&
							getBlock(world, west.up()) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(up);
						world.setBlockToAir(west.up());
						world.setBlockToAir(pos);
						world.setBlockState(west, RivalRebels.tachyonbombblock.getDefaultState().withProperty(BlockTachyonBomb.FACING, EnumFacing.EAST));
						return true;
					}
					else if (getBlock(world, south) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up) == RivalRebels.nukeCrateTop &&
							getBlock(world, up.south()) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(up);
						world.setBlockToAir(up.south());
						world.setBlockToAir(pos);
						world.setBlockState(south, RivalRebels.tachyonbombblock.getDefaultState().withProperty(BlockTachyonBomb.FACING, EnumFacing.NORTH));
						return true;
					}
					else if (getBlock(world, north) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up) == RivalRebels.nukeCrateTop &&
							getBlock(world, up.north()) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(up);
						world.setBlockToAir(up.north());
						world.setBlockToAir(pos);
						world.setBlockState(north, RivalRebels.tachyonbombblock.getDefaultState().withProperty(BlockTachyonBomb.FACING, EnumFacing.SOUTH));
						return true;
					}
					else if (getBlock(world, east) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.east(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.east(3)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down) == RivalRebels.nukeCrateTop &&
							getBlock(world, eastDown) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.east(2).down()) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.east(3).down()) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(east);
						world.setBlockToAir(pos.east(2));
						world.setBlockToAir(pos.east(3));
						world.setBlockToAir(down);
						world.setBlockState(eastDown, RivalRebels.tsarbombablock.getDefaultState().withProperty(BlockTheoreticalTsarBomba.FACING, EnumFacing.WEST));
						world.setBlockToAir(pos.east(2).down());
						world.setBlockToAir(pos.east(3).down());
						return true;
					}
					else if (getBlock(world, west) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.west(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.west(3)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down) == RivalRebels.nukeCrateTop &&
							getBlock(world, westDown) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.west(2).down()) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.west(3).down()) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(west);
						world.setBlockToAir(pos.west(2));
						world.setBlockToAir(pos.west(3));
						world.setBlockToAir(down);
						world.setBlockState(westDown, RivalRebels.tsarbombablock.getDefaultState().withProperty(BlockTheoreticalTsarBomba.FACING, EnumFacing.EAST));
						world.setBlockToAir(pos.west(2).down());
						world.setBlockToAir(pos.west(3).down());
						return true;
					}
					else if (getBlock(world, south) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.south(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.south(3)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down) == RivalRebels.nukeCrateTop &&
							getBlock(world, southDown) == RivalRebels.nukeCrateTop &&
							getBlock(world, down.south(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down.south(3)) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(south);
						world.setBlockToAir(pos.south(2));
						world.setBlockToAir(pos.south(3));
						world.setBlockToAir(down);
						world.setBlockState(southDown, RivalRebels.tsarbombablock.getDefaultState().withProperty(BlockTheoreticalTsarBomba.FACING, EnumFacing.NORTH));
						world.setBlockToAir(down.south(2));
						world.setBlockToAir(down.south(3));
						return true;
					}
					else if (getBlock(world, north) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.north(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.north(3)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down) == RivalRebels.nukeCrateTop &&
							getBlock(world, down.north()) == RivalRebels.nukeCrateTop &&
							getBlock(world, down.north(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, down.north(3)) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(north);
						world.setBlockToAir(pos.north(2));
						world.setBlockToAir(pos.north(3));
						world.setBlockToAir(down);
						world.setBlockState(down.north(), RivalRebels.tsarbombablock.getDefaultState().withProperty(BlockTheoreticalTsarBomba.FACING, EnumFacing.SOUTH));
						world.setBlockToAir(down.north(2));
						world.setBlockToAir(down.north(3));
						return true;
					}
					else if (getBlock(world, east) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.east(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.east(3)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up) == RivalRebels.nukeCrateTop &&
							getBlock(world, eastUp) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.east(2).up()) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.east(3).up()) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(eastUp);
						world.setBlockToAir(pos.east(2));
						world.setBlockToAir(pos.east(3));
						world.setBlockToAir(up);
						world.setBlockState(east, RivalRebels.theoreticaltsarbombablock.getDefaultState().withProperty(BlockTheoreticalTsarBomba.FACING, EnumFacing.WEST));
						world.setBlockToAir(pos.east(2).up());
						world.setBlockToAir(pos.east(3).up());
						return true;
					}
					else if (getBlock(world, west) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.west(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.west(3)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up) == RivalRebels.nukeCrateTop &&
							getBlock(world, west.up()) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.west(2).up()) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.west(3).up()) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(west.up());
						world.setBlockToAir(pos.west(2));
						world.setBlockToAir(pos.west(3));
						world.setBlockToAir(up);
						world.setBlockState(west, RivalRebels.theoreticaltsarbombablock.getDefaultState().withProperty(BlockTheoreticalTsarBomba.FACING, EnumFacing.EAST));
						world.setBlockToAir(pos.west(2).up());
						world.setBlockToAir(pos.west(3).up());
						return true;
					}
					else if (getBlock(world, south) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.south(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.south(3)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up) == RivalRebels.nukeCrateTop &&
							getBlock(world, up.south()) == RivalRebels.nukeCrateTop &&
							getBlock(world, up.south(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up.south(3)) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(up.south());
						world.setBlockToAir(pos.south(2));
						world.setBlockToAir(pos.south(3));
						world.setBlockToAir(up);
						world.setBlockState(south, RivalRebels.theoreticaltsarbombablock.getDefaultState().withProperty(BlockTheoreticalTsarBomba.FACING, EnumFacing.NORTH));
						world.setBlockToAir(up.south(2));
						world.setBlockToAir(up.south(3));
						return true;
					}
					else if (getBlock(world, north) == RivalRebels.nukeCrateTop &&
							getBlock(world, pos.north(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, pos.north(3)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up) == RivalRebels.nukeCrateTop &&
							getBlock(world, up.north()) == RivalRebels.nukeCrateTop &&
							getBlock(world, up.north(2)) == RivalRebels.nukeCrateBottom &&
							getBlock(world, up.north(3)) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(pos);
						world.setBlockToAir(up.north());
						world.setBlockToAir(pos.north(2));
						world.setBlockToAir(pos.north(3));
						world.setBlockToAir(up);
						world.setBlockState(north, RivalRebels.theoreticaltsarbombablock.getDefaultState().withProperty(BlockTheoreticalTsarBomba.FACING, EnumFacing.SOUTH));
						world.setBlockToAir(up.north(2));
						world.setBlockToAir(up.north(3));
						return true;
					}
					else if (getBlock(world, up) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(up);
						orientation = EnumFacing.DOWN;
					}
					else if (getBlock(world, down) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(down);
						orientation = EnumFacing.UP;
					}
					else if (getBlock(world, south) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(south);
						orientation = EnumFacing.NORTH;
					}
					else if (getBlock(world, north) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(north);
						orientation = EnumFacing.SOUTH;
					}
					else if (getBlock(world, east) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(east);
						orientation = EnumFacing.WEST;
					}
					else if (getBlock(world, west) == RivalRebels.nukeCrateBottom)
					{
						world.setBlockToAir(west);
						orientation = EnumFacing.EAST;
					}
					else
					{
						return false;
					}
					world.setBlockState(pos, RivalRebels.nuclearBomb.getDefaultState().withProperty(BlockNuclearBomb.FACING, orientation));
					return true;
				}
				else if (!world.isRemote)
				{
					PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Orders RivalRebels.message.use " + RivalRebels.pliers.getUnlocalizedName() + ".name"), (EntityPlayerMP) player);
				}
			}
			else if (!world.isRemote)
			{
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Orders RivalRebels.message.use " + RivalRebels.pliers.getUnlocalizedName() + ".name"), (EntityPlayerMP) player);
			}
		}
		return false;
	}

    @Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var)
	{
		return new TileEntityNukeCrate();
	}
}
