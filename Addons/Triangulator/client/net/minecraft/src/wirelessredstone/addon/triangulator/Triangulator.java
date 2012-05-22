package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessPlayerMem;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;

public class Triangulator
{
	protected EntityPlayer player;
	protected String freq;
	protected WirelessCoordinates coords;
	protected World world;

	public Triangulator(EntityPlayer player, World world)
	{
		this.player = player;
		this.coords = new WirelessCoordinates((int)player.posX, (int)player.posY, (int)player.posZ);
		this.world = world;
		this.freq = RedstoneWirelessPlayerMem.getInstance(world).getFreq(player);
	}
    
	public WirelessCoordinates getCoords()
	{
		return this.coords;
	}

	public String getFreq()
	{
		return this.freq;
	}
	
	public ItemStack isInInventory()
	{
		for (int stack = 0; stack < player.inventory.getSizeInventory(); ++stack)
		{
			if (player.inventory.getStackInSlot(stack).itemID == WirelessTriangulator.triangID)
			{
				return player.inventory.getStackInSlot(stack);
			}
		}
		return null;
	}
}
