package net.minecraft.src.clocker;

import net.minecraft.src.clocker.network.NetworkConnection;
import net.minecraft.src.forge.MinecraftForge;

public class WirelessClockerSMP {

	public static boolean initialize()
	{
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
		return true;
	}
}