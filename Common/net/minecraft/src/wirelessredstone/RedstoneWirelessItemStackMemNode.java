package net.minecraft.src.wirelessredstone;

import net.minecraft.src.ItemStack;

public class RedstoneWirelessItemStackMemNode {
	ItemStack itemstack;
	String freq;
	
	public RedstoneWirelessItemStackMemNode(ItemStack itemstack, String freq){
		this.itemstack = itemstack;
		this.freq = freq;
	}
}