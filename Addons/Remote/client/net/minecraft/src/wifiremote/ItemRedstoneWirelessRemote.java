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

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.PlayerController;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessRemote;
import net.minecraft.src.wifi.RedstoneEther;
import net.minecraft.src.wifi.RedstoneEtherNode;
import net.minecraft.src.wifi.WirelessTransmittingDevice;
import net.minecraft.src.wifi.core.Vector3;

public class ItemRedstoneWirelessRemote extends Item implements WirelessTransmittingDevice{
	protected ItemRedstoneWirelessRemote(int i) {
		super(i);
		maxStackSize = 1;
		setMaxDamage(64);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if (entityplayer.isSneaking()) {
			ModLoader.openGUI(entityplayer, new GuiRedstoneWirelessRemote(itemstack, world, entityplayer, i, j, k));
			if ( WirelessRemote.duraTogg )
				itemstack.damageItem(1, entityplayer);
			return true;
		}
		else {
			ThreadWirelessRemote.pulse(
				entityplayer,
				world);
		}
		return false;
	}
    
	@Override
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
	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public Vector3 getPosition() {
		EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
		return new Vector3(player.posX, player.posY, player.posZ);
	}

	@Override
	public int getDimension() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFreq() {
		String freq = MemRedstoneWirelessRemote.getInstance(ModLoader.getMinecraftInstance().theWorld).getFreq(this.hashCode());
		return freq;
	}

	@Override
	public EntityLiving getAttachedEntity() {
		// TODO Auto-generated method stub
		return null;
	}
}
