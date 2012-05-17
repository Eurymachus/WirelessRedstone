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

public class RedstoneWirelessPlayerMem {
	private static RedstoneWirelessPlayerMem instance;
	private Map<String,RedstoneWirelessPlayerMemNode> playerFreqs;
	private World world;
	
	private RedstoneWirelessPlayerMem(World world) {
		playerFreqs = new HashMap<String,RedstoneWirelessPlayerMemNode>();
		this.world = world;
	}
	
	public static RedstoneWirelessPlayerMem getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new RedstoneWirelessPlayerMem(world);
		
		return instance;
	}
	
	public void addMem(EntityPlayer entityplayer, String freq) {
		RedstoneWirelessPlayerMemNode memnode = new RedstoneWirelessPlayerMemNode(entityplayer, freq);
		playerFreqs.put(entityplayer.username, memnode);
	}
	
	public void remMem(String username) {
		playerFreqs.remove(username);
	}
	
	public void setFreq(EntityPlayer entityplayer, String freq) {
		addMem(entityplayer, freq);
	}
	
	public String getFreq(EntityPlayer entityplayer) {
		RedstoneWirelessPlayerMemNode node = playerFreqs.get(entityplayer.username);
		if ( node == null ) {
			addMem(entityplayer, "0");
			return "0";
		} else {
			return node.freq;
		}
	}
}
