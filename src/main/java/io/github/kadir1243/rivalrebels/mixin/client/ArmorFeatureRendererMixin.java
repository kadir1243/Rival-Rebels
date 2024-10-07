package io.github.kadir1243.rivalrebels.mixin.client;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(HumanoidArmorLayer.class)
@OnlyIn(Dist.CLIENT)
public abstract class ArmorFeatureRendererMixin {
    @Shadow
    protected abstract void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, HumanoidModel<LivingEntity> model, int dyeColor, ResourceLocation textureLocation);

    @Redirect(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArmorMaterial;layers()Ljava/util/List;"))
    private List<ArmorMaterial.Layer> getArmorTexture(ArmorMaterial instance, PoseStack poseStack, MultiBufferSource bufferSource, LivingEntity livingEntity, EquipmentSlot slot, int packedLight, HumanoidModel<LivingEntity> model, @Local ItemStack stack) {
        String name;
        if (stack.is(RRItems.camohat) || stack.is(RRItems.camoshirt) || stack.is(RRItems.camoshoes))
            name = "textures/armors/c.png";
        else if (stack.is(RRItems.camopants))
            name = "textures/armors/d.png";
        else if (stack.is(RRItems.camohat2) || stack.is(RRItems.camoshirt2) || stack.is(RRItems.camoshoes2))
            name = "textures/armors/a.png";
        else if (stack.is(RRItems.camopants2))
            name = "textures/armors/b.png";
        else if (stack.is(RRItems.orebelhelmet)) name = "textures/armors/l.png";
        else if (stack.is(RRItems.orebelchest)) name = "textures/armors/l.png";
        else if (stack.is(RRItems.orebelpants)) name = "textures/armors/k.png";
        else if (stack.is(RRItems.orebelboots)) name = "textures/armors/l.png";
        else if (stack.is(RRItems.onukerhelmet)) name = "textures/armors/i.png";
        else if (stack.is(RRItems.onukerchest)) name = "textures/armors/i.png";
        else if (stack.is(RRItems.onukerpants)) name = "textures/armors/k.png";
        else if (stack.is(RRItems.onukerboots)) name = "textures/armors/i.png";
        else if (stack.is(RRItems.ointelhelmet)) name = "textures/armors/g.png";
        else if (stack.is(RRItems.ointelchest)) name = "textures/armors/g.png";
        else if (stack.is(RRItems.ointelpants)) name = "textures/armors/k.png";
        else if (stack.is(RRItems.ointelboots)) name = "textures/armors/g.png";
        else if (stack.is(RRItems.ohackerhelmet)) name = "textures/armors/e.png";
        else if (stack.is(RRItems.ohackerchest)) name = "textures/armors/e.png";
        else if (stack.is(RRItems.ohackerpants)) name = "textures/armors/k.png";
        else if (stack.is(RRItems.ohackerboots)) name = "textures/armors/e.png";
        else if (stack.is(RRItems.srebelhelmet)) name = "textures/armors/m.png";
        else if (stack.is(RRItems.srebelchest)) name = "textures/armors/m.png";
        else if (stack.is(RRItems.srebelpants)) name = "textures/armors/n.png";
        else if (stack.is(RRItems.srebelboots)) name = "textures/armors/m.png";
        else if (stack.is(RRItems.snukerhelmet)) name = "textures/armors/j.png";
        else if (stack.is(RRItems.snukerchest)) name = "textures/armors/j.png";
        else if (stack.is(RRItems.snukerpants)) name = "textures/armors/n.png";
        else if (stack.is(RRItems.snukerboots)) name = "textures/armors/j.png";
        else if (stack.is(RRItems.sintelhelmet)) name = "textures/armors/h.png";
        else if (stack.is(RRItems.sintelchest)) name = "textures/armors/h.png";
        else if (stack.is(RRItems.sintelpants)) name = "textures/armors/n.png";
        else if (stack.is(RRItems.sintelboots)) name = "textures/armors/h.png";
        else if (stack.is(RRItems.shackerhelmet)) name = "textures/armors/f.png";
        else if (stack.is(RRItems.shackerchest)) name = "textures/armors/f.png";
        else if (stack.is(RRItems.shackerpants)) name = "textures/armors/n.png";
        else if (stack.is(RRItems.shackerboots)) name = "textures/armors/f.png";
        else if (stack.is(RRItems.trollmask)) name = "textures/armors/o.png";
        else name = null;

        if (name != null) {
            this.renderModel(poseStack, bufferSource, packedLight, model, -1, RRIdentifiers.create(name)); // direct rendering
            return List.of();
        }
        return instance.layers();
    }
}
