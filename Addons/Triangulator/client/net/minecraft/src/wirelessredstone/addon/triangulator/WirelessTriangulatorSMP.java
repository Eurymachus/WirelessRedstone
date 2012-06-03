package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_WirelessTriangulator;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.PacketHandlerWirelessTriangulator;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessPlayerMem;

public class WirelessTriangulatorSMP
{
	public static boolean isLoaded = false;
	private static int ticksInGame=0;
	private static int ticksInGUI=0;
	
	public static boolean initialize()
	{
		try
		{
			MinecraftForge.registerConnectionHandler(new NetworkConnection());
			ModLoader.setInGameHook(mod_WirelessTriangulator.instance, true, true);
			return true;
		}
		catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(
			LoggerRedstoneWireless.filterClassName(
			WirelessTriangulatorSMP.class.toString()))
			.write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}

	public static boolean tick(Minecraft mc)
	{
		if (mc.currentScreen instanceof GuiRedstoneWirelessTriangulator)
		{
			++ticksInGUI;
			return true;
		}
		else if (ticksInGUI != 0)
		{
			ticksInGUI = 0;
		}
		if (!(ticksInGUI > 0))
		{
			if (ticksInGame == 40)
			{
				ticksInGame = 0;
				if (mc.thePlayer.inventory.hasItem(WirelessTriangulator.triangID))
				{
					String freq =  RedstoneWirelessPlayerMem.getInstance(mc.theWorld).getFreq(mc.thePlayer);
					if (mc.theWorld.isRemote)
						PacketHandlerWirelessTriangulator.PacketHandlerOutput.sendWirelessTriangulatorPacket(mc.thePlayer, "requestTriangulation", freq);
				}
			}
			++ticksInGame;
			return true;	
		}
		return true;
	}
}
