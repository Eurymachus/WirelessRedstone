package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.EntityPlayer;

public class PlayerMemNode {
	EntityPlayer entityplayer;
	String freq;
	
	public PlayerMemNode(EntityPlayer entityplayer, String freq){
		this.entityplayer = entityplayer;
		this.freq = freq;
	}
}