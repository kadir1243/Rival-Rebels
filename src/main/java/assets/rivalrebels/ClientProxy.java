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
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.Level;

import static org.lwjgl.glfw.GLFW.*;

public class ClientProxy extends CommonProxy {
    public static final KeyMapping USE_KEY = new KeyMapping("use_key", GLFW_KEY_F, "");

    @Environment(EnvType.CLIENT)
    public static void registerKeyBinding() {
        KeyBindingHelper.registerKeyBinding(USE_KEY);
    }

	@Environment(EnvType.CLIENT)
	public static void registerRenderInformation() {
		BlockEntityRenderers.register(RRTileEntities.NUKE_CRATE, TileEntityNukeCrateRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.NUCLEAR_BOMB, TileEntityNuclearBombRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.PLASMA_EXPLOSION, TileEntityPlasmaExplosionRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.REACTOR, TileEntityReactorRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.JUMP_BLOCK, TileEntityJumpBlockRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.LOADER, TileEntityLoaderRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.OMEGA_OBJECTIVE, TileEntityOmegaObjectiveRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.SIGMA_OBJECTIVE, TileEntitySigmaObjectiveRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.TSAR_BOMB, TileEntityTsarBombaRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.FORCE_FIELD_NODE, TileEntityForceFieldNodeRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.GORE, TileEntityGoreRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.LAPTOP, TileEntityLaptopRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.RECIEVER, TileEntityRecieverRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.MELT_DOWN, TileEntityMeltdownRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.THEORETICAL_TSAR_BOMB, TileEntityTheoreticalTsarBombaRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.ANTIMATTER_BOMB, TileEntityAntimatterBombRenderer::new);
		BlockEntityRenderers.register(RRTileEntities.TACHYON_BOMB, TileEntityTachyonBombRenderer::new);
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
		Minecraft.getInstance().setScreen(null);
	}

	@Override
	public void nextBattle()
	{
		Minecraft.getInstance().setScreen(new GuiNextBattle());
	}

	@Override
	public void teamWin(boolean winner)
	{
		Minecraft.getInstance().setScreen(winner?new GuiOmegaWin():new GuiSigmaWin());
	}

	@Override
	public void guiClass()
	{
		Minecraft.getInstance().setScreen(new GuiClass(RivalRebels.round.rrplayerlist.getForGameProfile(Minecraft.getInstance().player.getGameProfile()).rrclass));
	}

	@Override
	public void guiSpawn()
	{
		Minecraft.getInstance().setScreen(new GuiSpawn(RivalRebels.round.rrplayerlist.getForGameProfile(Minecraft.getInstance().player.getGameProfile()).rrclass));
	}

	@Override
	public void flamethrowerGui(int i)
	{
		Minecraft.getInstance().setScreen(new GuiFlameThrower(i));
	}

	@Override
	public void teslaGui(int i)
	{
		Minecraft.getInstance().setScreen(new GuiTesla(i));
	}

	@Override
	public void spawnGore(Level world, EntityGore g, boolean greenblood)
	{
		Minecraft.getInstance().particleEngine.add(new EntityBloodFX((ClientLevel) world, g, !greenblood));
	}

	@Override
	public boolean spacebar()
	{
		return Minecraft.getInstance().options.keyJump.consumeClick() && Minecraft.getInstance().screen == null;
	}
	@Override
	public boolean w()
	{
		return Minecraft.getInstance().options.keyUp.consumeClick() && Minecraft.getInstance().screen == null;
	}
	@Override
	public boolean a()
	{
		return Minecraft.getInstance().options.keyLeft.consumeClick() && Minecraft.getInstance().screen == null;
	}
	@Override
	public boolean s()
	{
		return Minecraft.getInstance().options.keyDown.consumeClick() && Minecraft.getInstance().screen == null;
	}
	@Override
	public boolean d()
	{
		return Minecraft.getInstance().options.keyRight.consumeClick() && Minecraft.getInstance().screen == null;
	}
	@Override
	public boolean f()
	{
		return glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW_KEY_F) == GLFW_PRESS && Minecraft.getInstance().screen == null;
	}
	boolean prevc = false;
	public boolean c()
	{
		boolean isdown = glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW_KEY_C) == GLFW_PRESS && Minecraft.getInstance().screen == null;
		boolean x = !prevc && isdown;
		prevc = isdown;
		return x;
	}
	boolean prevx = false;
	@Override
	public boolean x()
	{
		boolean isdown = glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW_KEY_X) == GLFW_PRESS && Minecraft.getInstance().screen == null;
		boolean x = !prevx && isdown;
		prevx = isdown;
		return x;
	}
	@Override
	public boolean z()
	{
		return glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW_KEY_Z) == GLFW_PRESS && Minecraft.getInstance().screen == null;
	}
	public boolean g()
	{
		return glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW_KEY_G) == GLFW_PRESS && Minecraft.getInstance().screen == null;
	}

	@Override
	public void setOverlay(EntityRhodes rhodes)
	{
		if (rhodes.rider == Minecraft.getInstance().player)
		{
			RivalRebels.rrro.counter = 10;
			RivalRebels.rrro.rhodes = rhodes;
		}
	}
}
