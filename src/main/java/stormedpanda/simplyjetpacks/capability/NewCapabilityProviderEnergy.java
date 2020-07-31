package stormedpanda.simplyjetpacks.capability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NewCapabilityProviderEnergy<HANDLER> implements ICapabilityProvider {

    protected final HANDLER instance;

    protected final Capability<HANDLER> capability;

    protected final Direction facing;

    public NewCapabilityProviderEnergy(final Capability<HANDLER> capability, @Nullable final Direction facing) {
        this(capability.getDefaultInstance(), capability, facing);
    }

    public NewCapabilityProviderEnergy(final HANDLER instance, Capability<HANDLER> capability, @Nullable Direction facing) {
        this.instance = instance;
        this.capability = capability;
        this.facing = facing;
    }

    public boolean hasCapability(Capability<?> capability, @Nullable Direction facing) {
        return capability == getCapability();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == this.getCapability()) {
            return (LazyOptional<T>) getInstance();
        }
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability) {
        return LazyOptional.empty();
    }

    public final Capability<HANDLER> getCapability() {
        return capability;
    }
    public final HANDLER getInstance() {
        return instance;
    }
    @Nullable
    public Direction getDirection() {
        return facing;
    }
}
