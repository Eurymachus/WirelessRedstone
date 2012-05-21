package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.forge.DimensionManager;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class WirelessTriangulator
{
	public static boolean isLoaded = false;
	public static Item itemTriang;
	public static int triangID=6246;
	public static int pulseTime = 2500;
	public static int maxPulseThreads=5;
	public static boolean isServer = true;
	
	public static boolean initialize()
	{
		try
		{
			MinecraftForge.registerConnectionHandler(new NetworkConnection());
			loadConfig();
			itemTriang = (new ItemRedstoneWirelessTriangulator(triangID - 256)).setItemName("triang");
			loadItemTextures();
			addRecipes();
			ModLoader.addName(itemTriang, "Wireless Triangulator");
			return true;	
		}
		catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(
			LoggerRedstoneWireless.filterClassName(
			WirelessTriangulator.class.toString()))
			.write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}

	public static void loadItemTextures()
	{
	}

	public static void addRecipes()
	{
		ModLoader.addRecipe(new ItemStack(itemTriang, 1), new Object[] {
            "C", "X", Character.valueOf('X'), WirelessRedstone.blockWirelessR, Character.valueOf('C'), Item.compass
        });
	}

	private static void loadConfig()
	{
		triangID = (Integer) ConfigStoreRedstoneWireless.getInstance("Triangulator").get("ID", Integer.class, new Integer(triangID));
	}

	public static void openGUI(EntityPlayer entityplayer, World world)
	{
	}

	public static boolean tick(MinecraftServer mc)
	{
		World[] worlds = DimensionManager.getWorlds();
		for (int i = 0; i < worlds.length; i++)
		{
			for (int j = 0; j < worlds[i].playerEntities
					.size(); j++)
			{
				EntityPlayerMP player = (EntityPlayerMP)worlds[i].playerEntities.get(j);

				if (Math.abs(player.posX) <= 16
						&& Math.abs(player.posY) <= 16
						&& Math.abs(player.posZ) <= 16)
				{
					
				}
			}
		}
		return false;
	}
}
