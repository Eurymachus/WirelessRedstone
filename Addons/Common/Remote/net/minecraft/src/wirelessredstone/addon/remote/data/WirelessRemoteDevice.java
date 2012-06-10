package net.minecraft.src.wirelessredstone.addon.remote.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;
import net.minecraft.src.wirelessredstone.data.WirelessDevice;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

/**
 * 
 * @author Eurymachus
 * 
 */
public class WirelessRemoteDevice extends WirelessDevice {
	protected int slot;

	public WirelessRemoteDevice(World world, EntityPlayer player) {
		this.owner = player;
		this.coords = new WirelessCoordinates((int) player.posX,
				(int) player.posY, (int) player.posZ);
		this.slot = player.inventory.currentItem;
		this.world = world;
		ItemStack itemstack = player.inventory.getStackInSlot(this.slot);
		this.freq = RedstoneWirelessItemStackMem.getInstance(world).getFreq(
				itemstack);
	}

	public boolean isBeingHeld() {
		ItemStack itemstack = this.owner.inventory.getCurrentItem();
		return this.owner.inventory.currentItem == this.slot
				&& itemstack != null
				&& itemstack.getItem() == WirelessRemote.itemRemote
				&& (RedstoneWirelessItemStackMem.getInstance(world)
						.getFreq(itemstack)) == this.freq;
	}
	
	@Override
	public void activate() {
		ItemStack itemstack = this.owner.inventory.getStackInSlot(this.slot);
		if (itemstack != null) {
			RedstoneWirelessItemStackMem.getInstance(this.world).setState(
					itemstack, true);
			WirelessRemote.transmitRemote("activateRemote", world, this);
		}
	}

	@Override
	public void deactivate() {
		ItemStack itemstack = this.owner.inventory.getStackInSlot(this.slot);
		if (itemstack != null)
			RedstoneWirelessItemStackMem.getInstance(this.world).setState(
					itemstack, false);
			WirelessRemote.transmitRemote("deactivateRemote", world, this);
	}
}
