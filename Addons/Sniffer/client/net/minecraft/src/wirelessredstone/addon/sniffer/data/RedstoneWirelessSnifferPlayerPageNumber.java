package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class RedstoneWirelessSnifferPlayerPageNumber {

	private static RedstoneWirelessSnifferPlayerPageNumber instance;
	private Map<String,SnifferPlayerPageNumber> pageNumber;
	private World world;
	
	private RedstoneWirelessSnifferPlayerPageNumber(World world) {
		pageNumber = new HashMap<String,SnifferPlayerPageNumber>();
		this.world = world;
	}
	
	public static RedstoneWirelessSnifferPlayerPageNumber getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new RedstoneWirelessSnifferPlayerPageNumber(world);
		
		return instance;
	}
	
	public void addMem(EntityPlayer entityplayer, int page) {
		SnifferPlayerPageNumber memnode = new SnifferPlayerPageNumber(entityplayer, page);
		pageNumber.put(entityplayer.username, memnode);
	}
	
	public void remMem(String freq) {
		pageNumber.remove(freq);
	}
	
	public void setFreq(EntityPlayer entityplayer, int page) {
		addMem(entityplayer, page);
	}
	
	public int getPage(EntityPlayer entityplayer) {
		SnifferPlayerPageNumber node = pageNumber.get(entityplayer.username);
		if ( node == null ) {
			addMem(entityplayer, 0);
			return 0;
		} else {
			return node.pageNumber;
		}
	}
}
