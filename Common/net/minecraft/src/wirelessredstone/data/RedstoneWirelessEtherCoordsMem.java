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

public class RedstoneWirelessEtherCoordsMem {
	private static RedstoneWirelessEtherCoordsMem instance;
	private Map<String,EtherCoordsMemNode> coords;
	private World world;
	
	private RedstoneWirelessEtherCoordsMem(World world) {
		coords = new HashMap<String,EtherCoordsMemNode>();
		this.world = world;
	}
	
	public static RedstoneWirelessEtherCoordsMem getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new RedstoneWirelessEtherCoordsMem(world);
		
		return instance;
	}
	
	public void addMem(EntityPlayer entityplayer, int[] newcoords) {
		EtherCoordsMemNode memnode = new EtherCoordsMemNode(entityplayer, newcoords);
		coords.put(entityplayer.username, memnode);
	}
	
	public void remMem(String username) {
		coords.remove(username);
	}
	
	public void setCoords(EntityPlayer entityplayer, int[] newcoords) {
		addMem(entityplayer, newcoords);
	}
	
	public int[] getCoords(EntityPlayer entityplayer) {
		EtherCoordsMemNode node = coords.get(entityplayer.username);
		if ( node == null ) {
			addMem(entityplayer, null);
			return null;
		} else {
			return node.coords;
		}
	}
}