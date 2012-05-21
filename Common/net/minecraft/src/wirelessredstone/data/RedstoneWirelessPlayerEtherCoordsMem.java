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

public class RedstoneWirelessPlayerEtherCoordsMem {
	private static RedstoneWirelessPlayerEtherCoordsMem instance;
	private Map<String,PlayerEtherCoordsMemNode> coords;
	private World world;
	
	private RedstoneWirelessPlayerEtherCoordsMem(World world) {
		coords = new HashMap<String,PlayerEtherCoordsMemNode>();
		this.world = world;
	}
	
	public static RedstoneWirelessPlayerEtherCoordsMem getInstance(World world) {
		if ( instance == null || instance.world.hashCode() != world.hashCode() ) 
			instance = new RedstoneWirelessPlayerEtherCoordsMem(world);
		
		return instance;
	}
	
	public void addMem(EntityPlayer entityplayer, WirelessCoordinates newcoords) {
		PlayerEtherCoordsMemNode memnode = new PlayerEtherCoordsMemNode(entityplayer, newcoords);
		coords.put(entityplayer.username, memnode);
	}
	
	public void remMem(String username) {
		coords.remove(username);
	}
	
	public void setCoords(EntityPlayer entityplayer, WirelessCoordinates newcoords) {
		addMem(entityplayer, newcoords);
	}
	
	public WirelessCoordinates getCoords(EntityPlayer entityplayer) {
		PlayerEtherCoordsMemNode node = coords.get(entityplayer.username);
		if ( node == null ) {
			addMem(entityplayer, null);
			return null;
		} else {
			return node.coords;
		}
	}
	
	public class PlayerEtherCoordsMemNode
	{
		EntityPlayer entityplayer;
		WirelessCoordinates coords;
		
		public PlayerEtherCoordsMemNode(EntityPlayer entityplayer, WirelessCoordinates coords)
		{
			this.entityplayer = entityplayer;
			this.coords = coords;
		}
	}
}