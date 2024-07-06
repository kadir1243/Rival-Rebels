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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlockTimedBomb extends FallingBlock {
    public static final MapCodec<BlockTimedBomb> CODEC = AnvilBlock.simpleCodec(BlockTimedBomb::new);
    int	ticksSincePlaced;

	public BlockTimedBomb(Properties settings) {
		super(settings);
		ticksSincePlaced = 0;
	}

    @Override
    protected MapCodec<BlockTimedBomb> codec() {
        return CODEC;
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, net.minecraft.world.level.Explosion explosion) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RRConfig.SERVER.getTimedbombExplosionSize(), false, true, RivalRebelsDamageSource.timedBomb(world));
		RivalRebelsSoundPlayer.playSound(world, 26, 0, x + 0.5f, y + 0.5f, z + 0.5f, 2f, 0.3f);
	}

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RRConfig.SERVER.getTimedbombExplosionSize(), false, true, RivalRebelsDamageSource.timedBomb(world));
		RivalRebelsSoundPlayer.playSound(world, 26, 0, x + 0.5f, y + 0.5f, z + 0.5f, 2f, 0.3f);
        return state;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        ticksSincePlaced = 0;
        world.scheduleTick(pos, this, 8);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		world.scheduleTick(pos, this, 8);
		ticksSincePlaced += 1;
		if (ticksSincePlaced >= RRConfig.SERVER.getTimedbombTimer() * 2.5)
		{
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RRConfig.SERVER.getTimedbombExplosionSize(), false, true, RivalRebelsDamageSource.timedBomb(world));
			RivalRebelsSoundPlayer.playSound(world, 26, 0, x + 0.5f, y + 0.5f, z + 0.5f, 2f, 0.3f);
		}
		if (ticksSincePlaced == 100)
		{
			ticksSincePlaced = 0;
		}
		if (world.getBlockState(pos.above()).is(RRBlocks.light) && ticksSincePlaced <= 93)
		{
			world.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
			world.playLocalSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 1, 1, true);
		}
		else
		{
			if (ticksSincePlaced <= 93)
			{
				world.setBlockAndUpdate(pos.above(), RRBlocks.light.defaultBlockState());
				world.playLocalSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 1, 0.7F, true);
			}
			else
			{
				world.playLocalSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 2F, 2F, true);
			}
		}
	}

	/*@Environment(EnvType.CLIENT)
	IIcon	icon1;
	@Environment(EnvType.CLIENT)
	IIcon	icon2;
	@Environment(EnvType.CLIENT)
	IIcon	icon3;
	@Environment(EnvType.CLIENT)
	IIcon	icon4;
	@Environment(EnvType.CLIENT)
	IIcon	icon5;
	@Environment(EnvType.CLIENT)
	IIcon	icon6;

	@Environment(EnvType.CLIENT)
	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (side == 0) return icon1;
		if (side == 1) return icon2;
		if (side == 2) return icon3;
		if (side == 3) return icon4;
		if (side == 4) return icon5;
		if (side == 5) return icon6;
		return icon1;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:ac"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:ae"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:ac"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:ac"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:ab"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:ab"); // SIDE E
	}*/

	/*public void onFinishFalling(Level par1World, int par2, int par3, int par4, int par5)
	{
		par1World.setBlock(par2, par3, par4, Blocks.AIR);
		new Explosion(par1World, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, RRConfig.SERVER.getTimedbombExplosionSize(), false, true, RivalRebelsDamageSource.timebomb);
		RivalRebelsSoundPlayer.playSound(par1World, 26, 0, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, 2f, 0.3f);
	}*/
}
