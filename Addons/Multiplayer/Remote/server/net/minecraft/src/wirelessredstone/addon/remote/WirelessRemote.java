package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteData;
import net.minecraft.src.wirelessredstone.addon.remote.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.remote.overrides.RedstoneEtherOverrideRemote;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class WirelessRemote
{	
	public static boolean isLoaded = false;
	public static Item itemRemote;
	public static int remoteID=6245;
	
	public static long pulseTime=2500;
	public static boolean duraTogg=true;
	public static int maxPulseThreads=2;
	public static int remoteon, remoteoff;
	
	public static boolean initialize()
	{
		try
		{
			registerConnHandler();
			addEtherOverride();
			loadConfig();
			initItem();
			loadItemTextures();
			addRecipes();
			addNames();
			return true;
		}
		catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(
			LoggerRedstoneWireless.filterClassName(
			WirelessRemote.class.toString()))
			.write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}
	
	private static void registerConnHandler() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	private static void addEtherOverride() {
		RedstoneEtherOverrideRemote etherOverrideRemote = new RedstoneEtherOverrideRemote();
		RedstoneEther.getInstance().addOverride(etherOverrideRemote);
	}

	private static void initItem() {
		itemRemote = (new ItemRedstoneWirelessRemote(remoteID - 256)).setItemName("remote");
	}

	public static void loadItemTextures()
	{
		itemRemote.setIconIndex(ModLoader.addOverride("/gui/items.png", "/WirelessSprites/remote.png"));
	}

	public static void addRecipes()
	{
		ModLoader.addRecipe(new ItemStack(itemRemote, 1), new Object[] {
            "i", "#", Character.valueOf('i'), Block.torchRedstoneActive, Character.valueOf('#'), WirelessRedstone.blockWirelessT
        });
	}
	
	public static void addNames()
	{
		ModLoader.addName(itemRemote, "Wireless Remote");
	}

	private static void loadConfig()
	{
		remoteID = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote").get("ID", Integer.class, new Integer(remoteID));
		duraTogg = (Boolean) ConfigStoreRedstoneWireless.getInstance("Remote").get("Durability", Boolean.class, new Boolean(duraTogg));
		pulseTime = (Long) ConfigStoreRedstoneWireless.getInstance("Remote").get("PulseDurration", Long.class, new Long(pulseTime));
		maxPulseThreads = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote").get("MaxPulseThreads", Integer.class, new Integer(maxPulseThreads));
	}

	public static void openGUI(EntityPlayer entityplayer, World world)
	{
	}

	public static WirelessRemoteData getDeviceData(String index, int id,
			String name, World world, EntityPlayer entityplayer) {
		index = index + "[" + id + "]";
		WirelessRemoteData data = (WirelessRemoteData) world.loadItemData(
				WirelessRemoteData.class, index);
		if (data == null) {
			data = new WirelessRemoteData(index, id, name, world, entityplayer);
			world.setItemData(index, data);
		}
		return data;
	}

	public static WirelessRemoteData getDeviceData(ItemStack itemstack,
			World world, EntityPlayer entityplayer) {
		String index = itemstack.getItem().getItemName();
		int id = itemstack.getItemDamage();
		String name = "Wireless Remote";
		return getDeviceData(index, id, name, world, entityplayer);
	}
}
