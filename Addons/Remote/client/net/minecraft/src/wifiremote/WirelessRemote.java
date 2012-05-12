package net.minecraft.src.wifiremote;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ModLoader;
import net.minecraft.src.wifi.ConfigStoreRedstoneWireless;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.WirelessRedstone;

public class WirelessRemote {	
	public static Item itemRemote;
	public static int remoteID=6245;
	
	public static long pulseTime=1000;
	public static boolean duraTogg=true;
	public static int maxPulseThreads=10;
	public static int pulseKey=25;
	
	public static boolean initialize()
	{
		try
		{
			loadConfig();
			itemRemote = (new ItemRedstoneWirelessRemote(remoteID - 256)).setItemName("remote");
			loadItemTextures();
			addRecipes();
			addNames();
		} catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(WirelessRemote.class.toString())).write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return true;
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
		pulseKey = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote").get("PulseKey", Integer.class, new Integer(pulseKey));
	}
	
	public static void keyboardEvent(KeyBinding keybinding) {
		if ( 
				ModLoader.getMinecraftInstance().thePlayer.getCurrentEquippedItem() != null && 
				ModLoader.getMinecraftInstance().thePlayer.getCurrentEquippedItem().itemID == itemRemote.shiftedIndex
		)
			ThreadWirelessRemote.pulse(
					ModLoader.getMinecraftInstance().thePlayer,
					ModLoader.getMinecraftInstance().theWorld
			);
	}
}
