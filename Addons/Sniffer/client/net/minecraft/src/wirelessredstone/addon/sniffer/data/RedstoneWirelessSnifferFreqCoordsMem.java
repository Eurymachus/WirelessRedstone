package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.World;

public class RedstoneWirelessSnifferFreqCoordsMem {

	private static RedstoneWirelessSnifferFreqCoordsMem instance;
	private Map<String,SnifferFreqCoordsMemNode> freqCoords;
	private World world;
	
	private RedstoneWirelessSnifferFreqCoordsMem(World world) {
		freqCoords = new HashMap<String,SnifferFreqCoordsMemNode>();
		this.world = world;
	}
	
	public static RedstoneWirelessSnifferFreqCoordsMem getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new RedstoneWirelessSnifferFreqCoordsMem(world);
		
		return instance;
	}
	
	public void addMem(int x, int y, int page, String freq, boolean state) {
		SnifferFreqCoordsMemNode memnode = new SnifferFreqCoordsMemNode(x, y, page, freq, state);
		freqCoords.put(freq, memnode);
	}
	
	public void remMem(String freq) {
		freqCoords.remove(freq);
	}
	
	public void setFreq(int x, int y, int page, String freq, boolean state) {
		addMem(x, y, page, freq, state);
	}
	
	public int getPage(String freq) {
		SnifferFreqCoordsMemNode node = freqCoords.get(freq);
		if ( node == null ) {
			addMem(0, 0, 0, freq, false);
			return 0;
		} else {
			return node.page;
		}
	}
}
