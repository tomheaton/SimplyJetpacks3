package stormedpanda.simplyjetpacks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class SyncHandler {

    private static final Map<PlayerEntity, Boolean> flyKeyState = new HashMap<>();
    private static final Map<PlayerEntity, Boolean> descendKeyState = new HashMap<>();

    private static final Map<PlayerEntity, Boolean> forwardKeyState = new HashMap<>();
    private static final Map<PlayerEntity, Boolean> backwardKeyState = new HashMap<>();
    private static final Map<PlayerEntity, Boolean> leftKeyState = new HashMap<>();
    private static final Map<PlayerEntity, Boolean> rightKeyState = new HashMap<>();

    private static final Map<Integer, ParticleType> jetpackState = new HashMap<>();

    public static boolean isFlyKeyDown(LivingEntity user) {
        return !(user instanceof PlayerEntity) || flyKeyState.containsKey(user) && flyKeyState.get(user);
    }

    public static boolean isDescendKeyDown(LivingEntity user) {
        return user instanceof PlayerEntity && descendKeyState.containsKey(user) && descendKeyState.get(user);
    }

    public static boolean isForwardKeyDown(LivingEntity user) {
        return !(user instanceof PlayerEntity) || forwardKeyState.containsKey(user) && forwardKeyState.get(user);
    }

    public static boolean isBackwardKeyDown(LivingEntity user) {
        return user instanceof PlayerEntity && backwardKeyState.containsKey(user) && backwardKeyState.get(user);
    }

    public static boolean isLeftKeyDown(LivingEntity user) {
        return user instanceof PlayerEntity && leftKeyState.containsKey(user) && leftKeyState.get(user);
    }

    public static boolean isRightKeyDown(LivingEntity user) {
        return user instanceof PlayerEntity && rightKeyState.containsKey(user) && rightKeyState.get(user);
    }

    public static void processKeyUpdate(PlayerEntity player, boolean keyFly, boolean keyDescend, boolean keyForward, boolean keyBackward, boolean keyLeft, boolean keyRight) {
        flyKeyState.put(player, keyFly);
        descendKeyState.put(player, keyDescend);

        forwardKeyState.put(player, keyForward);
        backwardKeyState.put(player, keyBackward);
        leftKeyState.put(player, keyLeft);
        rightKeyState.put(player, keyRight);
    }

    public static void processJetpackUpdate(int entityId, ParticleType particleType) {
        if(particleType != null) {
            jetpackState.put(entityId, particleType);
        } else {
            jetpackState.remove(entityId);
        }
    }

    public static Map<Integer, ParticleType> getJetpackStates()
    {
        return jetpackState;
    }

    public static void clearAll() {
        flyKeyState.clear();
        forwardKeyState.clear();
        descendKeyState.clear();
        backwardKeyState.clear();
        leftKeyState.clear();
        rightKeyState.clear();
    }

    private static void removeFromAll(PlayerEntity player) {
        flyKeyState.remove(player);
        forwardKeyState.remove(player);
        descendKeyState.remove(player);
        backwardKeyState.remove(player);
        leftKeyState.remove(player);
        rightKeyState.remove(player);
    }

    @SubscribeEvent
    public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        removeFromAll(event.getPlayer());
    }

    @SubscribeEvent
    public void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        removeFromAll(event.getPlayer());
    }

}