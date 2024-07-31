package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.init.data.LCLang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Supplier;

public class TooltipItem extends Item {

	private final Supplier<MutableComponent> sup;

	public TooltipItem(Properties properties, Supplier<MutableComponent> sup) {
		super(properties);
		this.sup = sup;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		var e = sup.get();
		if (e == null) {
			list.add(LCLang.IDS.BANNED.get().withStyle(ChatFormatting.RED));
		} else {
			list.add(e.withStyle(ChatFormatting.GRAY));
		}
		super.appendHoverText(stack, level, list, flag);
	}

	@Override
	public boolean canBeHurtBy(ItemStack stack, DamageSource source) {
		if (getDefaultInstance().getRarity() != Rarity.COMMON) {
			if (source.is(DamageTypeTags.IS_LIGHTNING) || source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION))
				return false;
		}
		return true;
	}

}
