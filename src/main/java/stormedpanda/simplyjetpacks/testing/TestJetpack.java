package stormedpanda.simplyjetpacks.testing;

import net.minecraft.util.IStringSerializable;

public enum TestJetpack implements IStringSerializable {

    JETPACK_TEST("jetpack_test", 1),
    JETPACK_TEST_ARMORED("jetpack_test_armored", 2);

    public final String name;
    public final int tier;

    private TestJetpack(String name, int tier) {
        this.name = name;
        this.tier = tier;
    }

    @Override
    public String getString() {
        return null;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }
}
