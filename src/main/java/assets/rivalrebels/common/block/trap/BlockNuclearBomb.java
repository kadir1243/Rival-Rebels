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
package assets.rivalrebels.common.block.trap;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.tileentity.TileEntityNuclearBomb;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import java.util.Random;

public class BlockNuclearBomb extends BlockContainer {
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);
	public BlockNuclearBomb() {
		super(Material.iron);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
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
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (MathHelper.abs((float) placer.posX - pos.getX()) < 2.0F && MathHelper.abs((float) placer.posZ - pos.getZ()) < 2.0F) {
			double var5 = placer.posY + 1.82D - placer.getYOffset();

			if (var5 - pos.getY() > 2.0D) {
				return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
			}

			if (pos.getY() - var5 > 0.0D) {
				return this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
			}
		}
		int rotation = MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        return this.getDefaultState().withProperty(FACING, rotation == 0 ? EnumFacing.NORTH : rotation == 1 ? EnumFacing.EAST : rotation == 2 ? EnumFacing.SOUTH : EnumFacing.WEST);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking())
		{
			if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == RivalRebels.pliers)
			{
				FMLNetworkHandler.openGui(player, RivalRebels.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			}
			else if (!world.isRemote)
			{
                player.addChatMessage(new ChatComponentText("§7[§4Orders§7] §cUse pliers to open."));
			}
		}
		return false;
	}

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    /**
	 * each class overrides this to return a new <className>
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World, int var)
	{
		return new TileEntityNuclearBomb();
	}
}
