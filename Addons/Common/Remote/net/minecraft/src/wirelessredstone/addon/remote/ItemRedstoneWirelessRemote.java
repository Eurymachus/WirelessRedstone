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
package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteData;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ItemRedstoneWirelessRemote extends Item {
	protected ItemRedstoneWirelessRemote(int i) {
		super(i);
		maxStackSize = 1;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer,
			World world, int i, int j, int k, int l) {
		if (entityplayer.isSneaking()) {
			WirelessRemote.openGUI(entityplayer, world);
			return true;
		} else {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if (tileentity != null
					&& tileentity instanceof TileEntityRedstoneWireless)
				return true;
		}
		this.onItemRightClick(itemstack, world, entityplayer);
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		if (!entityplayer.isSneaking())
			WirelessRemote.activateRemote(world, entityplayer);
		else
			onItemUse(itemstack, entityplayer, world,
					(int) Math.round(entityplayer.posX),
					(int) Math.round(entityplayer.posY),
					(int) Math.round(entityplayer.posZ), 0);
		return itemstack;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public int getIconFromDamage(int i) {
		String index = this.getItemName() + "[" + i + "]";
		WirelessRemoteData data = (WirelessRemoteData) WirelessRedstone
				.getWorld().loadItemData(WirelessRemoteData.class, index);
		if (data == null || !data.getDeviceState())
			return WirelessRemote.remoteoff;
		return WirelessRemote.remoteon;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity,
			int i, boolean isHeld) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			WirelessRemoteData data = WirelessRemote.getDeviceData(itemstack,
					world, entityplayer);
			String freq = data.getFreq();
			if (!isHeld || !WirelessRemote.isRemoteOn(entityplayer, freq)
					&& !WirelessRemote.deactivateRemote(world, entityplayer)) {
			}
		}
	}

	@Override
	public void onCreated(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		itemstack.setItemDamage(world.getUniqueDataId(this.getItemName()));
		WirelessRemote.getDeviceData(itemstack, world, entityplayer);
	}
}
