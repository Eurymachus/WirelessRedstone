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
	protected EntityPlayer player;
	protected int slot;

	public WirelessRemoteDevice(EntityPlayer player, World world) {
		this.player = player;
		this.coords = new WirelessCoordinates((int) player.posX,
				(int) player.posY, (int) player.posZ);
		this.slot = player.inventory.currentItem;
		this.world = world;
		ItemStack itemstack = player.inventory.getStackInSlot(this.slot);
		this.freq = RedstoneWirelessItemStackMem.getInstance(world).getFreq(
				itemstack);
	}

	public boolean isBeingHeld() {
		ItemStack itemstack = this.player.inventory.getCurrentItem();
		return this.player.inventory.currentItem == this.slot
				&& itemstack != null
				&& itemstack.getItem() == WirelessRemote.itemRemote
				&& (RedstoneWirelessItemStackMem.getInstance(world)
						.getFreq(itemstack)) == this.freq;
	}
	
	@Override
	public void activate() {
		ItemStack itemstack = this.player.inventory.getStackInSlot(this.slot);
		RedstoneWirelessItemStackMem.getInstance(this.world).setState(
				itemstack, true);
		if (itemstack != null) {
			WirelessRemote.transmitRemote("activateRemote", world, this);
		}
	}

	@Override
	public void deactivate() {
		ItemStack itemstack = this.player.inventory.getStackInSlot(this.slot);
		RedstoneWirelessItemStackMem.getInstance(this.world).setState(
				itemstack, false);
		if (itemstack != null)
			WirelessRemote.transmitRemote("deactivateRemote", world, this);
	}
}
