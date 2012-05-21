package net.minecraft.src.wirelessredstone.addon.clocker;

import net.minecraft.src.mod_WirelessClocker;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.clocker.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.smp.client.GuiRedstoneWirelessOverrideClient;

public class WirelessClockerSMP {

	public static boolean initialize()
	{
		registerConnHandler();
		addGuiOverride();
		return true;
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