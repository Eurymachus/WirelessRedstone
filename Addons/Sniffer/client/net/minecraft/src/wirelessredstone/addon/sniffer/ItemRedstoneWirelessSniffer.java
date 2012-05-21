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
package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.PacketHandlerWirelessSniffer;

public class ItemRedstoneWirelessSniffer extends Item {
	protected ItemRedstoneWirelessSniffer(int i) {
		super(i);
		maxStackSize = 1;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ModLoader.openGUI(entityplayer, new GuiRedstoneWirelessSniffer(entityplayer, world));
		if(world.isRemote && WirelessSniffer.isLoaded) {
			PacketHandlerWirelessSniffer.PacketHandlerOutput.sendWirelessSnifferOpenGui(world, entityplayer);
		}
		return itemstack;
	}
	
	@Override
	public boolean isFull3D() {
		return true;
	}
}
