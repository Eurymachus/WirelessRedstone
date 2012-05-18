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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class RedstoneWirelessPlayerFreqMem {
	private static RedstoneWirelessPlayerFreqMem instance;
	private Map<String,PlayerFreqMemNode> freqs;
	private World world;
	
	private RedstoneWirelessPlayerFreqMem(World world) {
		freqs = new HashMap<String,PlayerFreqMemNode>();
		this.world = world;
	}
	
	public static RedstoneWirelessPlayerFreqMem getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new RedstoneWirelessPlayerFreqMem(world);
		
		return instance;
	}
	
	public void addMem(EntityPlayer entityplayer, String freq) {
		PlayerFreqMemNode memnode = new PlayerFreqMemNode(entityplayer, freq);
		freqs.put(entityplayer.username, memnode);
	}
	
	public void remMem(String username) {
		freqs.remove(username);
	}
	
	public void setFreq(EntityPlayer entityplayer, String freq) {
		addMem(entityplayer, freq);
	}
	
	public String getFreq(EntityPlayer entityplayer) {
		PlayerFreqMemNode node = freqs.get(entityplayer.username);
		if ( node == null ) {
			addMem(entityplayer, "0");
			return "0";
		} else {
			return node.freq;
		}
	}
}