package net.minecraft.src.wirelessredstone.addon.remote.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.addon.remote.overrides.WirelessRedstoneRemoteOverride;
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
	protected static List<WirelessRedstoneRemoteOverride> overrides = new ArrayList();

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
			((WirelessRemoteData) this.data).setState(true);
			transmitRemote("activateRemote", world);
		}
	}

	@Override
	public void deactivate() {
		ItemStack itemstack = this.owner.inventory.getStackInSlot(this.slot);
		if (itemstack != null)
			((WirelessRemoteData) this.data).setState(false);
		transmitRemote("deactivateRemote", world);
	}

	/**
	 * Adds a Remote override to the Remote.
	 * 
	 * @param override
	 *            Remote override.
	 */
	public static void addOverride(WirelessRedstoneRemoteOverride override) {
		overrides.add(override);
	}

	/**
	 * Transmits Wireless Remote Signal
	 * 
	 * @param command
	 *            Command to be used
	 * @param world
	 *            World Transmitted to/from
	 * @param remote
	 *            Remote that is transmitting
	 */
	public void transmitRemote(String command, World world) {
		boolean prematureExit = false;
		for (WirelessRedstoneRemoteOverride override : overrides) {
			prematureExit = override.beforeTransmitRemote(command, world, this);
		}
		if (prematureExit)
			return;

		if (command.equals("deactivateRemote"))
			RedstoneEther.getInstance().remTransmitter(world,
					this.getCoords().getX(), this.getCoords().getY(),
					this.getCoords().getZ(), this.getFreq());
		else {
			RedstoneEther.getInstance().addTransmitter(world,
					this.getCoords().getX(), this.getCoords().getY(),
					this.getCoords().getZ(), this.getFreq());
			RedstoneEther.getInstance().setTransmitterState(world,
					this.getCoords().getX(), this.getCoords().getY(),
					this.getCoords().getZ(), this.getFreq(), true);
		}
	}
}
