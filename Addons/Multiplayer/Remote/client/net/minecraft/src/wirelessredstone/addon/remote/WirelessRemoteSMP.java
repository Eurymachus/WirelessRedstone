package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.remote.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class WirelessRemoteSMP
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
			WirelessRemoteSMP.class.toString()))
			.write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}
}
