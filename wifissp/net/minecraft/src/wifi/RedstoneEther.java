/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
*/
package net.minecraft.src.wifi;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

/**
 * Wireless Redstone Ether.<br>
 * Singleton pattern class.
 * 
 * @author ali4z
 */
public class RedstoneEther {
	private Map<String,RedstoneEtherFrequency> ether;
	private int currentWorldHash = 0;
	private static RedstoneEther instance;
	private JFrame gui;
	
	private RedstoneEther() {
		ether = new HashMap<String,RedstoneEtherFrequency>();
	}
	
	/**
	 * Fetch the Ether singleton instance.
	 * 
	 * @return Ether instance.
	 */
	public static RedstoneEther getInstance() {
		if ( instance == null ) {
			instance = new RedstoneEther();
		}
		return instance;
	}
	
	/**
	 * Associate a JFrame GUI to the ether.<br>
	 * This will allow the ether to redraw the GUI on changes.
	 * 
	 * @param gui JFrame gui.
	 */
	public void assGui(JFrame gui) {
		this.gui = gui;
	}
	
	/**
	 * Add a transmitter to the ether on a given frequency.
	 * 
	 * @param world the world object
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @param freq frequency
	 */
	public synchronized void addTransmitter(World world, int i, int j, int k, String freq) {
		try {
			if ( world == null || world.isRemote ) {
				return;
			}
			
			LoggerRedstoneWireless.getInstance("RedstoneEther").write("addTransmitter(world, "+i+", "+j+", "+k+", "+freq+")", LoggerRedstoneWireless.LogLevel.INFO);
	
			checkWorldHash(world);
			if ( !freqIsset(freq) ) createFreq(freq);
	
			RedstoneEtherNode node = new RedstoneEtherNode(i,j,k);
			node.freq = freq;
			ether.get(freq).addTransmitter(node);
			
			if ( gui != null ) gui.repaint();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		
	}
	/**
	 * Remove a transmitter from the ether.
	 * 
	 * @param world the world object
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @param freq frequency
	 */
	public synchronized void remTransmitter(World world, int i, int j, int k, String freq) {
		try {
			if ( world == null || world.isRemote ) {
				return;
			}
			
			LoggerRedstoneWireless.getInstance("RedstoneEther").write("remTransmitter(world, "+i+", "+j+", "+k+", "+freq+")", LoggerRedstoneWireless.LogLevel.INFO);
	
			checkWorldHash(world);
			if ( freqIsset(freq) ) {
				ether.get(freq).remTransmitter(world, i, j, k);
				if ( ether.get(freq).count() == 0 )
					ether.remove(freq);
			}
			if ( gui != null ) gui.repaint();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Add a receiver to the ether on a given frequency.
	 * 
	 * @param world the world object
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @param freq frequency
	 */
	public synchronized void addReceiver(World world, int i, int j, int k, String freq) {
		try {
			if ( world == null || world.isRemote ) {
				return;
			}			
			
			LoggerRedstoneWireless.getInstance("RedstoneEther").write("addReceiver(world, "+i+", "+j+", "+k+", "+freq+")", LoggerRedstoneWireless.LogLevel.INFO);
	
			checkWorldHash(world);
			if ( !freqIsset(freq) ) createFreq(freq);
			RedstoneEtherNode node = new RedstoneEtherNode(i,j,k);
			node.freq = freq;
			ether.get(freq).addReceiver(node);
			if ( gui != null ) gui.repaint();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	/**
	 * Remove a receiver from the ether.
	 * 
	 * @param world the world object
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @param freq frequency
	 */
	public synchronized void remReceiver(World world, int i, int j, int k, String freq) {
		try {
			if ( world == null || world.isRemote ) {
				return;
			}
	
			LoggerRedstoneWireless.getInstance("RedstoneEther").write("remReceiver(world, "+i+", "+j+", "+k+", "+freq+")", LoggerRedstoneWireless.LogLevel.INFO);
	
			checkWorldHash(world);
			if ( freqIsset(freq) ) {
				ether.get(freq).remReceiver(i, j, k);
				if ( ether.get(freq).count() == 0 )
					ether.remove(freq);
			}
			if ( gui != null ) gui.repaint();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Checks the world HASH value.<br>
	 * Flush out the ether if it has changed. (meaning the world has changed.)
	 * 
	 * @param world the world object
	 */
	private synchronized void checkWorldHash(World world) {
		if ( world != null && world.hashCode() != currentWorldHash ) {
			ether = new HashMap<String,RedstoneEtherFrequency>();
			currentWorldHash = world.hashCode();
		}
		if ( gui != null ) gui.repaint();
	}
	
	/**
	 * Initialize a frequency object on the ether.
	 * 
	 * @param freq frequency
	 */
	private synchronized void createFreq(String freq) {
		ether.put(freq, new RedstoneEtherFrequency());
	}
	/**
	 * Checks if the frequency object is initialized.
	 * 
	 * @param freq frequency
	 * @return Initialization status.
	 */
	private synchronized boolean freqIsset(String freq) {
		return ether.containsKey(freq);
	}
	
	/**
	 * Get the transmitting state on a frequency.
	 * 
	 * @param world the world object
	 * @param freq frequency
	 * @return Frequency state.
	 */
	public synchronized boolean getFreqState(World world, String freq) {
		LoggerRedstoneWireless.getInstance("RedstoneEther").write("getFreqState(world, "+freq+")", LoggerRedstoneWireless.LogLevel.DEBUG);

		if ( !freqIsset(freq) ) 
			return false;
		else {
			return ether.get(freq).getState(world);
		}
	}
	
	/**
	 * Set the state of a transmitter on a given frequency.
	 * 
	 * @param world the world object
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @param freq frequency
	 * @param state transmitter state
	 */
	public synchronized void setTransmitterState(World world, int i, int j, int k, String freq, boolean state) {
		try {
			if ( world == null || world.isRemote ) {
				return;
			}
	
			LoggerRedstoneWireless.getInstance("RedstoneEther").write("setTransmitterState(world, "+i+", "+j+", "+k+", "+freq+", "+state+")", LoggerRedstoneWireless.LogLevel.INFO);
		
			if ( freqIsset(freq) )
				ether.get(freq).setTransmitterState(world, i, j, k, state);
			if ( gui != null ) gui.repaint();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Fetch the coordinate array of the closest ACTIVE transmitter from a given point on the world and on a given frequency.
	 * 
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @param freq frequency
	 * @return Closest transmitter coordinate: {X,Y,Z}
	 */
	public synchronized int[] getClosestActiveTransmitter(int i, int j, int k, String freq) {
		if ( freqIsset(freq) )
			return ether.get(freq).getClosestActiveTransmitter(i, j, k);
		else return null;
	}
	/**
	 * Fetch the coordinate array of the closest transmitter from a given point on the world and on a given frequency.
	 * 
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @param freq frequency
	 * @return Closest transmitter coordinate: {X,Y,Z}
	 */
	public synchronized int[] getClosestTransmitter(int i, int j, int k, String freq) {
		if ( freqIsset(freq) )
			return ether.get(freq).getClosestTransmitter(i, j, k);
		else return null;
	}
	/**
	 * Get the hypotenuse between two points by using the pythagorean theorem.<br>
	 * IE, returns the distrance between two points.
	 * @param a point A: {x,y,z} or {x,y}
	 * @param b point B: {x,y,z} or {x,y}
	 * @return Length between the two points.
	 */
	public static float pythagoras(int[] a, int[] b ) {
		double x = 0;
		if ( a.length <= b.length ) {
			for ( int n = 0; n < a.length; n++ ) {
				x += Math.pow((a[n]-b[n]), 2);
			}
		} else {
			for ( int n = 0; n < b.length; n++ ) {
				x += Math.pow((a[n]-b[n]), 2);
			}
		}
		return (float)Math.sqrt(x);
	}
	
	/**
	 * Fetches all receiver nodes on the ether.
	 * @return receiver nodes.
	 */
	@SuppressWarnings("unchecked")
	public synchronized List<RedstoneEtherNode> getRXNodes() {
		List<RedstoneEtherNode> list = new LinkedList<RedstoneEtherNode>();
		try {
			HashMap<String,RedstoneEtherFrequency> etherClone = (HashMap<String, RedstoneEtherFrequency>)((HashMap<String, RedstoneEtherFrequency>)ether).clone();
			for ( RedstoneEtherFrequency freq : etherClone.values() )
				list.addAll( freq.rxs.values());
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		return list;
	}
	/**
	 * Fetches all transmitter nodes on the ether.
	 * @return receiver nodes.
	 */
	@SuppressWarnings("unchecked")
	public synchronized List<RedstoneEtherNode> getTXNodes() {
		List<RedstoneEtherNode> list = new LinkedList<RedstoneEtherNode>();
		try {
			HashMap<String,RedstoneEtherFrequency> etherClone = (HashMap<String, RedstoneEtherFrequency>)((HashMap<String, RedstoneEtherFrequency>)ether).clone();
			for ( RedstoneEtherFrequency freq : etherClone.values() )
				list.addAll(freq.txs.values());
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		return list;
	}
	/**
	 * Gets a map of all loaded frequencies and the number of nodes on each.
	 * @return Loaded frequencies: [freq=>nodeCount, ...]
	 */
	@SuppressWarnings("unchecked")
	public synchronized Map<String,Integer> getLoadedFrequencies() {
		Map<String,Integer> list = new HashMap<String,Integer>();
		try {
			HashMap<String,RedstoneEtherFrequency> etherClone = (HashMap<String, RedstoneEtherFrequency>)((HashMap<String, RedstoneEtherFrequency>)ether).clone();
			for ( String freq : etherClone.keySet() )
				list.put(freq, etherClone.get(freq).count());
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		return list;
	}
	
	/**
	 * Checks if a block is loaded on the world.
	 * 
	 * @param world the world object
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @return false if the block is not loaded, true if it is.
	 */
	public static boolean isBlockLoaded(World world, int i, int j, int k) {
		LoggerRedstoneWireless.getInstance("RedstoneEther").write("isBlockLoaded(world, "+i+", "+j+", "+k+")", LoggerRedstoneWireless.LogLevel.DEBUG);

		if ( world != null && world.getBlockId(i, j, k) != 0 && world.getBlockTileEntity(i, j, k) != null )  // Is loaded
			return true;
		
		
		if ( ModLoader.getMinecraftInstance() == null || ModLoader.getMinecraftInstance().thePlayer == null )
			return false;

		int[] a = {i,j,k};
		int[] b = {
				(int) ModLoader.getMinecraftInstance().thePlayer.posX,
				(int) ModLoader.getMinecraftInstance().thePlayer.posY,
				(int) ModLoader.getMinecraftInstance().thePlayer.posZ
		};
		if ( RedstoneEther.pythagoras(a, b) < 1 ) // Is player
			return true;
		
		return false;
	}
	
	/**
	 * Attempt to load the chunk if.<br>
	 * WARNING: Not used. May be unstable.
	 * 
	 * @param world the world object
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 */
	public static void loadIfUnloaded(World world, int i, int j, int k) {
		if ( !isBlockLoaded(world,i,j,k) ) {
			LoggerRedstoneWireless.getInstance("RedstoneEther").write("loadIfUnloaded(world, "+i+", "+j+", "+k+")", LoggerRedstoneWireless.LogLevel.DEBUG);
			world.getChunkProvider().loadChunk(i >> 4, j >> 4);
		}
	}
}