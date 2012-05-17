package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.EntityPlayer;

public class EtherCoordsMemNode {
	EntityPlayer entityplayer;
	int[] coords;
	
	public EtherCoordsMemNode(EntityPlayer entityplayer, int[] coords){
		this.entityplayer = entityplayer;
		this.coords = coords;
	}
}