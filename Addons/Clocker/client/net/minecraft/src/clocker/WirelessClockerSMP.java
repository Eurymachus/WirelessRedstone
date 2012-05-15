package net.minecraft.src.clocker;

import net.minecraft.src.mod_WirelessClocker;
import net.minecraft.src.clocker.network.NetworkConnection;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wifi.GuiRedstoneWirelessOverrideClient;

public class WirelessClockerSMP {

	public static boolean initialize()
	{
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
		GuiRedstoneWirelessOverrideClient override = new GuiRedstoneWirelessOverrideClient();
		mod_WirelessClocker.addGuiOverrideToClocker(override);
		return true;
	}
}