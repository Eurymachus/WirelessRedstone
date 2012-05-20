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
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackFreqMem;

public class ItemRedstoneWirelessRemote extends Item{
	protected ItemRedstoneWirelessRemote(int i) {
		super(i);
		maxStackSize = 1;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if (entityplayer.isSneaking()) {
			ModLoader.openGUI(entityplayer, new GuiRedstoneWirelessRemote(itemstack, world, entityplayer, i, j, k));
			return true;
		}
		this.onItemRightClick(itemstack, world, entityplayer);
		return false;
	}
    
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!entityplayer.isSneaking())
		{
			WirelessProcessRemote.activateRemote(world, entityplayer);
		}
		else {
			onItemUse(itemstack, 
					entityplayer,
					world, 
					(int)Math.round(entityplayer.posX),
					(int)Math.round(entityplayer.posY), 
					(int)Math.round(entityplayer.posZ), 
					0);
		}
		return itemstack;
	}
	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		itemstack.setItemDamage(itemstack.hashCode());
		this.iconIndex = WirelessRemote.remoteoff;
    }
	
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean isHeld)
    {
        if (entity instanceof EntityPlayer)
        {
            String freq = this.getItemFreq(itemstack);
            EntityPlayer entityplayer = (EntityPlayer)entity;

            if (!isHeld || !WirelessProcessRemote.isRemoteOn(entityplayer, freq) && !WirelessProcessRemote.deactivateRemote(world, entityplayer))
            {
            	RedstoneWirelessItemStackFreqMem.getInstance(world).setFreq(itemstack, freq);
            }
        }
    }

    public String getItemFreq(ItemStack itemstack)
    {
        return RedstoneWirelessItemStackFreqMem.getInstance(ModLoader.getMinecraftInstance().theWorld).getFreq(itemstack);
    }
}
