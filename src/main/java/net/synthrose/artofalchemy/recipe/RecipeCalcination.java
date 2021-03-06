package net.synthrose.artofalchemy.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.synthrose.artofalchemy.block.AoABlocks;

public class RecipeCalcination implements Recipe<Inventory> {
	
	protected Identifier id;
	protected String group;
	protected Ingredient input;
	protected ItemStack output;
	protected int cost;
	protected ItemStack container;
	
	public RecipeCalcination(Identifier id, String group, Ingredient input, ItemStack output, int cost, ItemStack container) {
		this.id = id;
		this.group = group;
		this.input = input;
		this.cost = cost;
		this.output = output;
		this.container = container;
	}

	@Override
	public boolean matches(Inventory inv, World world) {
		return input.test(inv.getInvStack(0));
	}

	@Override
	public ItemStack craft(Inventory inv) {
		return output.copy();
	}
	
	public Ingredient getInput() {
		return input;
	}
	
	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public Identifier getId() {
		return id;
	}
	
	public int getCost() {
		return cost;
	}
	
	public ItemStack getContainer() {
		return container;
	}
	
	@Override
	public RecipeType<?> getType() {
		return AoARecipes.CALCINATION;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return AoARecipes.CALCINATION_SERIALIZER;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public String getGroup() {
		return group;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getRecipeKindIcon() {
		return new ItemStack(AoABlocks.CALCINATOR);
	}

}
