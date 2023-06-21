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
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityGasGrenade.class, new RenderGasGrenade());
		RenderingRegistry.registerEntityRenderingHandler(EntityPropulsionFX.class, new RenderBullet("fire"));
		RenderingRegistry.registerEntityRenderingHandler(EntityPassiveFire.class, new RenderBullet("fire"));
		RenderingRegistry.registerEntityRenderingHandler(EntityCuchillo.class, new RenderCuchillo());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameBall.class, new RenderFlame());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameBall1.class, new RenderFlameRedBlue());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameBall2.class, new RenderFlameBlue());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket());
		RenderingRegistry.registerEntityRenderingHandler(EntityPlasmoid.class, new RenderPlasmoid());
		RenderingRegistry.registerEntityRenderingHandler(EntityLightningLink.class, new RenderLightningLink());
		RenderingRegistry.registerEntityRenderingHandler(EntityNuclearBlast.class, new RenderNuclearBlast());
		RenderingRegistry.registerEntityRenderingHandler(EntityLightningBolt2.class, new RenderLightningBolt2());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaptop.class, new RenderLaptop());
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskRegular.class, new RenderRoddiskRegular());
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskRebel.class, new RenderRoddiskRebel());
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskOfficer.class, new RenderRoddiskOfficer());
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskLeader.class, new RenderRoddiskLeader());
		RenderingRegistry.registerEntityRenderingHandler(EntityTsarBlast.class, new RenderTsarBlast());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserLink.class, new RenderLaserLink());
		RenderingRegistry.registerEntityRenderingHandler(EntityGore.class, new RenderGore());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlood.class, new RenderBlood());
		RenderingRegistry.registerEntityRenderingHandler(EntityGoo.class, new RenderGoo());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserBurst.class, new RenderLaserBurst());
		RenderingRegistry.registerEntityRenderingHandler(EntityB83.class, new RenderB83());
		RenderingRegistry.registerEntityRenderingHandler(EntityB2Spirit.class, new RenderB2Spirit());
		RenderingRegistry.registerEntityRenderingHandler(EntityB2Frag.class, new RenderB2Frag());
		RenderingRegistry.registerEntityRenderingHandler(EntityDebris.class, new RenderDebris());
		RenderingRegistry.registerEntityRenderingHandler(EntityHackB83.class, new RenderHackB83());
		RenderingRegistry.registerEntityRenderingHandler(EntitySeekB83.class, new RenderSeeker());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodes.class, new RenderRhodes());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesHead.class, new RenderRhodesHead());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesTorso.class, new RenderRhodesTorso());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesLeftUpperArm.class, new RenderRhodesLeftUpperArm());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesRightUpperArm.class, new RenderRhodesRightUpperArm());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesLeftLowerArm.class, new RenderRhodesLeftLowerArm());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesRightLowerArm.class, new RenderRhodesRightLowerArm());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesLeftUpperLeg.class, new RenderRhodesLeftUpperLeg());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesRightUpperLeg.class, new RenderRhodesRightUpperLeg());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesLeftLowerLeg.class, new RenderRhodesLeftLowerLeg());
		RenderingRegistry.registerEntityRenderingHandler(EntityRhodesRightLowerLeg.class, new RenderRhodesRightLowerLeg());
		RenderingRegistry.registerEntityRenderingHandler(EntityB83NoShroom.class, new RenderB83());
		RenderingRegistry.registerEntityRenderingHandler(EntitySphereBlast.class, new RenderSphereBlast());
		RenderingRegistry.registerEntityRenderingHandler(EntityNuke.class, new RenderNuke());
		RenderingRegistry.registerEntityRenderingHandler(EntityTsar.class, new RenderTsar());
		RenderingRegistry.registerEntityRenderingHandler(EntityRoddiskRep.class, new RenderRoddiskRep());
		RenderingRegistry.registerEntityRenderingHandler(EntityHotPotato.class, new RenderHotPotato());
		RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBomb());
		RenderingRegistry.registerEntityRenderingHandler(EntityTheoreticalTsar.class, new RenderTheoreticalTsar());
		RenderingRegistry.registerEntityRenderingHandler(EntityTheoreticalTsarBlast.class, new RenderTheoreticalTsarBlast());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameBallGreen.class, new RenderFlameBallGreen());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntimatterBomb.class, new RenderAntimatterBomb());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntimatterBombBlast.class, new RenderAntimatterBombBlast());
		RenderingRegistry.registerEntityRenderingHandler(EntityTachyonBomb.class, new RenderTachyonBomb());
		RenderingRegistry.registerEntityRenderingHandler(EntityTachyonBombBlast.class, new RenderTachyonBombBlast());
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
		Minecraft.getMinecraft().displayGuiScreen(new GuiClass(RivalRebels.round.rrplayerlist.getForName(Minecraft.getMinecraft().thePlayer.getCommandSenderName()).rrclass));
	}

	@Override
	public void guiSpawn()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiSpawn(RivalRebels.round.rrplayerlist.getForName(Minecraft.getMinecraft().thePlayer.getCommandSenderName()).rrclass));
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
