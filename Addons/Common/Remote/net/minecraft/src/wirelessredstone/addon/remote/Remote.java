package net.minecraft.src.wirelessredstone.addon.remote;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

/**
 * 
 * @author Eurymachus
 *
 */
public class Remote
{
	protected EntityPlayer player;
	protected String freq;
	protected int slot;
	protected WirelessCoordinates coords;
	protected World world;
	protected List<WirelessRedstoneRemoteOverride> overrides;

	public Remote(EntityPlayer player, World world)
	{
		this.player = player;
		this.coords = new WirelessCoordinates((int)player.posX, (int)player.posY, (int)player.posZ);
		this.slot = player.inventory.currentItem;
		this.world = world;
		ItemStack itemstack = player.inventory.getStackInSlot(this.slot);
		this.freq = RedstoneWirelessItemStackMem.getInstance(world).getFreq(itemstack);
		this.overrides = new ArrayList<WirelessRedstoneRemoteOverride>();
	}
    
	public WirelessCoordinates getCoords()
	{
		return this.coords;
	}

	public String getFreq()
	{
		return this.freq;
	}

	public boolean isBeingHeld()
	{
		ItemStack itemstack = this.player.inventory.getCurrentItem();
		return this.player.inventory.currentItem == this.slot && itemstack != null && itemstack.getItem() == WirelessRemote.itemRemote && (RedstoneWirelessItemStackMem.getInstance(world).getFreq(itemstack)) == this.freq;
	}
    
	public void activate() 
	{
		WirelessRemote.itemRemote.setIconIndex(WirelessRemote.remoteon);
		transmitRemote("activateRemote", world, this);
	}

	public void deactivate()
	{
		ItemStack itemstack = this.player.inventory.getStackInSlot(this.slot);

		if (itemstack != null)
		{
			WirelessRemote.itemRemote.setIconIndex(WirelessRemote.remoteoff);
			transmitRemote("deactivateRemote", world, this);
		}
	}
	
	/**
	 * Adds a Remote override to the Remote.
	 * 
	 * @param override Remote override.
	 */
	public void addOverride(WirelessRedstoneRemoteOverride override)
	{
		overrides.add(override);
	}
    
	/**
	 * Transmits Wireless Remote Signal
	 * 
	 * @param command Command to be used
	 * @param world World Transmitted to/from
	 * @param remote Remote that is transmitting
	 */
	public void transmitRemote(String command, World world, Remote remote)
	{
		boolean prematureExit = false;
		for(WirelessRedstoneRemoteOverride override: overrides)
		{
			prematureExit = override.beforeTransmitRemote(command, world, remote);
		}
		if (prematureExit) return;
		
		if (command.equals("deactivateRemote"))
			RedstoneEther.getInstance().remTransmitter(world, remote.coords.getX(), remote.coords.getY(), remote.coords.getZ(), remote.getFreq());
		else
		{
			RedstoneEther.getInstance().addTransmitter(world, remote.coords.getX(), remote.coords.getY(), remote.coords.getZ(), remote.getFreq());
			RedstoneEther.getInstance().setTransmitterState(world, remote.coords.getX(), remote.coords.getY(), remote.coords.getZ(), remote.getFreq(), true);
		}
	}
}
