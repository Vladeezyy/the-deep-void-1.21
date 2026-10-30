package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.Identifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

public class ModifiersRemoveAttributesProcedure {
	private static final Identifier ID_24AB05C9_3824_49F6_ADF3_029FA9B4194B = Identifier.fromNamespaceAndPath("the_deep_void", "24ab05c9-3824-49f6-adf3-029fa9b4194b");
	private static final Identifier ID_3B71BDCC_6DDF_4B2C_A521_B67097A89EE4 = Identifier.fromNamespaceAndPath("the_deep_void", "3b71bdcc-6ddf-4b2c-a521-b67097a89ee4");
	private static final Identifier ID_740510EC_164B_4253_B513_3367BC1740AF = Identifier.fromNamespaceAndPath("the_deep_void", "740510ec-164b-4253-b513-3367bc1740af");
	private static final Identifier ID_BD693A85_8070_460A_99C7_A3E4335C7106 = Identifier.fromNamespaceAndPath("the_deep_void", "bd693a85-8070-460a-99c7-a3e4335c7106");
	private static final Identifier ID_FC2B6742_191E_4C45_9DFD_D3A22493C141 = Identifier.fromNamespaceAndPath("the_deep_void", "fc2b6742-191e-4c45-9dfd-d3a22493c141");

	public static void execute(Entity entity) {
		if (entity == null)
			return;
		//Lavendite
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_BD693A85_8070_460A_99C7_A3E4335C7106) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_BD693A85_8070_460A_99C7_A3E4335C7106);
		}
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).getModifier(ID_FC2B6742_191E_4C45_9DFD_D3A22493C141) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).removeModifier(ID_FC2B6742_191E_4C45_9DFD_D3A22493C141);
		} //Rot
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).getModifier(ID_3B71BDCC_6DDF_4B2C_A521_B67097A89EE4) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).removeModifier(ID_3B71BDCC_6DDF_4B2C_A521_B67097A89EE4);
		} //Dark Steel
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).getModifier(ID_740510EC_164B_4253_B513_3367BC1740AF) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).removeModifier(ID_740510EC_164B_4253_B513_3367BC1740AF);
		}
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_24AB05C9_3824_49F6_ADF3_029FA9B4194B) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_24AB05C9_3824_49F6_ADF3_029FA9B4194B);
		}
	}
}
