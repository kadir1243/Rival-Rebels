package assets.rivalrebels.mixin.client;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.item.RRItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(HumanoidArmorLayer.class)
@Environment(EnvType.CLIENT)
public abstract class ArmorFeatureRendererMixin {
    @Shadow
    protected abstract void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, HumanoidModel<LivingEntity> model, int dyeColor, ResourceLocation textureLocation);

    @Redirect(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArmorMaterial;layers()Ljava/util/List;"))
    private List<ArmorMaterial.Layer> getArmorTexture(ArmorMaterial instance, PoseStack poseStack, MultiBufferSource bufferSource, LivingEntity livingEntity, EquipmentSlot slot, int packedLight, HumanoidModel<LivingEntity> model, @Local ItemStack stack) {
        Item item = stack.getItem();
        String name;
        if (item == RRItems.camohat || item == RRItems.camoshirt || item == RRItems.camoshoes)
            name = "textures/armors/c.png";
        else if (item == RRItems.camopants)
            name = "textures/armors/d.png";
        else if (item == RRItems.camohat2 || item == RRItems.camoshirt2 || item == RRItems.camoshoes2)
            name = "textures/armors/a.png";
        else if (item == RRItems.camopants2)
            name = "textures/armors/b.png";
        else if (item == RRItems.orebelhelmet) name = "textures/armors/l.png";
        else if (item == RRItems.orebelchest) name = "textures/armors/l.png";
        else if (item == RRItems.orebelpants) name = "textures/armors/k.png";
        else if (item == RRItems.orebelboots) name = "textures/armors/l.png";
        else if (item == RRItems.onukerhelmet) name = "textures/armors/i.png";
        else if (item == RRItems.onukerchest) name = "textures/armors/i.png";
        else if (item == RRItems.onukerpants) name = "textures/armors/k.png";
        else if (item == RRItems.onukerboots) name = "textures/armors/i.png";
        else if (item == RRItems.ointelhelmet) name = "textures/armors/g.png";
        else if (item == RRItems.ointelchest) name = "textures/armors/g.png";
        else if (item == RRItems.ointelpants) name = "textures/armors/k.png";
        else if (item == RRItems.ointelboots) name = "textures/armors/g.png";
        else if (item == RRItems.ohackerhelmet) name = "textures/armors/e.png";
        else if (item == RRItems.ohackerchest) name = "textures/armors/e.png";
        else if (item == RRItems.ohackerpants) name = "textures/armors/k.png";
        else if (item == RRItems.ohackerboots) name = "textures/armors/e.png";
        else if (item == RRItems.srebelhelmet) name = "textures/armors/m.png";
        else if (item == RRItems.srebelchest) name = "textures/armors/m.png";
        else if (item == RRItems.srebelpants) name = "textures/armors/n.png";
        else if (item == RRItems.srebelboots) name = "textures/armors/m.png";
        else if (item == RRItems.snukerhelmet) name = "textures/armors/j.png";
        else if (item == RRItems.snukerchest) name = "textures/armors/j.png";
        else if (item == RRItems.snukerpants) name = "textures/armors/n.png";
        else if (item == RRItems.snukerboots) name = "textures/armors/j.png";
        else if (item == RRItems.sintelhelmet) name = "textures/armors/h.png";
        else if (item == RRItems.sintelchest) name = "textures/armors/h.png";
        else if (item == RRItems.sintelpants) name = "textures/armors/n.png";
        else if (item == RRItems.sintelboots) name = "textures/armors/h.png";
        else if (item == RRItems.shackerhelmet) name = "textures/armors/f.png";
        else if (item == RRItems.shackerchest) name = "textures/armors/f.png";
        else if (item == RRItems.shackerpants) name = "textures/armors/n.png";
        else if (item == RRItems.shackerboots) name = "textures/armors/f.png";
        else if (item == RRItems.trollmask) name = "textures/armors/o.png";
        else name = null;

        if (name != null) {
            this.renderModel(poseStack, bufferSource, packedLight, model, -1, RRIdentifiers.create(name)); // direct rendering
            return List.of();
        }
        return instance.layers();
    }
}
