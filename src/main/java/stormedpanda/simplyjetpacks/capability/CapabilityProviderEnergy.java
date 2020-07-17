package stormedpanda.simplyjetpacks.capability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class CapabilityProviderEnergy<HANDLER> implements ICapabilityProvider {

    protected IEnergyStorage instance;

    public CapabilityProviderEnergy(IEnergyStorage instance) {
        this.instance = instance;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
        return CapabilityEnergy.ENERGY.orEmpty(capability, LazyOptional.of(() -> this.instance));
    }
}
/*    protected final HANDLER instance;
    protected final Capability<HANDLER> capability;

    public CapabilityProviderEnergy(HANDLER instance, Capability<HANDLER> capability, @Nullable Direction side) {
        this.instance = instance;
        this.capability = capability;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (capability == getCapability()) {
            //return getCapability().cast(getInstance());
            return getCapability().orEmpty(getCapability(), getInstance());
        }
        return null;
    }

    public final HANDLER getInstance() {
        return instance;
    }

    public final Capability<HANDLER> getCapability() {
        return capability;
    }*/

    /*protected final HANDLER instance;

    protected final Capability<HANDLER> capability;

    protected final EnumFacing facing;

    public CapabilityProviderEnergy(final Capability<HANDLER> capability, @Nullable final EnumFacing facing) {
        this(capability.getDefaultInstance(), capability, facing);
    }

    public CapabilityProviderEnergy(final HANDLER instance, Capability<HANDLER> capability, @Nullable EnumFacing facing) {
        this.instance = instance;
        this.capability = capability;
        this.facing = facing;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == getCapability();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == getCapability()) {
            return getCapability().cast(getInstance());
        }

        return null;
    }

    public final Capability<HANDLER> getCapability() {
        return capability;
    }

    @Nullable
    public EnumFacing getFacing() {
        return facing;
    }

    public final HANDLER getInstance() {
        return instance;
    }*/