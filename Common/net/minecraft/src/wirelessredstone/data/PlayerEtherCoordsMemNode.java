package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.EntityPlayer;

public class PlayerEtherCoordsMemNode {
	EntityPlayer entityplayer;
	int[] coords;
	
	public PlayerEtherCoordsMemNode(EntityPlayer entityplayer, int[] coords){
		this.entityplayer = entityplayer;
		this.coords = coords;
	}
}