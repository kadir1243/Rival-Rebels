package io.github.kadir1243.rivalrebels.client.renderhelper;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterPictureInPictureRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

import java.util.Optional;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class RRRenderTypes {
    public static final RenderPipeline LIGHTNING_ASTRO_BLAST_PIPELINE =
        RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withVertexShader("core/entity")
            .withFragmentShader("core/entity")
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withLocation(RRIdentifiers.create("pipeline/lightning_astro_blast_pipeline"))
            .build();
    public static final RenderPipeline COLOR_WRITE_TRI =
        RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withVertexShader("core/rendertype_lightning")
            .withFragmentShader("core/rendertype_lightning")
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.TRIANGLE_FAN)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withLocation(RRIdentifiers.create("pipeline/color_write_tri"))
            .build();
    public static final RenderType LIGHTNING_ASTRO_BLAST = RenderType.create(
        RRIdentifiers.MODID+"_lightning_astro_blast",
        RenderSetup.builder(LIGHTNING_ASTRO_BLAST_PIPELINE).createRenderSetup()
    );
    public static final RenderType LIGHTNING_ASTRO_BLAST_TRIANGLES = RenderType.create(
        RRIdentifiers.MODID+"_lightning_astro_blast_triangles",
        RenderSetup.builder(COLOR_WRITE_TRI).createRenderSetup()
    );
    public static final RenderPipeline CELLULAR_NOISE_PIPELINE = RenderPipeline.builder()
        .withVertexShader("core/entity")
        .withFragmentShader("core/entity")
        .withLocation(RRIdentifiers.create("pipeline/cellular_noise"))
        .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
        .build();
    public static final RenderType CELLULAR_NOISE = RenderType.create(
        RRIdentifiers.MODID + "_cellular_noise",
        RenderSetup.builder(CELLULAR_NOISE_PIPELINE).createRenderSetup()
    );
    public static final RenderPipeline LASER_PIPELINE = RenderPipeline.builder()
        .withVertexShader("core/entity")
        .withFragmentShader("core/entity")
        .withLocation(RRIdentifiers.create("pipeline/laser"))
        .withCull(true)
        .withColorTargetState(new ColorTargetState(BlendFunction.ADDITIVE))
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .build();
    public static final RenderPipeline RHODES_LASER_PIPELINE = RenderPipeline.builder()
        .withVertexShader("core/entity")
        .withFragmentShader("core/entity")
        .withLocation(RRIdentifiers.create("pipeline/rhodes_laser"))
        .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
        .withColorTargetState(new ColorTargetState(Optional.of(BlendFunction.ADDITIVE), GpuFormat.RGBA8_UNORM, ColorTargetState.WRITE_NONE))
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))
        .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
        .build();
    public static final RenderType LASER_RENDER_TYPE = RenderType.create(RRIdentifiers.MODID+"_laser_render_type",
        RenderSetup.builder(LASER_PIPELINE).useLightmap().useOverlay().createRenderSetup()
    );
    public static final RenderType RHODES_LIGHTNING = RenderType.create(RRIdentifiers.MODID+"_rhodes_lightning",
        RenderSetup.builder(LASER_PIPELINE).createRenderSetup()
    );
    public static final RenderPipeline ENTITY_SOLID_TRIANGLES =
        RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withVertexBinding(0, DefaultVertexFormat.ENTITY)
            .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
            .withLocation(RRIdentifiers.create("pipeline/entity_solid_tri"))
            .build();
    public static final VertexFormat POSITION_COLOR_LIGHTMAP_NORMAL = VertexFormat.builder(0)
        .addAttribute("Position", GpuFormat.RGB32_FLOAT)
        .addAttribute("Color", GpuFormat.RGBA8_UNORM)
        .addAttribute("UV2", GpuFormat.RG16_SINT)
        .addAttribute("Normal", GpuFormat.RGBA8_SNORM)
        .build();
    public static final RenderPipeline BLAST_SPHERE_PIPELINE =
        RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withVertexShader("core/entity")
            .withFragmentShader("core/entity")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withVertexBinding(0, POSITION_COLOR_LIGHTMAP_NORMAL)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withLocation(RRIdentifiers.create("pipeline/blast_sphere"))
            .build();
    public static final RenderType MODEL_BLAST_SPHERE = RenderType.create(
        RRIdentifiers.MODID +"_model_blast_sphere",
        RenderSetup.builder(BLAST_SPHERE_PIPELINE).useLightmap().useOverlay().createRenderSetup()
    );
    public static final Function<Identifier, RenderType> RENDER_SOLID_TRIANGLES = Util.memoize(
        resourceLocation -> {
            RenderSetup rendersetup = RenderSetup.builder(ENTITY_SOLID_TRIANGLES)
                .withTexture("Sampler0", resourceLocation)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();
            return RenderType.create(RRIdentifiers.MODID + "_render_solid_triangles", rendersetup);
        }
    );
    public static final RenderPipeline LASER_LINK_PIPELINE = RenderPipeline.builder()
        .withVertexShader("core/entity")
        .withFragmentShader("core/entity")
        .withLocation(RRIdentifiers.create("pipeline/laser_link"))
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(new ColorTargetState(Optional.of(BlendFunction.ADDITIVE), GpuFormat.RGBA8_UNORM, ColorTargetState.WRITE_NONE))
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
        .build();
    public static final RenderType LASER_LINK_ENTITY = RenderType.create(
        RRIdentifiers.MODID +"_laser_link_entity",
        RenderSetup.builder(LASER_LINK_PIPELINE).createRenderSetup()
    );
    public static final RenderPipeline ANTIMATTER_BOMB_BLAST_ENTITY_PIPELINE = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
        .withLocation(RRIdentifiers.create("pipeline/antimatter_bomb_blast_entity"))
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
        .withColorTargetState(new ColorTargetState(BlendFunction.ADDITIVE))
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
        .build();
    public static final RenderType ANTIMATTER_BOMB_BLAST_ENTITY = RenderType.create(
        RRIdentifiers.MODID +"_antimatter_bomb_blast_entity",
        RenderSetup.builder(ANTIMATTER_BOMB_BLAST_ENTITY_PIPELINE).createRenderSetup()
    );
    public static final RenderPipeline LIGHTNING_LINK_PIPELINE = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
        .withLocation(RRIdentifiers.create("pipeline/lightning_link"))
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(new ColorTargetState(BlendFunction.LIGHTNING))
        .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
        .build();
    public static final RenderType LIGHTNING_LINK = RenderType.create(
        RRIdentifiers.MODID + "_lightning_link",
        RenderSetup.builder(LIGHTNING_LINK_PIPELINE).createRenderSetup()
    );

    public static void registerRenderPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(LIGHTNING_LINK_PIPELINE);
        event.registerPipeline(LIGHTNING_ASTRO_BLAST_PIPELINE);
        event.registerPipeline(COLOR_WRITE_TRI);
        event.registerPipeline(CELLULAR_NOISE_PIPELINE);
        event.registerPipeline(ANTIMATTER_BOMB_BLAST_ENTITY_PIPELINE);
        event.registerPipeline(LASER_LINK_PIPELINE);
        event.registerPipeline(LASER_PIPELINE);
        event.registerPipeline(ENTITY_SOLID_TRIANGLES);
        event.registerPipeline(BLAST_SPHERE_PIPELINE);
    }

    public static void registerPIPRenderer(RegisterPictureInPictureRenderersEvent event) {
        event.register(TrayModelPIPRenderState.class, () -> new GuiTrayModelRenderer(Minecraft.getInstance().getModelManager()));
    }
}
