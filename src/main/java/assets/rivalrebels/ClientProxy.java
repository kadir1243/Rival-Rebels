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
package assets.rivalrebels;

import assets.rivalrebels.client.gui.*;
import assets.rivalrebels.client.renderentity.*;
import assets.rivalrebels.client.tileentityrender.*;
import assets.rivalrebels.common.entity.EntityBloodFX;
import assets.rivalrebels.common.entity.EntityGore;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.entity.RREntities;
import assets.rivalrebels.common.tileentity.RRTileEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;

import static org.lwjgl.glfw.GLFW.*;

public class ClientProxy extends CommonProxy
{
    public static final KeyBinding USE_KEY = new KeyBinding("use_key", GLFW_KEY_F, "");

    @Environment(EnvType.CLIENT)
    public static void registerKeyBinding() {
        KeyBindingHelper.registerKeyBinding(USE_KEY);
    }

	@Environment(EnvType.CLIENT)
	public static void registerRenderInformation() {
		BlockEntityRendererFactories.register(RRTileEntities.NUKE_CRATE, TileEntityNukeCrateRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.NUCLEAR_BOMB, TileEntityNuclearBombRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.PLASMA_EXPLOSION, TileEntityPlasmaExplosionRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.REACTOR, TileEntityReactorRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.JUMP_BLOCK, TileEntityJumpBlockRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.LOADER, TileEntityLoaderRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.OMEGA_OBJECTIVE, TileEntityOmegaObjectiveRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.SIGMA_OBJECTIVE, TileEntitySigmaObjectiveRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.TSAR_BOMB, TileEntityTsarBombaRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.FORCE_FIELD_NODE, TileEntityForceFieldNodeRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.GORE, TileEntityGoreRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.LAPTOP, TileEntityLaptopRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.RECIEVER, TileEntityRecieverRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.MELT_DOWN, TileEntityMeltdownRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.THEORETICAL_TSAR_BOMB, TileEntityTheoreticalTsarBombaRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.ANTIMATTER_BOMB, TileEntityAntimatterBombRenderer::new);
		BlockEntityRendererFactories.register(RRTileEntities.TACHYON_BOMB, TileEntityTachyonBombRenderer::new);
		EntityRendererRegistry.register(RREntities.GAS_GRENADE, RenderGasGrenade::new);
		EntityRendererRegistry.register(RREntities.PROPULSION_FX, manager -> new RenderBullet(manager, "fire"));
		EntityRendererRegistry.register(RREntities.PASSIVE_FIRE, manager -> new RenderBullet(manager, "fire"));
		EntityRendererRegistry.register(RREntities.CUCHILLO, RenderCuchillo::new);
		EntityRendererRegistry.register(RREntities.FLAME_BALL, RenderFlame::new);
		EntityRendererRegistry.register(RREntities.FLAME_BALL1, RenderFlameRedBlue::new);
		EntityRendererRegistry.register(RREntities.FLAME_BALL2, RenderFlameBlue::new);
		EntityRendererRegistry.register(RREntities.ROCKET, RenderRocket::new);
		EntityRendererRegistry.register(RREntities.PLASMOID, RenderPlasmoid::new);
		EntityRendererRegistry.register(RREntities.LIGHTNING_LINK, RenderLightningLink::new);
		EntityRendererRegistry.register(RREntities.NUCLEAR_BLAST, RenderNuclearBlast::new);
		EntityRendererRegistry.register(RREntities.LAPTOP, RenderLaptop::new);
		EntityRendererRegistry.register(RREntities.RODDISK_REGULAR, RoddiskRenderer::new);
		EntityRendererRegistry.register(RREntities.RODDISK_REBEL, RoddiskRenderer::new);
		EntityRendererRegistry.register(RREntities.RODDISK_OFFICER, RoddiskRenderer::new);
		EntityRendererRegistry.register(RREntities.RODDISK_LEADER, RoddiskRenderer::new);
		EntityRendererRegistry.register(RREntities.TSAR_BLAST, RenderTsarBlast::new);
		EntityRendererRegistry.register(RREntities.LASER_LINK, RenderLaserLink::new);
		EntityRendererRegistry.register(RREntities.GORE, RenderGore::new);
		EntityRendererRegistry.register(RREntities.BLOOD, RenderBlood::new);
		EntityRendererRegistry.register(RREntities.GOO, RenderGoo::new);
		EntityRendererRegistry.register(RREntities.LASER_BURST, RenderLaserBurst::new);
		EntityRendererRegistry.register(RREntities.B83, RenderB83::new);
		EntityRendererRegistry.register(RREntities.B2SPIRIT, RenderB2Spirit::new);
		EntityRendererRegistry.register(RREntities.B2FRAG, RenderB2Frag::new);
		EntityRendererRegistry.register(RREntities.DEBRIS, RenderDebris::new);
		EntityRendererRegistry.register(RREntities.HACK_B83, RenderHackB83::new);
		EntityRendererRegistry.register(RREntities.SEEK_B83, RenderSeeker::new);
		EntityRendererRegistry.register(RREntities.RHODES, RenderRhodes::new);
		EntityRendererRegistry.register(RREntities.RHODES_HEAD, RenderRhodesHead::new);
		EntityRendererRegistry.register(RREntities.RHODES_TORSO, RenderRhodesTorso::new);
		EntityRendererRegistry.register(RREntities.RHODES_LEFT_UPPER_ARM, RenderRhodesLeftUpperArm::new);
		EntityRendererRegistry.register(RREntities.RHODES_RIGHT_UPPER_ARM, RenderRhodesRightUpperArm::new);
		EntityRendererRegistry.register(RREntities.RHODES_LEFT_LOWER_ARM, RenderRhodesLeftLowerArm::new);
		EntityRendererRegistry.register(RREntities.RHODES_RIGHT_LOWER_ARM, RenderRhodesRightLowerArm::new);
		EntityRendererRegistry.register(RREntities.RHODES_LEFT_UPPER_LEG, RenderRhodesLeftUpperLeg::new);
		EntityRendererRegistry.register(RREntities.RHODES_RIGHT_UPPER_LEG, RenderRhodesRightUpperLeg::new);
		EntityRendererRegistry.register(RREntities.RHODES_LEFT_LOWER_LEG, RenderRhodesLeftLowerLeg::new);
		EntityRendererRegistry.register(RREntities.RHODES_RIGHT_LOWER_LEG, RenderRhodesRightLowerLeg::new);
		EntityRendererRegistry.register(RREntities.B83_NO_SHROOM, RenderB83::new);
		EntityRendererRegistry.register(RREntities.SPHERE_BLAST, RenderSphereBlast::new);
		EntityRendererRegistry.register(RREntities.NUKE, RenderNuke::new);
		EntityRendererRegistry.register(RREntities.TSAR, RenderTsar::new);
		EntityRendererRegistry.register(RREntities.RODDISK_REP, RenderRoddiskRep::new);
		EntityRendererRegistry.register(RREntities.HOT_POTATO, RenderHotPotato::new);
		EntityRendererRegistry.register(RREntities.BOMB, RenderBomb::new);
		EntityRendererRegistry.register(RREntities.THEORETICAL_TSAR, RenderTheoreticalTsar::new);
		EntityRendererRegistry.register(RREntities.THEORETICAL_TSAR_BLAST, RenderTheoreticalTsarBlast::new);
		EntityRendererRegistry.register(RREntities.FLAME_BALL_GREEN, RenderFlameBallGreen::new);
		EntityRendererRegistry.register(RREntities.ANTIMATTER_BOMB, RenderAntimatterBomb::new);
		EntityRendererRegistry.register(RREntities.ANTIMATTER_BOMB_BLAST, RenderAntimatterBombBlast::new);
		EntityRendererRegistry.register(RREntities.TACHYON_BOMB, RenderTachyonBomb::new);
		EntityRendererRegistry.register(RREntities.TACHYON_BOMB_BLAST, RenderTachyonBombBlast::new);
	}

    @Override
	public void closeGui()
	{
		MinecraftClient.getInstance().setScreen(null);
	}

	@Override
	public void nextBattle()
	{
		MinecraftClient.getInstance().setScreen(new GuiNextBattle());
	}

	@Override
	public void teamWin(boolean winner)
	{
		MinecraftClient.getInstance().setScreen(winner?new GuiOmegaWin():new GuiSigmaWin());
	}

	@Override
	public void guiClass()
	{
		MinecraftClient.getInstance().setScreen(new GuiClass(RivalRebels.round.rrplayerlist.getForGameProfile(MinecraftClient.getInstance().player.getGameProfile()).rrclass));
	}

	@Override
	public void guiSpawn()
	{
		MinecraftClient.getInstance().setScreen(new GuiSpawn(RivalRebels.round.rrplayerlist.getForGameProfile(MinecraftClient.getInstance().player.getGameProfile()).rrclass));
	}

	@Override
	public void flamethrowerGui(int i)
	{
		MinecraftClient.getInstance().setScreen(new GuiFlameThrower(i));
	}

	@Override
	public void teslaGui(int i)
	{
		MinecraftClient.getInstance().setScreen(new GuiTesla(i));
	}

	@Override
	public void spawnGore(World world, EntityGore g, boolean greenblood)
	{
		MinecraftClient.getInstance().particleManager.addParticle(new EntityBloodFX((ClientWorld) world, g, !greenblood));
	}

	@Override
	public boolean spacebar()
	{
		return MinecraftClient.getInstance().options.jumpKey.wasPressed() && MinecraftClient.getInstance().currentScreen == null;
	}
	@Override
	public boolean w()
	{
		return MinecraftClient.getInstance().options.forwardKey.wasPressed() && MinecraftClient.getInstance().currentScreen == null;
	}
	@Override
	public boolean a()
	{
		return MinecraftClient.getInstance().options.leftKey.wasPressed() && MinecraftClient.getInstance().currentScreen == null;
	}
	@Override
	public boolean s()
	{
		return MinecraftClient.getInstance().options.backKey.wasPressed() && MinecraftClient.getInstance().currentScreen == null;
	}
	@Override
	public boolean d()
	{
		return MinecraftClient.getInstance().options.rightKey.wasPressed() && MinecraftClient.getInstance().currentScreen == null;
	}
	@Override
	public boolean f()
	{
		return glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_F) == GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null;
	}
	boolean prevc = false;
	public boolean c()
	{
		boolean isdown = glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_C) == GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null;
		boolean x = !prevc && isdown;
		prevc = isdown;
		return x;
	}
	boolean prevx = false;
	@Override
	public boolean x()
	{
		boolean isdown = glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_X) == GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null;
		boolean x = !prevx && isdown;
		prevx = isdown;
		return x;
	}
	@Override
	public boolean z()
	{
		return glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_Z) == GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null;
	}
	public boolean g()
	{
		return glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_G) == GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null;
	}

	@Override
	public void setOverlay(EntityRhodes rhodes)
	{
		if (rhodes.rider == MinecraftClient.getInstance().player)
		{
			RivalRebels.rrro.counter = 10;
			RivalRebels.rrro.rhodes = rhodes;
		}
	}
}
