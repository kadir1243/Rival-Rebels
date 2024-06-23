package assets.rivalrebels.common.util;

import java.util.function.Predicate;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemUtil {
    public static ItemStack getItemStack(Player player, Predicate<ItemStack> predicate) {
        if (player == null) return ItemStack.EMPTY;
        ItemStack mainhand = player.getMainHandItem();
        if (predicate.test(mainhand)) return mainhand;
        ItemStack offhand = player.getOffhandItem();
        if (predicate.test(offhand)) return offhand;
        for (ItemStack stack : player.getInventory().items) {
            if (predicate.test(stack))
                return stack;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getItemStack(Player player, Item item) {
        return getItemStack(player, stack -> stack.is(item));
    }
}
