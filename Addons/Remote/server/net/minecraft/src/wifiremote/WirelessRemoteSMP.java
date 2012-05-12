package net.minecraft.src.wifiremote;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wifiremote.network.NetworkConnection;

public class WirelessRemoteSMP {
	public static boolean initialize()
	{
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
		return true;
	}
}
