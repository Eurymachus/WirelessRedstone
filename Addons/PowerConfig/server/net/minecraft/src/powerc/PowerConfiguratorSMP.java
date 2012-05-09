package net.minecraft.src.powerc;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.powerc.network.NetworkConnection;

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
