package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.triangulator.WirelessTriangulatorSMP;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class WirelessSnifferSMP
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
			WirelessTriangulatorSMP.class.toString()))
			.write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}
}
