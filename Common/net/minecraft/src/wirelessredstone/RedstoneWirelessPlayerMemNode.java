package net.minecraft.src.wirelessredstone;

import net.minecraft.src.EntityPlayer;

public class RedstoneWirelessPlayerMemNode {
	EntityPlayer entityplayer;
	String freq;
	
	public RedstoneWirelessPlayerMemNode(EntityPlayer entityplayer, String freq){
		this.entityplayer = entityplayer;
		this.freq = freq;
	}
}