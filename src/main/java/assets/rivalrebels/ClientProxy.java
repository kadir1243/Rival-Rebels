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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;

public class ClientProxy extends CommonProxy
{
    public static final KeyBinding USE_KEY = new KeyBinding("use_key", GLFW.GLFW_KEY_F, "");

    @OnlyIn(Dist.CLIENT)
    public static void registerKeyBinding() {
        ClientRegistry.registerKeyBinding(USE_KEY);
    }

	@OnlyIn(Dist.CLIENT)
	public static void registerRenderInformation(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(RRTileEntities.NUKE_CRATE, TileEntityNukeCrateRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.NUCLEAR_BOMB, TileEntityNuclearBombRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.PLASMA_EXPLOSION, TileEntityPlasmaExplosionRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.REACTOR, TileEntityReactorRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.JUMP_BLOCK, TileEntityJumpBlockRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.LOADER, TileEntityLoaderRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.OMEGA_OBJECTIVE, TileEntityOmegaObjectiveRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.SIGMA_OBJECTIVE, TileEntitySigmaObjectiveRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.TSAR_BOMB, TileEntityTsarBombaRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.FORCE_FIELD_NODE, TileEntityForceFieldNodeRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.GORE, TileEntityGoreRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.LAPTOP, TileEntityLaptopRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.RECIEVER, TileEntityRecieverRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.MELT_DOWN, TileEntityMeltdownRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.THEORETICAL_TSAR_BOMB, TileEntityTheoreticalTsarBombaRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.ANTIMATTER_BOMB, TileEntityAntimatterBombRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.TACHYON_BOMB, TileEntityTachyonBombRenderer::new);
		event.registerEntityRenderer(RREntities.GAS_GRENADE, RenderGasGrenade::new);
		event.registerEntityRenderer(RREntities.PROPULSION_FX, manager -> new RenderBullet(manager, "fire"));
		event.registerEntityRenderer(RREntities.PASSIVE_FIRE, manager -> new RenderBullet(manager, "fire"));
		event.registerEntityRenderer(RREntities.CUCHILLO, RenderCuchillo::new);
		event.registerEntityRenderer(RREntities.FLAME_BALL, RenderFlame::new);
		event.registerEntityRenderer(RREntities.FLAME_BALL1, RenderFlameRedBlue::new);
		event.registerEntityRenderer(RREntities.FLAME_BALL2, RenderFlameBlue::new);
		event.registerEntityRenderer(RREntities.ROCKET, RenderRocket::new);
		event.registerEntityRenderer(RREntities.PLASMOID, RenderPlasmoid::new);
		event.registerEntityRenderer(RREntities.LIGHTNING_LINK, RenderLightningLink::new);
		event.registerEntityRenderer(RREntities.NUCLEAR_BLAST, RenderNuclearBlast::new);
		event.registerEntityRenderer(RREntities.LAPTOP, RenderLaptop::new);
		event.registerEntityRenderer(RREntities.RODDISK_REGULAR, RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.RODDISK_REBEL, RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.RODDISK_OFFICER, RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.RODDISK_LEADER, RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.TSAR_BLAST, RenderTsarBlast::new);
		event.registerEntityRenderer(RREntities.LASER_LINK, RenderLaserLink::new);
		event.registerEntityRenderer(RREntities.GORE, RenderGore::new);
		event.registerEntityRenderer(RREntities.BLOOD, RenderBlood::new);
		event.registerEntityRenderer(RREntities.GOO, RenderGoo::new);
		event.registerEntityRenderer(RREntities.LASER_BURST, RenderLaserBurst::new);
		event.registerEntityRenderer(RREntities.B83, RenderB83::new);
		event.registerEntityRenderer(RREntities.B2SPIRIT, RenderB2Spirit::new);
		event.registerEntityRenderer(RREntities.B2FRAG, RenderB2Frag::new);
		event.registerEntityRenderer(RREntities.DEBRIS, RenderDebris::new);
		event.registerEntityRenderer(RREntities.HACK_B83, RenderHackB83::new);
		event.registerEntityRenderer(RREntities.SEEK_B83, RenderSeeker::new);
		event.registerEntityRenderer(RREntities.RHODES, RenderRhodes::new);
		event.registerEntityRenderer(RREntities.RHODES_HEAD, RenderRhodesHead::new);
		event.registerEntityRenderer(RREntities.RHODES_TORSO, RenderRhodesTorso::new);
		event.registerEntityRenderer(RREntities.RHODES_LEFT_UPPER_ARM, RenderRhodesLeftUpperArm::new);
		event.registerEntityRenderer(RREntities.RHODES_RIGHT_UPPER_ARM, RenderRhodesRightUpperArm::new);
		event.registerEntityRenderer(RREntities.RHODES_LEFT_LOWER_ARM, RenderRhodesLeftLowerArm::new);
		event.registerEntityRenderer(RREntities.RHODES_RIGHT_LOWER_ARM, RenderRhodesRightLowerArm::new);
		event.registerEntityRenderer(RREntities.RHODES_LEFT_UPPER_LEG, RenderRhodesLeftUpperLeg::new);
		event.registerEntityRenderer(RREntities.RHODES_RIGHT_UPPER_LEG, RenderRhodesRightUpperLeg::new);
		event.registerEntityRenderer(RREntities.RHODES_LEFT_LOWER_LEG, RenderRhodesLeftLowerLeg::new);
		event.registerEntityRenderer(RREntities.RHODES_RIGHT_LOWER_LEG, RenderRhodesRightLowerLeg::new);
		event.registerEntityRenderer(RREntities.B83_NO_SHROOM, RenderB83::new);
		event.registerEntityRenderer(RREntities.SPHERE_BLAST, RenderSphereBlast::new);
		event.registerEntityRenderer(RREntities.NUKE, RenderNuke::new);
		event.registerEntityRenderer(RREntities.TSAR, RenderTsar::new);
		event.registerEntityRenderer(RREntities.RODDISK_REP, RenderRoddiskRep::new);
		event.registerEntityRenderer(RREntities.HOT_POTATO, RenderHotPotato::new);
		event.registerEntityRenderer(RREntities.BOMB, RenderBomb::new);
		event.registerEntityRenderer(RREntities.THEORETICAL_TSAR, RenderTheoreticalTsar::new);
		event.registerEntityRenderer(RREntities.THEORETICAL_TSAR_BLAST, RenderTheoreticalTsarBlast::new);
		event.registerEntityRenderer(RREntities.FLAME_BALL_GREEN, RenderFlameBallGreen::new);
		event.registerEntityRenderer(RREntities.ANTIMATTER_BOMB, RenderAntimatterBomb::new);
		event.registerEntityRenderer(RREntities.ANTIMATTER_BOMB_BLAST, RenderAntimatterBombBlast::new);
		event.registerEntityRenderer(RREntities.TACHYON_BOMB, RenderTachyonBomb::new);
		event.registerEntityRenderer(RREntities.TACHYON_BOMB_BLAST, RenderTachyonBombBlast::new);
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
		return glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_X) == GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null;
	}
	@Override
	public boolean d()
	{
		return glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_D) == GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null;
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
		boolean isdown = glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW_KEY_S) == GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null;
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
