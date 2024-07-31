package dev.xkmc.l2complements.compat;

import dev.xkmc.l2complements.content.recipe.DiffusionRecipe;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.compat.jei.BaseRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DiffuseRecipeCategory extends BaseRecipeCategory<DiffusionRecipe, DiffuseRecipeCategory> {

	protected static final ResourceLocation BG = L2Complements.loc("textures/jei/background.png");

	public DiffuseRecipeCategory() {
		super(L2Complements.loc("diffusion"), DiffusionRecipe.class);
	}

	public DiffuseRecipeCategory init(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(BG, 0, 18, 90, 18);
		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, LCItems.DIFFUSION_WAND.asStack());
		return this;
	}

	@Override
	public Component getTitle() {
		return LCLang.IDS.DIFFUSE_TITLE.get();
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, DiffusionRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addItemStack(recipe.ingredient.asItem().getDefaultInstance());
		builder.addSlot(RecipeIngredientRole.INPUT, 19, 1).addItemStack(recipe.base.asItem().getDefaultInstance());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 1).addItemStack(recipe.result.asItem().getDefaultInstance());
	}

}
