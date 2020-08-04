package stormedpanda.simplyjetpacks.lists;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stormedpanda.simplyjetpacks.SimplyJetpacks;

public enum ArmorMaterialList implements IArmorMaterial {

	PILOT_GOGGLES("pilot_goggles", 0, new int[] {0, 0, 0, 0}, 0, Items.IRON_INGOT, "item.armor.equip_leather", 0.0f, 0.0f),
	JETPACK("jetpack", 0, new int[] {0, 0, 0, 0}, 10, Items.IRON_INGOT, "item.armor.equip_iron", 0.0f, 0.0f),
	JETPACK_ARMORED("jetpack_armored", 66, new int[] {4, 7, 10, 4}, 10, Items.IRON_INGOT, "item.armor.equip_iron", 3.0f, 60.0f),
	JETPLATE("jetplate", 66, new int[] {4, 7, 10, 4}, 10, Items.IRON_INGOT, "item.armor.equip_iron", 3.0f, 60.0f);

	private final String name;
	private final int durability;
	private final int[] damageReductionAmounts;
	private final int enchantability;
	private final Item repairItem;
	private final String equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private static final int[] max_damage_array = new int[] {13, 15, 16, 11};

	ArmorMaterialList(String name, int durability, int[] damageReductionAmounts, int enchantability, Item repairItem, String equipSound, float toughness, float knockbackResistance) {
		this.name = name;
		this.durability = durability;
		this.damageReductionAmounts = damageReductionAmounts;
		this.enchantability = enchantability;
		this.repairItem = repairItem;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getName() {
		return SimplyJetpacks.MODID + ":" + this.name;
	}

	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return max_damage_array[slotIn.getIndex()] * this.durability;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return this.damageReductionAmounts[slotIn.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return Ingredient.fromItems(this.repairItem);
	}

	@Override
	public SoundEvent getSoundEvent() {
		return new SoundEvent(new ResourceLocation(equipSound));
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
