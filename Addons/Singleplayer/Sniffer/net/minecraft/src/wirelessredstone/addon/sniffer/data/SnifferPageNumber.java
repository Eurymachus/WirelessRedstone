package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public class SnifferPageNumber
{
	ItemStack itemstack;
	int pageNumber;
	
	public SnifferPageNumber(ItemStack itemstack, int pageNumber)
	{
		this.itemstack = itemstack;
		this.pageNumber = pageNumber;
	}
}
