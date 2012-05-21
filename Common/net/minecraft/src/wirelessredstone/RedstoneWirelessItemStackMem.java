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

import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class RedstoneWirelessItemStackMem {
	private static RedstoneWirelessItemStackMem instance;
	private Map<Integer,RedstoneWirelessItemStackMemNode> itemFreqs;
	private WirelessReadWriteLock lock;
	private World world;
	
	private RedstoneWirelessItemStackMem(World world) {
		itemFreqs = new HashMap<Integer,RedstoneWirelessItemStackMemNode>();
		lock = new WirelessReadWriteLock();
		this.world = world;
	}

	/**
	 * Fetch the ItemStackMem singleton instance.
	 * 
	 * @return ItemStackMem instance.
	 */
	public static RedstoneWirelessItemStackMem getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new RedstoneWirelessItemStackMem(world);
		
		return instance;
	}
	
	/**
	 * Add an ItemStack to the memory with a set frequency. <br>
	 * ID is the item damage.
	 * 
	 * @param itemstack The ItemStack.
	 * @param freq Frequency.
	 */
	public void addMem(ItemStack itemstack, String freq) {
		RedstoneWirelessItemStackMemNode memnode = new RedstoneWirelessItemStackMemNode(itemstack, freq);
		
		try {
			lock.writeLock();
			itemFreqs.put(itemstack.getItemDamage(), memnode);
			lock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Remove a memory node based on ID/item damage.
	 * 
	 * @param stackDamage The ID/item damage
	 */
	public void remMem(int stackDamage) {
		try {
			lock.writeLock();
			itemFreqs.remove(stackDamage);
			lock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Get the frequency of a particular ItemStack <br>
	 * ID is the item damage.
	 * 
	 * @param itemstack The ItemStack.
	 * @return Frequency.
	 */
	public String getFreq(ItemStack itemstack) {
		try {
			lock.readLock();
			RedstoneWirelessItemStackMemNode node = itemFreqs.get(itemstack.getItemDamage());
			lock.readUnlock();
			
			if ( node == null ) {
				addMem(itemstack, "0");
				return "0";
			} else {
				return node.freq;
			}
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		
		return "0";
	}
	
	private class RedstoneWirelessItemStackMemNode {
		ItemStack itemstack;
		String freq;
		
		public RedstoneWirelessItemStackMemNode(ItemStack itemstack, String freq){
			this.itemstack = itemstack;
			this.freq = freq;
		}
	}
}
