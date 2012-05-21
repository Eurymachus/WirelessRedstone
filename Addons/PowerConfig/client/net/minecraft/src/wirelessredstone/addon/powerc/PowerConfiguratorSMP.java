package net.minecraft.src.wirelessredstone.addon.powerc;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.powerc.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class PowerConfiguratorSMP
{
	public static boolean isLoaded = false;
	
	public static boolean initialize()
	{
		try
		{
			MinecraftForge.registerConnectionHandler(new NetworkConnection());
			return true;
		}
		catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(
			LoggerRedstoneWireless.filterClassName(
			PowerConfiguratorSMP.class.toString()))
			.write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}

}
