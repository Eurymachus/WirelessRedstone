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
package net.minecraft.src.wifiremote;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class MemRedstoneWirelessRemote {
	private static MemRedstoneWirelessRemote instance;
	private Map<Integer,MemNode> freqs;
	private World world;
	
	private MemRedstoneWirelessRemote(World world) {
		freqs = new HashMap<Integer,MemNode>();
		this.world = world;
	}
	
	public static MemRedstoneWirelessRemote getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new MemRedstoneWirelessRemote(world);
		
		return instance;
	}
	
	public void addMem(ItemStack itemstack, String freq) {
		MemNode memnode = new MemNode(itemstack, freq);
		freqs.put(itemstack.getItemDamage(), memnode);
	}
	
	public void remMem(int stackDamage) {
		freqs.remove(stackDamage);
	}
	
	public void setFreq(ItemStack itemstack, String freq) {
		addMem(itemstack, freq);
	}
	
	public String getFreq(ItemStack itemstack) {
		MemNode node = freqs.get(itemstack.getItemDamage());
		if ( node == null ) {
			addMem(itemstack, "0");
			return "0";
		} else {
			return node.freq;
		}
	}
}