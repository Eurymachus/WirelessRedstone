package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.ItemStack;

public class ItemStackMemNode {
	ItemStack itemstack;
	String freq;
	
	public ItemStackMemNode(ItemStack itemstack, String freq){
		this.itemstack = itemstack;
		this.freq = freq;
	}
}