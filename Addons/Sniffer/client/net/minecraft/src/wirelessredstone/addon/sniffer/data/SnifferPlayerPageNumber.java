package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import net.minecraft.src.EntityPlayer;

public class SnifferPlayerPageNumber
{
	EntityPlayer entityplayer;
	int pageNumber;
	
	public SnifferPlayerPageNumber(EntityPlayer entityplayer, int pageNumber)
	{
		this.entityplayer = entityplayer;
		this.pageNumber = pageNumber;
	}
}
