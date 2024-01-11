package assets.rivalrebels.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class ItemUtil {
    public static ItemStack getItemStack(EntityPlayer player, Predicate<ItemStack> predicate) {
        if (player == null) return ItemStack.EMPTY;
        ItemStack mainhand = player.getHeldItemMainhand();
        if (predicate.test(mainhand)) return mainhand;
        ItemStack offhand = player.getHeldItemOffhand();
        if (predicate.test(offhand)) return offhand;
        for (ItemStack stack : player.inventory.mainInventory) {
            if (predicate.test(stack))
                return stack;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getItemStack(EntityPlayer player, Item item) {
        return getItemStack(player, stack -> stack.getItem() == item);
    }
}
