package io.github.kadir1243.rivalrebels.mixin;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Attributes.class)
public class AttributesMixin {
    @ModifyConstant(method = "<clinit>", target = @Desc(owner = RangedAttribute.class, value = "<init>",args={String.class, double.class, double.class, double.class}), constant = @Constant(doubleValue = 1024, ordinal = 6))
    private static double init(double constant) {
        return Double.MAX_VALUE; // change MAX_HEALTH max value
    }

    @ModifyConstant(method = "<clinit>", target = @Desc(owner = RangedAttribute.class, value = "<init>",args={String.class, double.class, double.class, double.class}), constant = @Constant(doubleValue = 16))
    private static double init2(double constant) {
        return Double.MAX_VALUE; // change SCALE max value
    }
}
