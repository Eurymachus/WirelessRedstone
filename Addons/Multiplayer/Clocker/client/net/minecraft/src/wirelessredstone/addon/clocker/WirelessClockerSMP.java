package net.minecraft.src.wirelessredstone.addon.clocker;

import net.minecraft.src.mod_WirelessClocker;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.clocker.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.client.GuiRedstoneWirelessOverrideClient;

public class WirelessClockerSMP
{
	public static boolean isLoaded = false;
	
	public static boolean initialize()
	{
		try
		{
			registerConnHandler();
			addGuiOverride();
			return true;
		}
		catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(
			LoggerRedstoneWireless.filterClassName(
			WirelessClockerSMP.class.toString()))
			.write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}
	
	private static void registerConnHandler() 
	{
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}
	
	private static void addGuiOverride() 
	{
		GuiRedstoneWirelessOverrideClient override = new GuiRedstoneWirelessOverrideClient();
		mod_WirelessClocker.addGuiOverrideToClocker(override);
	}
}