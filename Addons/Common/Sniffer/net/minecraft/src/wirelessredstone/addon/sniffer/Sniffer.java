package net.minecraft.src.wirelessredstone.addon.sniffer;

import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class Sniffer
{
	protected EntityPlayer player;
	protected ItemStack itemstack;
	protected List<WirelessRedstoneSnifferOverride> overrides;
	
	public Sniffer(EntityPlayer player)
	{
		this.player = player;
		this.itemstack = player.getCurrentEquippedItem();
	}
	
	public void addOverride(WirelessRedstoneSnifferOverride override)
	{
		overrides.add(override);
	}

	public synchronized boolean getFreqState(int c)
	{
		boolean prematureExit = false;
		
		for (WirelessRedstoneSnifferOverride override: overrides)
		{
			if (override.beforeGetFreqState(c))
				prematureExit = false;
		}
		
		boolean returnState = false;
		
		if (!prematureExit)
			returnState = RedstoneEther.getInstance().getFreqState(ModLoader.getMinecraftInstance().theWorld,Integer.toString(c));

		boolean out = returnState;
		
		for (WirelessRedstoneSnifferOverride override: overrides)
		{
			out = override.afterGetFreqState(c, out);
		}
		return out;
	}
}
