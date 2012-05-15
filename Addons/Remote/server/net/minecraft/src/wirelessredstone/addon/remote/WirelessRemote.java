package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class WirelessRemote {	
	public static Item itemRemote;
	public static int remoteID=6245;
	
	public static long pulseTime=2500;
	public static boolean duraTogg=true;
	public static int maxPulseThreads=2;
	
	public static boolean initialize()
	{
		try
		{
			loadConfig();
			itemRemote = (new ItemRedstoneWirelessRemote(remoteID - 256)).setItemName("remote");
			loadItemTextures();
			addRecipes();
			addNames();
			return true;
		} catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(WirelessRemote.class.toString())).write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}
	
	public static void loadItemTextures() {
		itemRemote.setIconIndex(ModLoader.addOverride("/gui/items.png", "/WirelessSprites/remote.png"));
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemRemote, 1), new Object[] {
            "i", "#", Character.valueOf('i'), Block.torchRedstoneActive, Character.valueOf('#'), WirelessRedstone.blockWirelessT
        });
	}
	
	public static void addNames() {
		ModLoader.addName(itemRemote, "Wireless Remote");
	}

	private static void loadConfig() {
		remoteID = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote").get("ID", Integer.class, new Integer(remoteID));
		duraTogg = (Boolean) ConfigStoreRedstoneWireless.getInstance("Remote").get("Durability", Boolean.class, new Boolean(duraTogg));
		pulseTime = (Long) ConfigStoreRedstoneWireless.getInstance("Remote").get("PulseDurration", Long.class, new Long(pulseTime));
		maxPulseThreads = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote").get("MaxPulseThreads", Integer.class, new Integer(maxPulseThreads));
	}
}
