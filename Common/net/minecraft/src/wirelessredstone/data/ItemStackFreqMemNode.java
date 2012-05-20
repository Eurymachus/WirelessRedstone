package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.ItemStack;

public class ItemStackFreqMemNode {
	public ItemStack itemstack;
	public String freq;
	
	public ItemStackFreqMemNode(ItemStack itemstack, String freq){
		this.itemstack = itemstack;
		this.freq = freq;
	}
}