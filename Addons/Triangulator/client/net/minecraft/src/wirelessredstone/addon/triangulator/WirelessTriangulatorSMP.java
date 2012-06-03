package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_WirelessTriangulator;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class WirelessTriangulatorSMP
{
	public static boolean isLoaded = false;
	
	public static boolean initialize()
	{
		try
		{
			MinecraftForge.registerConnectionHandler(new NetworkConnection());
			ModLoader.setInGameHook(mod_WirelessTriangulator.instance, true, true);
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
