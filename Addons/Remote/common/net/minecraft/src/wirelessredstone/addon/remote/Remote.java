package net.minecraft.src.wirelessredstone.addon.remote;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;

public class Remote
{
	protected EntityPlayer player;
	protected String freq;
	protected int slot;
	protected WirelessCoordinates coords;
	protected World world;

	public Remote(EntityPlayer player, World world)
	{
		this.player = player;
		this.coords = new WirelessCoordinates((int)player.posX, (int)player.posY, (int)player.posZ);
		this.slot = player.inventory.currentItem;
		this.world = world;
		ItemStack itemstack = player.inventory.getStackInSlot(this.slot);
		this.freq = RedstoneWirelessItemStackMem.getInstance(world).getFreq(itemstack);
	}
    
	public WirelessCoordinates getTransmitCoords()
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
    
	public void remoteOn() 
	{
		ItemStack var1 = this.player.inventory.getStackInSlot(this.slot);
		var1.setItemDamage(var1.getItemDamage());
		WirelessRemote.itemRemote.setIconIndex(WirelessRemote.remoteon);
	}

	public void remoteOff()
	{
		ItemStack itemstack = this.player.inventory.getStackInSlot(this.slot);

		if (itemstack != null)
		{
			itemstack.setItemDamage(itemstack.getItemDamage());
			WirelessRemote.itemRemote.setIconIndex(WirelessRemote.remoteoff);
		}
	}
}
