package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.client.model.ModelJetpack;
import stormedpanda.simplyjetpacks.lists.ListArmorMaterial;

import java.util.Properties;
import java.util.function.BiFunction;

public enum JetpackType {
    TEST("jetpack_test", 1, 1, 1),
    TEST_ARMORED("jetpack_test_armored", 25, 25, 1, true),
    IRON("iron_jetpack", 1, 1, 1),
    GOLD("gold_jetpack", 2, 1, 1),
    DIAMOND("diamond_jetpack", 3, 3, 3)
    ;

    private String name;
    private int capacity;
    private int maxReceive;
    private int maxExtract;
    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> getArmorApplier;
    private ResourceLocation armorTexture;
    private boolean isArmored;
    private int platingID;
    private Item.Properties properties;

    JetpackType(String name, int capacity, int maxReceive, int maxExtract, boolean isArmored) {
        this(name, capacity, maxReceive, maxExtract);
        this.isArmored = isArmored;
    }

    JetpackType(String name, int capacity, int maxReceive, int maxExtract, boolean isArmored, int platingID ) {
        this(name, capacity, maxReceive, maxExtract);
        this.isArmored = isArmored;
        this.platingID = platingID;
    }

    JetpackType(String name, int capacity, int maxReceive, int maxExtract) {
        this.name = name;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.getArmorApplier = new ModelJetpack()::applyData;
        this.armorTexture = new ResourceLocation(("simplyjetpacks:textures/armor/" + name + ".png"));
        this.isArmored = false;
        this.properties = new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks).maxStackSize(1);
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> getArmorApplier() {
        return getArmorApplier;
    }

    public String getArmorTexture() {
        return armorTexture.toString();
    }

    public IArmorMaterial getArmorMaterial() {
        if (isArmored) {
            return ListArmorMaterial.JETPACK_ARMORED;
        } else { return ListArmorMaterial.JETPACK; }
    }

    public boolean getIsArmored() {
        return isArmored;
    }

    public Item.Properties getProperties() {
        return properties;
    }

    public int getPlatingID() {
        return platingID;
    }
}
