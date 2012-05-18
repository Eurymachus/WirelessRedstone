package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.EntityPlayer;

public class PlayerFreqMemNode {
	EntityPlayer entityplayer;
	String freq;
	
	public PlayerFreqMemNode(EntityPlayer entityplayer, String freq){
		this.entityplayer = entityplayer;
		this.freq = freq;
	}
}