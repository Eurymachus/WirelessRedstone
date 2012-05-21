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
package net.minecraft.src.wirelessredstone;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class RedstoneWirelessPlayerMem {
	private static RedstoneWirelessPlayerMem instance;
	private Map<String,RedstoneWirelessPlayerMemNode> playerFreqs;
	private WirelessReadWriteLock lock;
	private World world;
	
	private RedstoneWirelessPlayerMem(World world) {
		playerFreqs = new HashMap<String,RedstoneWirelessPlayerMemNode>();
		lock = new WirelessReadWriteLock();
		this.world = world;
	}

	/**
	 * Fetch the PlayerMem singleton instance.
	 * 
	 * @return PlayerMem instance.
	 */
	public static RedstoneWirelessPlayerMem getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new RedstoneWirelessPlayerMem(world);
		
		return instance;
	}

	/**
	 * Add a Player to the memory with a set frequency. <br>
	 * ID is the username.
	 * 
	 * @param entityplayer The player.
	 * @param freq Frequency.
	 */
	public synchronized void addMem(EntityPlayer entityplayer, String freq) {
		RedstoneWirelessPlayerMemNode memnode = new RedstoneWirelessPlayerMemNode(entityplayer, freq);
		try {
			lock.writeLock();
			playerFreqs.put(entityplayer.username, memnode);
			lock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	/**
	 * Add a Player to the memory with a set frequency and state. <br>
	 * ID is the username.
	 * 
	 * @param entityplayer The player.
	 * @param freq Frequency.
	 * @param state State.
	 */
	public synchronized void addMem(EntityPlayer entityplayer, String freq, boolean state) {
		addMem(entityplayer, freq);

		try {
			lock.writeLock();
			RedstoneWirelessPlayerMemNode memnode = playerFreqs.get(freq);
			memnode.received = state;
			lock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Remove a memory node based on ID/username.
	 * 
	 * @param username The player's username.
	 */
	public void remMem(String username) {
		try {
			lock.writeLock();
			playerFreqs.remove(username);
			lock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Get the frequency of a particular Player <br>
	 * ID is the username.
	 * 
	 * @param entityplayer The Player.
	 * @return Frequency.
	 */
	public String getFreq(EntityPlayer entityplayer) {
		try {
			lock.readLock();
			RedstoneWirelessPlayerMemNode node = playerFreqs.get(entityplayer.username);
			lock.readUnlock();
			
			if ( node == null ) {
				addMem(entityplayer, "0");
				return "0";
			} else {
				return node.freq;
			}
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		
		return "0";
	}
	
	/**
	 * 
	 * Get the state of a particular Player <br>
	 * ID is the username.
	 * 
	 * @param entityplayer The Player.
	 * @return State.
	 */
	public boolean getState(EntityPlayer entityplayer) {
		try {
			lock.readLock();
			RedstoneWirelessPlayerMemNode node = playerFreqs.get(entityplayer.username);
			lock.readUnlock();
			
			if ( node == null ) {
				addMem(entityplayer, "0");
				return false;
			} else {
				return node.received;
			}
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		
		return false;
	}

	private class RedstoneWirelessPlayerMemNode {
		EntityPlayer entityplayer;
		String freq;
		boolean received;
		
		public RedstoneWirelessPlayerMemNode(EntityPlayer entityplayer, String freq){
			this.entityplayer = entityplayer;
			this.freq = freq;
		}
	}
}
