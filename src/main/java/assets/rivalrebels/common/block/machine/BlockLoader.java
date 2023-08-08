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
import assets.rivalrebels.common.tileentity.TileEntityLoader;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import java.util.Random;

public class BlockLoader extends BlockContainer
{
	private Random	random	= new Random();
	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

	public BlockLoader()
	{
		super(Material.iron);
		this.setCreativeTab(CreativeTabs.tabDecorations);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING);
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        int rotation = MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EnumFacing rFacing;
        switch (rotation) {
            case 0:
                rFacing = EnumFacing.NORTH;
                break;
            case 1:
                rFacing = EnumFacing.EAST;
                break;
            case 2:
                rFacing = EnumFacing.SOUTH;
                break;
            case 3:
                rFacing = EnumFacing.WEST;
                break;
            default:
                rFacing = EnumFacing.DOWN;
                break;

        }
        return this.getDefaultState().withProperty(FACING, rFacing);
    }

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether or not to render the shared face of two adjacent blocks and also whether the player can attach torches, redstone wire,
	 * etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean isFullCube()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return -1;
	}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityLoader tileEntityLoader = null;
		try {
			tileEntityLoader = (TileEntityLoader) world.getTileEntity(pos);
		} catch (Exception e) {
			// no error message ;]
		}

		world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RivalRebels.loader)));
		if (tileEntityLoader != null)
		{
			for (int slotNumber = 0; slotNumber < tileEntityLoader.getSizeInventory(); ++slotNumber)
			{
				ItemStack stackInSlot = tileEntityLoader.getStackInSlot(slotNumber);

                float var10 = this.random.nextFloat() * 0.8F + 0.1F;
                float var11 = this.random.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem;

                for (float var12 = this.random.nextFloat() * 0.8F + 0.1F; stackInSlot.stackSize > 0; world.spawnEntityInWorld(entityItem))
                {
                    int var13 = this.random.nextInt(21) + 10;

                    if (var13 > stackInSlot.stackSize)
                    {
                        var13 = stackInSlot.stackSize;
                    }

                    stackInSlot.stackSize -= var13;
                    entityItem = new EntityItem(world, (pos.getX() + var10), (pos.getY() + var11), (pos.getZ() + var12), new ItemStack(stackInSlot.getItem(), var13, stackInSlot.getItemDamage()));
                    float var15 = 0.05F;
                    entityItem.motionX = ((float) this.random.nextGaussian() * var15);
                    entityItem.motionY = ((float) this.random.nextGaussian() * var15 + 0.2F);
                    entityItem.motionZ = ((float) this.random.nextGaussian() * var15);

                    if (stackInSlot.hasTagCompound())
                    {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) stackInSlot.getTagCompound().copy());
                    }
                }
            }
			tileEntityLoader.invalidate();
		}

		super.breakBlock(world, pos, state);
	}


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, RivalRebels.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		}
		RivalRebelsSoundPlayer.playSound(world, 10, 3, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World, int var)
	{
		return new TileEntityLoader();
	}
}
