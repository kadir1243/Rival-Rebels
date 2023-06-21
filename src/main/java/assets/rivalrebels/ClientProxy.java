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

import assets.rivalrebels.client.blockrenderer.SteelBlockRenderer;
import assets.rivalrebels.client.gui.*;
import assets.rivalrebels.client.renderentity.*;
import assets.rivalrebels.client.tileentityrender.*;
import assets.rivalrebels.common.entity.*;
import assets.rivalrebels.common.tileentity.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy
{

	@SideOnly(Side.CLIENT)
	public static void registerRenderInformation()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeCrate.class, new TileEntityNukeCrateRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNuclearBomb.class, new TileEntityNuclearBombRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlasmaExplosion.class, new TileEntityPlasmaExplosionRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReactor.class, new TileEntityReactorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJumpBlock.class, new TileEntityJumpBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLoader.class, new TileEntityLoaderRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOmegaObjective.class, new TileEntityOmegaObjectiveRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySigmaObjective.class, new TileEntitySigmaObjectiveRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTsarBomba.class, new TileEntityTsarBombaRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForceFieldNode.class, new TileEntityForceFieldNodeRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGore.class, new TileEntityGoreRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaptop.class, new TileEntityLaptopRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReciever.class, new TileEntityRecieverRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMeltDown.class, new TileEntityMeltdownRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTheoreticalTsarBomba.class, new TileEntityTheoreticalTsarBombaRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAntimatterBomb.class, new TileEntityAntimatterBombRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTachyonBomb.class, new TileEntityTachyonBombRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityGasGrenade.class, RenderGasGrenade::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPropulsionFX.class, manager -> new RenderBullet(manager, "fire"));
		RenderingRegistry.registerEntityRenderingHandler(EntityPassiveFire.class, manager -> new RenderBullet(manager, "fire"));
		RenderingRegistry.registerEntityRenderingHandler(EntityCuchillo.class, RenderCuchillo::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameBall.class, RenderFlame::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameBall1.class, RenderFlameRedBlue::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameBall2.class, RenderFlameBlue::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, RenderRocket::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPlasmoid.class, RenderPlasmoid::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLightningLink.class, RenderLightningLink::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityNuclearBlast.class, RenderNuclearBlast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLaptop.class, RenderLaptop::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskRegular.class, RenderRoddiskRegular::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskRebel.class, RenderRoddiskRebel::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskOfficer.class, RenderRoddiskOfficer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskLeader.class, RenderRoddiskLeader::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTsarBlast.class, RenderTsarBlast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserLink.class, RenderLaserLink::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGore.class, RenderGore::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlood.class, RenderBlood::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGoo.class, RenderGoo::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserBurst.class, RenderLaserBurst::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityB83.class, RenderB83::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityB2Spirit.class, RenderB2Spirit::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityB2Frag.class, RenderB2Frag::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDebris.class, RenderDebris::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityHackB83.class, RenderHackB83::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySeekB83.class, RenderSeeker::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodes.class, RenderRhodes::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesHead.class, RenderRhodesHead::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesTorso.class, RenderRhodesTorso::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesLeftUpperArm.class, RenderRhodesLeftUpperArm::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesRightUpperArm.class, RenderRhodesRightUpperArm::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesLeftLowerArm.class, RenderRhodesLeftLowerArm::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesRightLowerArm.class, RenderRhodesRightLowerArm::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesLeftUpperLeg.class, RenderRhodesLeftUpperLeg::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesRightUpperLeg.class, RenderRhodesRightUpperLeg::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesLeftLowerLeg.class, RenderRhodesLeftLowerLeg::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesRightLowerLeg.class, RenderRhodesRightLowerLeg::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityB83NoShroom.class, RenderB83::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySphereBlast.class, RenderSphereBlast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityNuke.class, RenderNuke::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTsar.class, RenderTsar::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskRep.class, RenderRoddiskRep::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityHotPotato.class, RenderHotPotato::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, RenderBomb::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTheoreticalTsar.class, RenderTheoreticalTsar::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTheoreticalTsarBlast.class, RenderTheoreticalTsarBlast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameBallGreen.class, RenderFlameBallGreen::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntimatterBomb.class, RenderAntimatterBomb::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntimatterBombBlast.class, RenderAntimatterBombBlast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTachyonBomb.class, RenderTachyonBomb::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTachyonBombBlast.class, RenderTachyonBombBlast::new);
		RenderingRegistry.registerBlockHandler(new SteelBlockRenderer());
	}

	@Override
	public int addArmor(String armor)
	{
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}

	@Override
	public void closeGui()
	{
		Minecraft.getMinecraft().displayGuiScreen(null);
	}

	@Override
	public void nextBattle()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiNextBattle());
	}

	@Override
	public void teamWin(boolean winner)
	{
		Minecraft.getMinecraft().displayGuiScreen(winner?new GuiOmegaWin():new GuiSigmaWin());
	}

	@Override
	public void guiClass()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiClass(RivalRebels.round.rrplayerlist.getForName(Minecraft.getMinecraft().thePlayer.getName()).rrclass));
	}

	@Override
	public void guiSpawn()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiSpawn(RivalRebels.round.rrplayerlist.getForName(Minecraft.getMinecraft().thePlayer.getName()).rrclass));
	}

	@Override
	public void flamethrowerGui(int i)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiFlameThrower(i));
	}

	@Override
	public void teslaGui(int i)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiTesla(i));
	}

	@Override
	public void spawnGore(World world, EntityGore g, boolean greenblood)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBloodFX(world, g, !greenblood));
	}

	@Override
	public boolean spacebar()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Minecraft.getMinecraft().currentScreen == null;
	}
	@Override
	public boolean w()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_W) && Minecraft.getMinecraft().currentScreen == null;
	}
	@Override
	public boolean a()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_A) && Minecraft.getMinecraft().currentScreen == null;
	}
	@Override
	public boolean s()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_X) && Minecraft.getMinecraft().currentScreen == null;
	}
	@Override
	public boolean d()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_D) && Minecraft.getMinecraft().currentScreen == null;
	}
	@Override
	public boolean f()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_F) && Minecraft.getMinecraft().currentScreen == null;
	}
	boolean prevc = false;
	public boolean c()
	{
		boolean isdown = Keyboard.isKeyDown(Keyboard.KEY_C) && Minecraft.getMinecraft().currentScreen == null;
		boolean x = !prevc && isdown;
		prevc = isdown;
		return x;
	}
	boolean prevx = false;
	@Override
	public boolean x()
	{
		boolean isdown = Keyboard.isKeyDown(Keyboard.KEY_S) && Minecraft.getMinecraft().currentScreen == null;
		boolean x = !prevx && isdown;
		prevx = isdown;
		return x;
	}
	@Override
	public boolean z()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_Z) && Minecraft.getMinecraft().currentScreen == null;
	}
	public boolean g()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_G) && Minecraft.getMinecraft().currentScreen == null;
	}


	@Override
	public void setOverlay(EntityRhodes rhodes)
	{
		if (rhodes.rider == Minecraft.getMinecraft().thePlayer)
		{
			RivalRebels.rrro.counter = 10;
			RivalRebels.rrro.rhodes = rhodes;
		}
	}
}
