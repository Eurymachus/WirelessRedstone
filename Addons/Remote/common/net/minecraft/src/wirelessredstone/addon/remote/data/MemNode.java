package net.minecraft.src.wirelessredstone.addon.remote.data;

import net.minecraft.src.ItemStack;

public class MemNode {
	ItemStack itemstack;
	String freq;
	
	public MemNode(ItemStack itemstack, String freq){
		this.itemstack = itemstack;
		this.freq = freq;
	}
}