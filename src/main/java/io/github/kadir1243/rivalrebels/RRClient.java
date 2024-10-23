package io.github.kadir1243.rivalrebels;

import io.github.kadir1243.rivalrebels.client.gui.RivalRebelsRenderOverlay;
import io.github.kadir1243.rivalrebels.client.itemrenders.*;
import io.github.kadir1243.rivalrebels.client.model.RRModelLoadingPlugin;
import io.github.kadir1243.rivalrebels.client.renderentity.*;
import io.github.kadir1243.rivalrebels.client.tileentityrender.*;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.entity.EntityRaytrace;
import io.github.kadir1243.rivalrebels.common.entity.RREntities;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.tileentity.RRTileEntities;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.util.function.Supplier;

import static org.lwjgl.glfw.GLFW.*;

@OnlyIn(Dist.CLIENT)
public class RRClient {
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
    public static RivalRebelsRenderOverlay rrro;

    private static KeyMapping createRhodesKey(String translation, int key) {
        return new KeyMapping("key." + RRIdentifiers.MODID + ".rhodes." + "." + translation, key, "key." + RRIdentifiers.MODID + ".rhodes");
    }

    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(USE_KEY);
        event.register(TARGET_KEY);
        event.register(RHODES_GUARD_KEY);
        event.register(RHODES_ROCKET_KEY);
        event.register(RHODES_LASER_KEY);
        event.register(RHODES_FIRE_KEY);
        event.register(RHODES_FORCE_FIELD_KEY);
        event.register(RHODES_PLASMA_KEY);
        event.register(RHODES_NUKE_KEY);
        event.register(RHODES_STOP_KEY);
        event.register(RHODES_B2SPIRIT_KEY);
        event.register(USE_BINOCULARS_ITEM);
    }

	public static void registerRenderInformation(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(RRTileEntities.NUKE_CRATE.get(), TileEntityNukeCrateRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.NUCLEAR_BOMB.get(), TileEntityNuclearBombRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.PLASMA_EXPLOSION.get(), TileEntityPlasmaExplosionRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.REACTOR.get(), TileEntityReactorRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.JUMP_BLOCK.get(), TileEntityJumpBlockRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.LOADER.get(), TileEntityLoaderRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.OMEGA_OBJECTIVE.get(), OmegaObjectiveBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.SIGMA_OBJECTIVE.get(), SigmaObjectiveBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.TSAR_BOMB.get(), TileEntityTsarBombaRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.FORCE_FIELD_NODE.get(), TileEntityForceFieldNodeRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.GORE.get(), TileEntityGoreRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.LAPTOP.get(), TileEntityLaptopRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.RECIEVER.get(), TileEntityRecieverRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.MELT_DOWN.get(), TileEntityMeltdownRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.THEORETICAL_TSAR_BOMB.get(), TileEntityTheoreticalTsarBombaRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.ANTIMATTER_BOMB.get(), TileEntityAntimatterBombRenderer::new);
		event.registerBlockEntityRenderer(RRTileEntities.TACHYON_BOMB.get(), TileEntityTachyonBombRenderer::new);
		event.registerEntityRenderer(RREntities.GAS_GRENADE.get(), RenderGasGrenade::new);
		event.registerEntityRenderer(RREntities.PROPULSION_FX.get(), manager -> new RenderBullet(manager, "fire"));
		event.registerEntityRenderer(RREntities.PASSIVE_FIRE.get(), manager -> new RenderBullet(manager, "fire"));
		event.registerEntityRenderer(RREntities.CUCHILLO.get(), RenderCuchillo::new);
		event.registerEntityRenderer(RREntities.FLAME_BALL.get(), RenderFlame::new);
		event.registerEntityRenderer(RREntities.FLAME_BALL1.get(), RenderFlameRedBlue::new);
		event.registerEntityRenderer(RREntities.FLAME_BALL2.get(), RenderFlameBlue::new);
		event.registerEntityRenderer(RREntities.ROCKET.get(), RenderRocket::new);
		event.registerEntityRenderer(RREntities.PLASMOID.get(), RenderPlasmoid::new);
		event.registerEntityRenderer(RREntities.LIGHTNING_LINK.get(), RenderLightningLink::new);
		event.registerEntityRenderer(RREntities.NUCLEAR_BLAST.get(), RenderNuclearBlast::new);
		event.registerEntityRenderer(RREntities.LAPTOP.get(), RenderLaptop::new);
		event.registerEntityRenderer(RREntities.RODDISK_REGULAR.get(), RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.RODDISK_REBEL.get(), RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.RODDISK_OFFICER.get(), RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.RODDISK_LEADER.get(), RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.TSAR_BLAST.get(), RenderTsarBlast::new);
		event.registerEntityRenderer(RREntities.LASER_LINK.get(), RenderLaserLink::new);
		event.registerEntityRenderer(RREntities.GORE.get(), RenderGore::new);
		event.registerEntityRenderer(RREntities.BLOOD.get(), RenderBlood::new);
		event.registerEntityRenderer(RREntities.GOO.get(), RenderGoo::new);
		event.registerEntityRenderer(RREntities.LASER_BURST.get(), RenderLaserBurst::new);
		event.registerEntityRenderer(RREntities.B83.get(), RenderB83::new);
		event.registerEntityRenderer(RREntities.B2SPIRIT.get(), RenderB2Spirit::new);
		event.registerEntityRenderer(RREntities.B2FRAG.get(), RenderB2Frag::new);
		event.registerEntityRenderer(RREntities.DEBRIS.get(), RenderDebris::new);
		event.registerEntityRenderer(RREntities.HACK_B83.get(), RenderHackB83::new);
		event.registerEntityRenderer(RREntities.SEEK_B83.get(), RenderSeeker::new);
		event.registerEntityRenderer(RREntities.RHODES.get(), RenderRhodes::new);
		event.registerEntityRenderer(RREntities.RHODES_HEAD.get(), RenderRhodesHead::new);
		event.registerEntityRenderer(RREntities.RHODES_TORSO.get(), RenderRhodesTorso::new);
		event.registerEntityRenderer(RREntities.RHODES_LEFT_UPPER_ARM.get(), RenderRhodesLeftUpperArm::new);
		event.registerEntityRenderer(RREntities.RHODES_RIGHT_UPPER_ARM.get(), RenderRhodesRightUpperArm::new);
		event.registerEntityRenderer(RREntities.RHODES_LEFT_LOWER_ARM.get(), RenderRhodesLeftLowerArm::new);
		event.registerEntityRenderer(RREntities.RHODES_RIGHT_LOWER_ARM.get(), RenderRhodesRightLowerArm::new);
		event.registerEntityRenderer(RREntities.RHODES_LEFT_UPPER_LEG.get(), RenderRhodesLeftUpperLeg::new);
		event.registerEntityRenderer(RREntities.RHODES_RIGHT_UPPER_LEG.get(), RenderRhodesRightUpperLeg::new);
		event.registerEntityRenderer(RREntities.RHODES_LEFT_LOWER_LEG.get(), RenderRhodesLeftLowerLeg::new);
		event.registerEntityRenderer(RREntities.RHODES_RIGHT_LOWER_LEG.get(), RenderRhodesRightLowerLeg::new);
		event.registerEntityRenderer(RREntities.B83_NO_SHROOM.get(), RenderB83::new);
		event.registerEntityRenderer(RREntities.SPHERE_BLAST.get(), RenderSphereBlast::new);
		event.registerEntityRenderer(RREntities.NUKE.get(), RenderNuke::new);
		event.registerEntityRenderer(RREntities.TSAR.get(), RenderTsar::new);
		event.registerEntityRenderer(RREntities.RODDISK_REP.get(), RoddiskRenderer::new);
		event.registerEntityRenderer(RREntities.HOT_POTATO.get(), RenderHotPotato::new);
		event.registerEntityRenderer(RREntities.BOMB.get(), RenderBomb::new);
		event.registerEntityRenderer(RREntities.THEORETICAL_TSAR.get(), RenderTheoreticalTsar::new);
		event.registerEntityRenderer(RREntities.THEORETICAL_TSAR_BLAST.get(), RenderTheoreticalTsarBlast::new);
		event.registerEntityRenderer(RREntities.FLAME_BALL_GREEN.get(), RenderFlameBallGreen::new);
		event.registerEntityRenderer(RREntities.ANTIMATTER_BOMB.get(), RenderAntimatterBomb::new);
		event.registerEntityRenderer(RREntities.ANTIMATTER_BOMB_BLAST.get(), RenderAntimatterBombBlast::new);
		event.registerEntityRenderer(RREntities.TACHYON_BOMB.get(), RenderTachyonBomb::new);
		event.registerEntityRenderer(RREntities.TACHYON_BOMB_BLAST.get(), RenderTachyonBombBlast::new);
        event.registerEntityRenderer(RREntities.RAYTRACE.get(), context -> new EntityRenderer<>(context) {
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

    private static void addItemRenderer(RegisterClientExtensionsEvent event, Holder<Item> item, Supplier<DynamicItemRenderer> renderer) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new DynamicItemRendererWrapper(renderer);
            }
        }, item);
    }

    private static void registerCustomRenderers(RegisterClientExtensionsEvent event) {
        addItemRenderer(event, RRItems.NUCLEAR_ROD, NuclearRodRenderer::new);
        addItemRenderer(event, RRItems.tesla, TeslaRenderer::new);
        addItemRenderer(event, RRItems.einsten, AstroBlasterRenderer::new);
        addItemRenderer(event, RRItems.battery, BatteryRenderer::new);
        addItemRenderer(event, RRItems.binoculars, BinocularsRenderer::new);
        addItemRenderer(event, RRItems.emptyrod, EmptyRodRenderer::new);
        addItemRenderer(event, RRItems.flamethrower, FlamethrowerRenderer::new);
        addItemRenderer(event, RRItems.fuel, GasRenderer::new);
        addItemRenderer(event, RRItems.hackm202, HackRocketLauncherRenderer::new);
        addItemRenderer(event, RRItems.hydrod, HydrogenRodRenderer::new);
        addItemRenderer(event, Holder.direct(RRBlocks.controller.asItem()), LaptopRenderer::new);
        addItemRenderer(event, Holder.direct(RRBlocks.loader.asItem()), LoaderRenderer::new);
        addItemRenderer(event, RRItems.plasmacannon, PlasmaCannonRenderer::new);
        addItemRenderer(event, Holder.direct(RRBlocks.reactor.asItem()), ReactorRenderer::new);
        addItemRenderer(event, RRItems.redrod, RedstoneRodRenderer::new);
        addItemRenderer(event, RRItems.rpg, RocketLauncherRenderer::new);
        addItemRenderer(event, RRItems.rocket, RocketRenderer::new);
        addItemRenderer(event, RRItems.roda, RodaRenderer::new);
        addItemRenderer(event, RRItems.roddisk, RodDiskRenderer::new);
        addItemRenderer(event, RRItems.seekm202, SeekRocketLauncherRenderer::new);
    }

    public static void init(IEventBus bus) {
        rrro = new RivalRebelsRenderOverlay();
        rrro.init(bus);
        bus.addListener(RRClient::registerRenderInformation);
        bus.addListener(RRClient::registerKeyBinding);
        bus.addListener(RRClient::registerCustomRenderers);
        RRModelLoadingPlugin.init(bus);
    }

    @OnlyIn(Dist.CLIENT)
    private static class DynamicItemRendererWrapper extends BlockEntityWithoutLevelRenderer {
        private final DynamicItemRenderer itemRenderer;

        public DynamicItemRendererWrapper(Supplier<DynamicItemRenderer> renderer) {
            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
            itemRenderer = renderer.get();
        }

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
            itemRenderer.render(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
