package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.NetworkConnection;

public class WirelessTriangulatorSMP {

	public static boolean initialize()
	{
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
		return true;
	}

}
