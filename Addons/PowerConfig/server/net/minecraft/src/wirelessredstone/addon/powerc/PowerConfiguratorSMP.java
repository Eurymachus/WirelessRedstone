package net.minecraft.src.wirelessredstone.addon.powerc;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.powerc.network.NetworkConnection;

public class PowerConfiguratorSMP
{

	public static boolean initialize()
	{
		try
		{
			MinecraftForge.registerConnectionHandler(new NetworkConnection());
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

}
