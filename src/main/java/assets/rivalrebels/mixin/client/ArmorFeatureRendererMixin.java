package assets.rivalrebels.mixin.client;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorFeatureRenderer.class)
@Environment(EnvType.CLIENT)
public class ArmorFeatureRendererMixin {
    @Inject(method = "getArmorTexture", at = @At("HEAD"), cancellable = true)
    private void getArmorTexture(ArmorItem item, boolean secondLayer, String overlay, CallbackInfoReturnable<Identifier> cir) {
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
            cir.setReturnValue(RRIdentifiers.create(name));
        }
    }
}
