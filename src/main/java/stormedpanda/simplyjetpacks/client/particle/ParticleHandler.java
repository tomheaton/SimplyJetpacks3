package stormedpanda.simplyjetpacks.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import stormedpanda.simplyjetpacks.handlers.SyncHandler;
import stormedpanda.simplyjetpacks.items.JetpackItem;
import stormedpanda.simplyjetpacks.sound.JetpackSound;

import java.util.Random;

public class ParticleHandler {
    Minecraft minecraft = Minecraft.getInstance();

    public void showJetpackParticles(World world, LivingEntity wearer, ParticleType particle) {
        if (minecraft.gameSettings.particles != ParticleStatus.MINIMAL) {

            Random rand = new Random();
            Vector3d playerPos = new Vector3d(wearer.getPositionVec().getX(),wearer.getPositionVec().getY(), wearer.getPositionVec().getZ()).add(0, 1.5, 0);
            float random = (rand.nextFloat() - 0.5F) * 0.1F;

            Vector3d vLeft = new Vector3d(-0.28, -0.95, -0.35).rotatePitch(0).rotateYaw(wearer.renderYawOffset);
            Vector3d vRight = new Vector3d(0.28, -0.95, -0.35).rotatePitch(0).rotateYaw(wearer.renderYawOffset);
            Vector3d vCenter = new Vector3d((rand.nextFloat() - 0.5F) * 0.25F, -0.95, -0.38).rotatePitch(0).rotateYaw(wearer.renderYawOffset);

/*            Vector3d v = playerPos.add(vLeft).add(new Vector3d(wearer.getMotion().getX(), wearer.getMotion().getY(), wearer.getMotion().getZ()));
            ParticleUtils.spawnParticle(particle, world, v.x, v.y, v.z, random, -0.2D, random);
            v = playerPos.translate(vRight).translate(new Pos3D(wearer.motionX, wearer.motionY, wearer.motionZ));
            ParticleUtils.spawnParticle(particle, world, v.x, v.y, v.z, random, -0.2D, random);
            v = playerPos.translate(vCenter).translate(new Pos3D(wearer.motionX, wearer.motionY, wearer.motionZ));
            ParticleUtils.spawnParticle(particle, world, v.x, v.y, v.z, random, -0.2D, random);*/
        }
    }

    public static void spawnParticle(ParticleType particle, World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        final Minecraft minecraft = Minecraft.getInstance();
        /*switch(particle) {
            case NONE:
                return;
            case DEFAULT:
                minecraft.effectRenderer.addEffect(new EntityCustomFlameFX(world, posX, posY, posZ, velX, velY, velZ));
            case SMOKE:
                minecraft.effectRenderer.addEffect(new EntityCustomSmokeFX(world, posX, posY, posZ, velX, velY - 0.1D, velZ));
                return;
            case RAINBOW:
                minecraft.effectRenderer.addEffect(EntityColoredSmokeFX.getRainbowSmoke(world, posX, posY, posZ, velX, velY - 0.1D, velZ));
                minecraft.effectRenderer.addEffect(EntityColoredSmokeFX.getRainbowSmoke(world, posX, posY - 0.2D, posZ, velX, velY - 0.1D, velZ));
                return;
            case BUBBLE:
                minecraft.effectRenderer.addEffect(new EntityCustomBubbleFX(world, posX, posY, posZ, velX, velY, velZ));
        }*/
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (minecraft.player != null && minecraft.world != null) {
                if (!minecraft.isGamePaused()) {
                    ItemStack chest = minecraft.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                    Item item = chest.getItem();
                    if (!chest.isEmpty() && item instanceof JetpackItem) {
                        if (isFlying(minecraft.player)) {
                            Random rand = new Random();
                            Vector3d playerPos = new Vector3d(minecraft.player.getPositionVec().getX(), minecraft.player.getPositionVec().getY(), minecraft.player.getPositionVec().getZ()).add(0, 1.5, 0);
                            float random = (rand.nextFloat() - 0.5F) * 0.1F;

                            Vector3d vLeft = new Vector3d(-0.28, -0.95, -0.35).rotatePitch(0).rotateYaw(minecraft.player.renderYawOffset);
                            Vector3d vRight = new Vector3d(0.28, -0.95, -0.35).rotatePitch(0).rotateYaw(minecraft.player.renderYawOffset);

                            Vector3d v = playerPos.add(vLeft).add(new Vector3d(minecraft.player.getMotion().getX(), minecraft.player.getMotion().getY(), minecraft.player.getMotion().getZ()));
                            minecraft.particles.addParticle(ParticleTypes.FLAME, v.x, v.y, v.z, random, -0.2D, random);
                            //ParticleUtils.spawnParticle(particle, world, v.x, v.y, v.z, random, -0.2D, random);

                            v = playerPos.add(vRight).add(new Vector3d(minecraft.player.getMotion().getX(), minecraft.player.getMotion().getY(), minecraft.player.getMotion().getZ()));
                            minecraft.particles.addParticle(ParticleTypes.FLAME, v.x, v.y, v.z, random, -0.2D, random);
                            //ParticleUtils.spawnParticle(particle, world, v.x, v.y, v.z, random, -0.2D, random);

                            if (!JetpackSound.playing(minecraft.player.getEntityId())) {
                                minecraft.getSoundHandler().play(new JetpackSound(minecraft.player));
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean isFlying(PlayerEntity player) {
        ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            if (item instanceof JetpackItem) {
                JetpackItem jetpack = (JetpackItem) item;
                if (jetpack.isEngineOn(stack) && (jetpack.getEnergyStored(stack) > 0 || player.isCreative() || jetpack.isCreative())) {
                    if (jetpack.isHoverOn(stack)) {
                        return !player.isOnGround();
                    } else {
                        return SyncHandler.isHoldingUp(player);
                    }
                }
            }
        }
        return false;
    }
}
