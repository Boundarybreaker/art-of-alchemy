package net.synthrose.artofalchemy.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.synthrose.artofalchemy.MateriaRank;

public class ItemMateria extends Item {
	
	private MateriaRank rank;

	public ItemMateria(Settings settings, MateriaRank rank) {
		super(settings);
		this.rank = rank;
	}
	
	public MateriaRank getRank() {
		return rank;
	}
	
	public int getTier() {
		if (rank == null) {
			return 0;
		} else {
			return rank.getTier();
		}
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack stack) {
	      return (stack.hasEnchantments() || rank.getTier() >= 6);
	}
}
