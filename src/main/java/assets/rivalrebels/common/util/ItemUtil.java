package assets.rivalrebels.common.util;

import java.util.function.Predicate;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemUtil {
    public static ItemStack getItemStack(Player player, Predicate<ItemStack> predicate) {
        if (player == null) return ItemStack.EMPTY;

        for (ItemStack stack : player.getAllSlots()) {
            if (predicate.test(stack)) {
                return stack;
            }
        }
        for (ItemStack stack : player.getInventory().items) {
            if (predicate.test(stack))
                return stack;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getItemStack(Player player, Item item) {
        return getItemStack(player, stack -> stack.is(item));
    }

    public static ItemStack findAndConsumeItem(LivingEntity user, Item item) {
        return getItemStack(user, stack -> stack.is(item)).consumeAndReturn(1, user);
    }

    public static ItemStack getItemStack(LivingEntity user, Item item) {
        return getItemStack(user, stack -> stack.is(item));
    }

    public static ItemStack getItemStack(LivingEntity user, Predicate<ItemStack> predicate) {
        if (user == null) return ItemStack.EMPTY;
        if (user instanceof Player) return getItemStack(((Player) user), predicate);
        for (ItemStack stack : user.getAllSlots()) {
            if (predicate.test(stack))
                return stack;
        }
        return ItemStack.EMPTY;
    }

    public static EquipmentSlot getRandomArmorSlot(RandomSource random) {
        return EquipmentSlot.values()[2 + random.nextIntBetweenInclusive(0, 4)];
    }

    public static EquipmentSlot damageRandomArmor(LivingEntity entity, int amount, RandomSource random) {
        EquipmentSlot slot = getRandomArmorSlot(random);
        ItemStack stack = entity.getItemBySlot(slot);
        if (!stack.isEmpty()) {
            stack.hurtAndBreak(amount, entity, slot);
        }
        return slot;
    }
}
