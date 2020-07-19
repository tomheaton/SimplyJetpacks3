package stormedpanda.simplyjetpacks.integration;

import stormedpanda.simplyjetpacks.SimplyJetpacks;
import net.minecraftforge.fml.ModList;

public enum ModType {

    SIMPLY_JETPACKS(SimplyJetpacks.MODID),
    MEKANISM("mekanism"),
    ENDER_IO("enderio"),
    THERMAL_EXPANSION("thermalexpansion"),
    THERMAL_DYNAMICS("thermaldynamics");

    public final String[] modids;
    public final boolean loaded;

    ModType(String... modids) {
        this.modids = modids;

        for (String s : this.modids) {
            if (!ModList.get().isLoaded(s)) {
                SimplyJetpacks.LOGGER.info("mod is loaded : " + s + ModList.get().isLoaded(s) );
                this.loaded = false;
                return;
            }
        }
        this.loaded = true;
    }
}
