package dev.xkmc.l2complements.content.enchantment.legacy;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class FreezingBladeEnchantment extends EffectBladeEnchantment {

	@Override
	protected MobEffectInstance getEffect(int pLevel) {
		return new MobEffectInstance(LCEffects.ICE.holder(), LCConfig.SERVER.iceEnchantDuration.get() << (pLevel - 1));
	}


}
