package assets.rivalrebels.common.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class SimpleArmorMaterial implements ArmorMaterial {
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final int[] durability;
    private final int durabilityMultiplier;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final Ingredient repair;
    private final String name;
    private final float toughness;
    private final float knockbackResistance;

    public SimpleArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Ingredient repairIngredient) {
        this.durability = slotProtections;
        this.durabilityMultiplier = durabilityMultiplier;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = sound;
        this.repair = repairIngredient;
        this.name = name;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurability(EquipmentSlot equipmentSlot) {
        return durability[equipmentSlot.getEntitySlotId()];
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return HEALTH_PER_SLOT[slot.getEntitySlotId()] * this.durabilityMultiplier;
    }

    @Override
    public int getEnchantability() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repair;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
