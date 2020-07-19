package stormedpanda.simplyjetpacks.items;

import net.minecraft.util.IStringSerializable;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public enum Jetpack implements IStringSerializable {

    JETPACK_IRON("jetpack_iron", 1),
    JETPACK_IRON_ARMORED("jetpack_iron_armored", 2, true, 1)
    ;

    public String baseName;
    public int tier;
    public boolean isArmored;
    public int platingMeta;
    public boolean usesFuel;

    private final List<String> jetpacks = new ArrayList<String>();

/*    private Jetpack(String baseName, int tier, boolean usesFuel) {
        this(baseName, tier);
        this.usesFuel = usesFuel;
    }*/

    private Jetpack(String baseName, int tier, boolean isArmored, int platingMeta) {
        this(baseName, tier);
        this.isArmored = isArmored;
        this.platingMeta = platingMeta;
    }

    private Jetpack(String baseName, int tier) {
        this.baseName = baseName;
        this.tier = tier;
        this.isArmored = false;
        //this.platingMeta = platingMeta;
        this.usesFuel = true;
        this.jetpacks.add(baseName);
    }

    @Override
    public String getString() {
        return baseName;
    }

    public boolean getIsArmored() {
        return isArmored;
    }

    public int getPlatingMeta() {
        return platingMeta;
    }

    public static
    @Nonnull
    Jetpack getTypeFromMeta(int meta) {
        return values()[meta >= 0 && meta < values().length ? meta : 0];
    }
}

