package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.remote.network.NetworkConnection;

public class WirelessRemoteSMP {
	public static boolean initialize()
	{
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
		return true;
	}
}
