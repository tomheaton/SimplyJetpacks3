package stormedpanda.simplyjetpacks.client.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelDragonArmor extends BipedModel<PlayerEntity> {

    private final ModelRenderer armorHead;
    private final ModelRenderer armorBody;
    private final ModelRenderer armorRightArm;
    private final ModelRenderer armorLeftArm;
    private final ModelRenderer armorRightLeg;
    private final ModelRenderer armorLeftLeg;
    private final ModelRenderer armorRightBoot;
    private final ModelRenderer armorLeftBoot;

    public ModelDragonArmor() {
        super(0f, 0f, 0, 0);
        textureWidth = 64;
        textureHeight = 64;

        armorHead = new ModelRenderer(this);
        armorHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedHead.addChild(armorHead);

        armorBody = new ModelRenderer(this);
        armorBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedBody.addChild(armorBody);
        
        armorRightArm = new ModelRenderer(this);
        armorRightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightArm.addChild(armorRightArm);

        armorLeftArm = new ModelRenderer(this);
        armorLeftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedLeftArm.addChild(armorLeftArm);
        
        armorRightLeg = new ModelRenderer(this);
        armorRightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightLeg.addChild(armorRightLeg);

        armorLeftLeg = new ModelRenderer(this);
        armorLeftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedLeftLeg.addChild(armorLeftLeg);

        armorRightBoot = new ModelRenderer(this);
        armorRightBoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightLeg.addChild(armorRightBoot);

        armorLeftBoot = new ModelRenderer(this);
        armorLeftBoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedLeftLeg.addChild(armorLeftBoot);

        setupCustomModel();
    }

    private void setupCustomModel() {
		bipedHead.addChild(armorHead);
		armorHead.addBox("armorHead", -5.0F, -2.0F, -3.0F, 10, 2, 8, 0.0F, 0, 12);
		armorHead.addBox("armorHead", -5.0F, -6.0F, 0.0F, 10, 2, 5, 0.0F, 0, 22);
		armorHead.addBox("armorHead", -3.0F, -10.0F, -2.0F, 1, 2, 9, 0.0F, 29, 32);
		armorHead.addBox("armorHead", 2.0F, -10.0F, -2.0F, 1, 2, 9, 0.0F, 27, 13);
		armorHead.addBox("armorHead", -5.0F, -9.0F, -5.0F, 10, 2, 10, 0.0F, 0, 0);

		bipedBody.addChild(armorBody);
		armorBody.addBox("armorBody", -4.0F, 0.0F, -3.0F, 1, 11, 6, 0.0F, 24, 24);
		armorBody.addBox("armorBody", -2.0F, 6.0F, -3.0F, 4, 1, 1, 0.0F, 48, 38);
		armorBody.addBox("armorBody", -1.0F, 4.0F, -3.0F, 2, 1, 1, 0.0F, 0, 12);
		armorBody.addBox("armorBody", -2.0F, 1.0F, -3.0F, 4, 2, 1, 0.0F, 49, 24);
		armorBody.addBox("armorBody", -2.0F, 8.0F, -3.0F, 4, 3, 6, 0.0F, 30, 0);
		armorBody.addBox("armorBody", 3.0F, 0.0F, -3.0F, 1, 11, 6, 0.0F, 0, 29);
		armorBody.addBox("armorBody", 1.0F, 0.0F, 2.0F, 2, 7, 3, 0.0F, 38, 43);
		armorBody.addBox("armorBody", -3.0F, 0.0F, 2.0F, 2, 7, 3, 0.0F, 0, 0);

		bipedRightArm.addChild(armorRightArm);
		armorRightArm.addBox("armorRightArm", -4.0F, -3.0F, 0.0F, 3, 11, 3, 0.0F, 40, 24);
		armorRightArm.addBox("armorRightArm", -4.0F, -1.0F, -3.0F, 3, 3, 2, 0.0F, 48, 7);
		armorRightArm.addBox("armorRightArm", -4.0F, 3.0F, -3.0F, 3, 3, 2, 0.0F, 48, 48);

		bipedLeftArm.addChild(armorLeftArm);
		armorLeftArm.addBox("armorLeftArm", 1.0F, -3.0F, 0.0F, 3, 11, 3, 0.0F, 14, 38);
		armorLeftArm.addBox("armorLeftArm", 1.0F, -1.0F, -3.0F, 3, 3, 2, 0.0F, 47, 41);
		armorLeftArm.addBox("armorLeftArm", 1.0F, 3.0F, -3.0F, 3, 3, 2, 0.0F, 0, 46);

		bipedRightLeg.addChild(armorRightLeg);
		armorRightLeg.addBox("armorLeftLeg", -3.0F, 1.0F, 0.0F, 3, 10, 3, 0.0F, 38, 9);
		
		bipedLeftLeg.addChild(armorLeftLeg);
		armorLeftLeg.addBox("armorRightLeg", 0.0F, 1.0F, 0.0F, 3, 10, 3, 0.0F, 26, 43);

		bipedRightLeg.addChild(armorRightBoot);
		armorRightBoot.addBox("armorLeftBoot", -3.0F, 2.0F, -3.0F, 3, 3, 2, 0.0F, 8, 29);
		armorRightBoot.addBox("armorLeftBoot", -3.0F, 6.0F, -3.0F, 3, 2, 2, 0.0F, 14, 34);
		armorRightBoot.addBox("armorLeftBoot", -3.0F, 9.0F, -3.0F, 3, 1, 2, 0.0F, 40, 38);
		
		bipedLeftLeg.addChild(armorLeftBoot);
		armorLeftBoot.addBox("armorRightBoot", 0.0F, 2.0F, -3.0F, 3, 3, 2, 0.0F, 44, 0);
		armorLeftBoot.addBox("armorRightBoot", 0.0F, 6.0F, -3.0F, 3, 2, 2, 0.0F, 48, 20);
		armorLeftBoot.addBox("armorRightBoot", 0.0F, 9.0F, -3.0F, 3, 1, 2, 0.0F, 32, 24);
    }

    @SuppressWarnings("rawtypes")
	public BipedModel<PlayerEntity> applyData(BipedModel defaultArmor, EquipmentSlotType slot) {
        this.isChild = defaultArmor.isChild;
        this.isSneak = defaultArmor.isSneak;
        this.isSitting = defaultArmor.isSitting;
        this.rightArmPose = defaultArmor.rightArmPose;
        this.leftArmPose = defaultArmor.leftArmPose;

        armorHead.showModel = false;
        armorBody.showModel = false;
        armorRightArm.showModel = false;
        armorLeftArm.showModel = false;
        armorRightLeg.showModel = false;
        armorLeftLeg.showModel = false;
        armorRightBoot.showModel = false;
        armorLeftBoot.showModel = false;
        switch(slot){
            case HEAD:
                armorHead.showModel = true;
                break;
            case CHEST:
                armorBody.showModel = true;
                armorRightArm.showModel = true;
                armorLeftArm.showModel = true;
                break;
            case LEGS:
                armorRightLeg.showModel = true;
                armorLeftLeg.showModel = true;
                break;
            case FEET:
                armorRightBoot.showModel = true;
                armorLeftBoot.showModel = true;
                break;
            default:
                break;
        }
        return this;
    }
}