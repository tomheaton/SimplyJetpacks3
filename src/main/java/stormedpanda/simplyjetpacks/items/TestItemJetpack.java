package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.IEnergyStorage;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.client.models.JetpackTest;
import stormedpanda.simplyjetpacks.lists.ArmorMaterialList;

import java.util.function.BiFunction;

import static stormedpanda.simplyjetpacks.ModRegistry.location;

public class TestItemJetpack extends ArmorItem implements IEnergyStorage {

    public final static String TAG_ENERGY = "Energy";
    public final static String TAG_ON = "PackOn";
    //public final static String TAG_HOVERMODE_ON = "JetpackHoverModeOn";
    //public final static String TAG_EHOVER_ON = "JetpackEHoverOn";
    //public final static String TAG_CHARGER_ON = "JetpackChargerOn";

    public String name;
    public boolean showTier = true;
    public boolean hasFuelIndicator = true;
    public boolean hasStateIndicator = true;
    //public FuelType fuelType = FuelType.ENERGY;
    public boolean isForgeEnergyBase = true;
    public boolean isFluxBased = false;

    private final int numItems;

    public String armorTexture;

    public TestItemJetpack(String name, Properties properties, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
        super(ArmorMaterialList.netherite, EquipmentSlotType.CHEST, new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks));
        this.name = name;
        //this.armorApplier = armorApplier;
        this.armorApplier = new JetpackTest()::applyData;
        //this.armorTexture = armorTexture.toString();
        this.armorTexture = (new ResourceLocation("simplyjetpacks:textures/armor/" + name + ".png")).toString();
        //.setRegistryName(location("jetpack_iron"))
        this.setRegistryName(location(name));

        //this.setUnlocalizedName(SimplyJetpacks.PREFIX + name);
        //this.setHasSubtypes(true);
        //this.setMaxDamage(0);
        //this.setCreativeTab(SimplyJetpacks.creativeTab);
        this.setRegistryName(name);

        numItems = Jetpack.values().length;
    }

    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() { return 0; }

    @Override
    public int getMaxEnergyStored() { return 0; }

    //@Override
    public int getEnergyStored(ItemStack container) {
        return 0;
    }

    //@Override
    public int getMaxEnergyStored(ItemStack container) {
        int i = MathHelper.clamp(container.getDamage(), 0, numItems-1);
        return Jetpack.values()[i].getFuelCapacity();
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
