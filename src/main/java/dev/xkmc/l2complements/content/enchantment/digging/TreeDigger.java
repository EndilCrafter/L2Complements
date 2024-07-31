package dev.xkmc.l2complements.content.enchantment.digging;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2complements.init.data.LCTagGen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public record TreeDigger() implements BlockBreaker {
	@Override
	public BlockBreakerInstance getInstance(DiggerContext ctx) {
		int r = LCConfig.SERVER.treeChopMaxRadius.get();
		int h = LCConfig.SERVER.treeChopMaxHeight.get();
		int max = LCConfig.SERVER.treeChopMaxBlock.get();
		return new TreeInstance(-r, r, -r, h, -r, r, max, ctx.level(), this::match);
	}

	public int match(BlockState state) {
		if (state.is(BlockTags.LOGS)) {
			return 2;
		}
		if (state.is(LCTagGen.AS_LEAF)) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<Component> descFull(int lv, String key, boolean alt, boolean book) {
		List<Component> ans = new ArrayList<>();
		ans.add(Component.translatable(key).withStyle(ChatFormatting.GRAY));
		if (lv > 0) {
			ans.add(LCLang.IDS.TREE_CHOP.get().withStyle(ChatFormatting.GRAY));
		}
		ans.add(LCLang.diggerRotate().withStyle(ChatFormatting.DARK_GRAY));
		return ans;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

}
