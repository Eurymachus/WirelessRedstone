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
package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ItemRedstoneWirelessTriangulator extends Item {
	protected ItemRedstoneWirelessTriangulator(int i) {
		super(i);
		maxStackSize = 1;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer,
			World world, int i, int j, int k, int l) {
		if (entityplayer.isSneaking()) {
			WirelessTriangulator.openGUI(world, entityplayer,
					WirelessTriangulator.getDeviceData(itemstack, world,
							entityplayer));
			return true;
		} else {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if (tileentity != null
					&& tileentity instanceof TileEntityRedstoneWireless)
				return true;
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		if (entityplayer.isSneaking())
			onItemUse(itemstack, entityplayer, world,
					(int) Math.round(entityplayer.posX),
					(int) Math.round(entityplayer.posY),
					(int) Math.round(entityplayer.posZ), 0);
		return itemstack;
	}

	public boolean isFull3D() {
		return true;
	}

	public int getIconFromDamage(int i) {
		return WirelessTriangulator.getIconFromDamage(this.getItemName(), i);
	}

	@Override
	public void onCreated(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		itemstack.setItemDamage(world.getUniqueDataId(this.getItemName()));
		WirelessTriangulator.getDeviceData(itemstack, world, entityplayer);
	}
}
