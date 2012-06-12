package net.minecraft.src.wirelessredstone.addon.remote.data;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;
import net.minecraft.src.wirelessredstone.data.WirelessDevice;

/**
 * 
 * @author Eurymachus
 * 
 */
public class WirelessRemoteDevice extends WirelessDevice {
	protected int slot;

	public WirelessRemoteDevice(World world, EntityPlayer entityplayer) {
		this.owner = entityplayer;
		this.world = world;
		this.slot = entityplayer.inventory.currentItem;
		ItemStack itemstack = entityplayer.inventory.getStackInSlot(this.slot);
		this.data = WirelessRemote
				.getDeviceData(itemstack, world, entityplayer);
		this.coords = new WirelessCoordinates((int) entityplayer.posX,
				(int) entityplayer.posY, (int) entityplayer.posZ);
	}

	public boolean isBeingHeld() {
		ItemStack itemstack = this.owner.inventory.getCurrentItem();
		return this.owner.inventory.currentItem == this.slot
				&& itemstack != null
				&& itemstack.getItem() == WirelessRemote.itemRemote
				&& WirelessRemote.getDeviceData(itemstack, this.world,
						this.owner).getFreq() == this.getFreq();
	}

	@Override
	public void activate() {
		ItemStack itemstack = this.owner.inventory.getStackInSlot(this.slot);
		if (itemstack != null) {
			((WirelessRemoteData) this.data).setDeviceState(true);
			WirelessRemote.transmitRemote("activateRemote", world, this);
		}
	}

	@Override
	public void deactivate() {
		ItemStack itemstack = this.owner.inventory.getStackInSlot(this.slot);
		if (itemstack != null)
			((WirelessRemoteData) this.data).setDeviceState(false);
		WirelessRemote.transmitRemote("deactivateRemote", world, this);
	}
}
