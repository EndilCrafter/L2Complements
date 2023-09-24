package dev.xkmc.l2complements.events;

import com.mojang.datafixers.util.Pair;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2damagetracker.contents.materials.generic.GenericArmorItem;
import dev.xkmc.l2damagetracker.contents.materials.generic.GenericTieredItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Stack;

@Mod.EventBusSubscriber(modid = L2Complements.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpecialEquipmentEvents {

	public static ThreadLocal<Stack<Pair<ServerPlayer, BlockState>>> PLAYER = ThreadLocal.withInitial(Stack::new);

	public static boolean isVisible(LivingEntity entity, ItemStack stack) {
		if (entity.isInvisible()) {
			if (stack.getItem() instanceof GenericTieredItem item) {
				if (item.getExtraConfig().hideWithEffect())
					return false;
			}
			if (stack.getItem() instanceof GenericArmorItem item) {
				if (item.getConfig().hideWithEffect())
					return false;
			}
			return stack.getEnchantmentLevel(LCEnchantments.SHULKER_ARMOR.get()) == 0;
		}
		return true;
	}

	public static int blockSound(ItemStack stack) {
		if (stack.getItem() instanceof GenericArmorItem item) {
			if (item.getConfig().dampenVibration())
				return 1;
		}
		return stack.getEnchantmentLevel(LCEnchantments.DAMPENED.get());
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		var players = PLAYER.get();
		if (players.isEmpty()) return;
		ServerPlayer player = players.peek().getFirst();
		if (!(event.getEntity() instanceof ItemEntity e)) return;
		if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.SMELT.get()) > 0) {
			ItemStack input = e.getItem().copy();
			SimpleContainer cont = new SimpleContainer(input);
			var opt = event.getLevel().getRecipeManager()
					.getRecipeFor(RecipeType.SMELTING, cont, event.getLevel());
			if (opt.isPresent()) {
				ItemStack ans = opt.get().assemble(cont, event.getLevel().registryAccess());
				int count = ans.getCount() * input.getCount();
				ans.setCount(count);
				e.setItem(ans);
			}
		}
		if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.ENDER.get()) > 0) {
			ItemStack stack = e.getItem().copy();
			if (!player.getInventory().add(stack)) {
				e.setItem(stack);
				e.teleportTo(player.getX(), player.getY(), player.getZ());
			} else {
				event.setCanceled(true);
				return;
			}
		}
		if (player.dampensVibrations()) {
			e.getPersistentData().putBoolean("dampensVibrations", true);
		}
	}

	public static void pushPlayer(ServerPlayer player, BlockPos pos) {
		PLAYER.get().push(Pair.of(player, player.level().getBlockState(pos)));
	}


	public static void popPlayer(ServerPlayer player) {
		if (PLAYER.get().peek().getFirst() == player)
			PLAYER.get().pop();
	}
}
