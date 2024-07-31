package dev.xkmc.l2complements.content.enchantment.core;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2core.serial.configval.DoubleConfigValue;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

import java.util.function.DoubleSupplier;

public record LimitValue(DoubleSupplier limit) implements EnchantmentValueEffect {

	public static final MapCodec<LimitValue> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			DoubleConfigValue.CODEC.fieldOf("limit").forGetter(LimitValue::limit)
	).apply(i, LimitValue::new));

	@Override
	public float process(int lv, RandomSource r, float val) {
		return Math.min((float) limit.getAsDouble(), val);
	}

	@Override
	public MapCodec<? extends EnchantmentValueEffect> codec() {
		return MAP_CODEC;
	}

}
