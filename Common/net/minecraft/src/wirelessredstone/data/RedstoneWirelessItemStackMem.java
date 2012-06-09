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
package net.minecraft.src.wirelessredstone.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class RedstoneWirelessItemStackMem {
	private static RedstoneWirelessItemStackMem instance;
	private Map<Integer, RedstoneWirelessItemStackMemNode> itemFreqs;
	private WirelessReadWriteLock lock;
	private World world;

	private RedstoneWirelessItemStackMem(World world) {
		itemFreqs = new HashMap<Integer, RedstoneWirelessItemStackMemNode>();
		lock = new WirelessReadWriteLock();
		this.world = world;
	}

	/**
	 * Fetch the ItemStackMem singleton instance.
	 * 
	 * @return ItemStackMem instance.
	 */
	public static RedstoneWirelessItemStackMem getInstance(World world) {
		if (instance == null || instance.world.hashCode() != world.hashCode())
			instance = new RedstoneWirelessItemStackMem(world);

		return instance;
	}

	/**
	 * Add an ItemStack to the memory with a set frequency. <br>
	 * ID is the item damage.
	 * 
	 * @param itemstack
	 *            The ItemStack.
	 * @param freq
	 *            Frequency.
	 */
	public void addMem(ItemStack itemstack, String freq) {
		addMem(itemstack.getItemDamage(), freq);
	}

	/**
	 * Add an ItemStack to the memory with a set frequency. <br>
	 * ID is the item damage.
	 * 
	 * @param itemstack
	 *            The ItemStack.
	 * @param freq
	 *            Frequency.
	 */
	public void addMem(int id, String freq) {
		RedstoneWirelessItemStackMemNode memnode = new RedstoneWirelessItemStackMemNode(
				id, freq);

		try {
			lock.writeLock();
			itemFreqs.put(id, memnode);
			lock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * Remove a memory node based on ID.
	 * 
	 * @param id
	 *            The ID
	 */
	public void remMem(int id) {
		try {
			lock.writeLock();
			itemFreqs.remove(id);
			lock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * Set the state of a particular ItemStack <br>
	 * ID is the item damage.
	 * 
	 * @param itemstack
	 *            The ItemStack.
	 * @param state
	 *            .
	 */
	public void setState(ItemStack itemstack, boolean state) {
		setState(itemstack.getItemDamage(), state);
	}

	/**
	 * Set the state of a particular id <br>
	 * ID is the item damage.
	 * 
	 * @param id
	 *            The Memory ID.
	 * @param state
	 *            .
	 */
	public void setState(int id, boolean state) {
		try {
			lock.readLock();
			RedstoneWirelessItemStackMemNode node = itemFreqs.get(id);
			lock.readUnlock();
			if (node != null) {
				lock.writeLock();
				node.state = state;
				lock.writeUnlock();
			}
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * Get the state of a particular ItemStack <br>
	 * ID is the item damage.
	 * 
	 * @param itemstack
	 *            The ItemStack.
	 * @return state.
	 */
	public boolean getState(ItemStack itemstack) {
		return getState(itemstack.getItemDamage());
	}

	/**
	 * Get the state of a particular id <br>
	 * ID is the item damage.
	 * 
	 * @param id
	 *            The Memory ID.
	 * @return state.
	 */
	public boolean getState(int id) {
		try {
			lock.readLock();
			RedstoneWirelessItemStackMemNode node = itemFreqs.get(id);
			lock.readUnlock();

			if (node != null)
				return node.state;
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
		return false;
	}

	/**
	 * Get the frequency of a particular ItemStack <br>
	 * ID is the item damage.
	 * 
	 * @param itemstack
	 *            The ItemStack.
	 * @return Frequency.
	 */
	public String getFreq(ItemStack itemstack) {
		return getFreq(itemstack.getItemDamage());
	}

	/**
	 * Get the frequency of a particular id <br>
	 * ID is the item damage.
	 * 
	 * @param id
	 *            The Memory ID.
	 * @return Frequency.
	 */
	public String getFreq(int id) {
		try {
			lock.readLock();
			RedstoneWirelessItemStackMemNode node = itemFreqs.get(id);
			lock.readUnlock();

			if (node == null) {
				addMem(id, "0");
				return "0";
			} else {
				return node.freq;
			}
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}

		return "0";
	}

	private class RedstoneWirelessItemStackMemNode {
		int id;
		String freq;
		boolean state;

		public RedstoneWirelessItemStackMemNode(int id, String freq) {
			this.id = id;
			this.freq = freq;
			this.state = false;
		}
	}
}
