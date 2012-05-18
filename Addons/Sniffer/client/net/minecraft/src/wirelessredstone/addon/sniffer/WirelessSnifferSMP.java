package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.NetworkConnection;

public class WirelessSnifferSMP
{
	public static boolean initialize()
	{
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
		return true;
	}
}
