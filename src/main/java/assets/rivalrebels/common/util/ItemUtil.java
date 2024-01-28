package assets.rivalrebels.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class ItemUtil {
    public static ItemStack getItemStack(PlayerEntity player, Predicate<ItemStack> predicate) {
        if (player == null) return ItemStack.EMPTY;
        ItemStack mainhand = player.getMainHandStack();
        if (predicate.test(mainhand)) return mainhand;
        ItemStack offhand = player.getOffHandStack();
        if (predicate.test(offhand)) return offhand;
        for (ItemStack stack : player.getInventory().main) {
            if (predicate.test(stack))
                return stack;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getItemStack(PlayerEntity player, Item item) {
        return getItemStack(player, stack -> stack.getItem() == item);
    }
}
