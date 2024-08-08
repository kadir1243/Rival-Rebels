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

import assets.rivalrebels.client.itemrenders.*;
import assets.rivalrebels.client.renderentity.*;
import assets.rivalrebels.client.tileentityrender.*;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.entity.*;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.tileentity.RRTileEntities;
import com.google.common.base.Supplier;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import static org.lwjgl.glfw.GLFW.*;

@Environment(EnvType.CLIENT)
public class ClientProxy {
    public static final KeyMapping USE_KEY = new KeyMapping("key." + RRIdentifiers.MODID + ".use", GLFW_KEY_R, "key." + RRIdentifiers.MODID);
    public static final KeyMapping TARGET_KEY = new KeyMapping("key." + RRIdentifiers.MODID + ".target", InputConstants.Type.MOUSE, GLFW_MOUSE_BUTTON_LEFT, "key." + RRIdentifiers.MODID);
    public static final KeyMapping RHODES_JUMP_KEY = createRhodesKey("jump", GLFW_KEY_SPACE);
    public static final KeyMapping RHODES_ROCKET_KEY = createRhodesKey("rocket", GLFW_KEY_A);
    public static final KeyMapping RHODES_LASER_KEY = createRhodesKey("laser", GLFW_KEY_W);
    public static final KeyMapping RHODES_FIRE_KEY = createRhodesKey("fire", GLFW_KEY_D);
    public static final KeyMapping RHODES_FORCE_FIELD_KEY = createRhodesKey("force_field", GLFW_KEY_C);
    public static final KeyMapping RHODES_PLASMA_KEY = createRhodesKey("plasma", GLFW_KEY_F);
    public static final KeyMapping RHODES_NUKE_KEY = createRhodesKey("nuke", GLFW_KEY_S);
    public static final KeyMapping RHODES_STOP_KEY = createRhodesKey("stop", GLFW_KEY_X);
    public static final KeyMapping RHODES_B2SPIRIT_KEY = createRhodesKey("b2spirit", GLFW_KEY_Z);
    public static final KeyMapping RHODES_GUARD_KEY = createRhodesKey("guard", GLFW_KEY_G);
    public static final KeyMapping USE_BINOCULARS_ITEM = new KeyMapping("key." + RRIdentifiers.MODID + ".use_binoculars", GLFW_KEY_C, "key." + RRIdentifiers.MODID);

    private static KeyMapping createRhodesKey(String translation, int key) {
        return new KeyMapping("key." + RRIdentifiers.MODID + ".rhodes." + "." + translation, key, "key." + RRIdentifiers.MODID + ".rhodes");
    }

    public static void registerKeyBinding() {
        KeyBindingHelper.registerKeyBinding(USE_KEY);
        KeyBindingHelper.registerKeyBinding(TARGET_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_GUARD_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_ROCKET_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_LASER_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_FIRE_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_FORCE_FIELD_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_PLASMA_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_NUKE_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_STOP_KEY);
        KeyBindingHelper.registerKeyBinding(RHODES_B2SPIRIT_KEY);
        KeyBindingHelper.registerKeyBinding(USE_BINOCULARS_ITEM);
    }

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
		EntityRendererRegistry.register(RREntities.RODDISK_REP, RoddiskRenderer::new);
		EntityRendererRegistry.register(RREntities.HOT_POTATO, RenderHotPotato::new);
		EntityRendererRegistry.register(RREntities.BOMB, RenderBomb::new);
		EntityRendererRegistry.register(RREntities.THEORETICAL_TSAR, RenderTheoreticalTsar::new);
		EntityRendererRegistry.register(RREntities.THEORETICAL_TSAR_BLAST, RenderTheoreticalTsarBlast::new);
		EntityRendererRegistry.register(RREntities.FLAME_BALL_GREEN, RenderFlameBallGreen::new);
		EntityRendererRegistry.register(RREntities.ANTIMATTER_BOMB, RenderAntimatterBomb::new);
		EntityRendererRegistry.register(RREntities.ANTIMATTER_BOMB_BLAST, RenderAntimatterBombBlast::new);
		EntityRendererRegistry.register(RREntities.TACHYON_BOMB, RenderTachyonBomb::new);
		EntityRendererRegistry.register(RREntities.TACHYON_BOMB_BLAST, RenderTachyonBombBlast::new);
        EntityRendererRegistry.register(RREntities.RAYTRACE, context -> new EntityRenderer<>(context) {
            @Override
            public ResourceLocation getTextureLocation(EntityRaytrace entity) {
                return null;
            }

            @Override
            public boolean shouldRender(EntityRaytrace livingEntity, Frustum camera, double camX, double camY, double camZ) {
                return false;
            }
        });
	}

    private static void addItemRenderer(ItemLike item, Supplier<DynamicItemRenderer> renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item, new DynamicItemRendererWrapper(renderer));
    }

    static void registerCustomRenderers() {
        addItemRenderer(RRItems.NUCLEAR_ROD, NuclearRodRenderer::new);
        addItemRenderer(RRItems.tesla, TeslaRenderer::new);
        addItemRenderer(RRItems.einsten, AstroBlasterRenderer::new);
        addItemRenderer(RRItems.battery, BatteryRenderer::new);
        addItemRenderer(RRItems.binoculars, BinocularsRenderer::new);
        addItemRenderer(RRItems.emptyrod, EmptyRodRenderer::new);
        addItemRenderer(RRItems.flamethrower, FlamethrowerRenderer::new);
        addItemRenderer(RRItems.fuel, GasRenderer::new);
        addItemRenderer(RRItems.hackm202, HackRocketLauncherRenderer::new);
        addItemRenderer(RRItems.hydrod, HydrogenRodRenderer::new);
        addItemRenderer(RRBlocks.controller, LaptopRenderer::new);
        addItemRenderer(RRBlocks.loader, LoaderRenderer::new);
        addItemRenderer(RRItems.plasmacannon, PlasmaCannonRenderer::new);
        addItemRenderer(RRBlocks.reactor, ReactorRenderer::new);
        addItemRenderer(RRItems.redrod, RedstoneRodRenderer::new);
        addItemRenderer(RRItems.rpg, RocketLauncherRenderer::new);
        addItemRenderer(RRItems.rocket, RocketRenderer::new);
        addItemRenderer(RRItems.roda, RodaRenderer::new);
        addItemRenderer(RRItems.roddisk, RodDiskRenderer::new);
        addItemRenderer(RRItems.seekm202, SeekRocketLauncherRenderer::new);
    }

    @Environment(EnvType.CLIENT)
    private static class DynamicItemRendererWrapper implements BuiltinItemRendererRegistry.DynamicItemRenderer {
        private final DynamicItemRenderer itemRenderer;

        public DynamicItemRendererWrapper(Supplier<DynamicItemRenderer> renderer) {
            itemRenderer = renderer.get();
        }

        @Override
        public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
            itemRenderer.render(stack, mode, matrices, vertexConsumers, light, overlay);
        }
    }
}
