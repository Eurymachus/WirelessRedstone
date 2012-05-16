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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class ItemRedstoneWirelessTriangulator extends Item{
	public String freq = "0";

	protected ItemRedstoneWirelessTriangulator(int i) {
		super(i);
		maxStackSize = 1;
	}
	
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		ModLoader.openGUI(entityplayer, new GuiRedstoneWirelessTriangulator());
		return true;
	}
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		onItemUse(
				itemstack, 
				entityplayer,
				world, 
				(int)Math.round(entityplayer.posX),
				(int)Math.round(entityplayer.posY), 
				(int)Math.round(entityplayer.posZ), 
				0
		);
		return itemstack;
	}
	public boolean isFull3D() {
		return true;
	}
}