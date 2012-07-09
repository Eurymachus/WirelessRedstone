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
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.wirelessredstone.addon.remote.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.remote.network.PacketHandlerWirelessRemote;
import net.minecraft.src.wirelessredstone.addon.remote.overrides.RedstoneEtherOverrideRemote;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class WirelessRemote {
	public static boolean isLoaded = false;
	public static Item itemRemote;
	public static int remoteID = 6245;

	public static WirelessRemoteDevice remoteTransmitter;

	public static long pulseTime = 2500;
	public static boolean duraTogg = true;
	public static int maxPulseThreads = 2;
	public static int remoteon, remoteoff;

	public static boolean initialize() {
		try {
			registerConnHandler();

			addEtherOverride();

			loadConfig();
			loadItemTextures();

			initItem();

			addRecipes();
			addNames();
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(WirelessRemote.class
							.toString())).write("Initialization failed.",
					LoggerRedstoneWireless.LogLevel.WARNING);
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
		itemRemote = (new ItemRedstoneWirelessRemote(remoteID - 256))
				.setItemName("wirelessredstone.remote");
	}

	public static void loadItemTextures() {
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemRemote, 1), new Object[] { "i",
				"#", Character.valueOf('i'), Block.torchRedstoneActive,
				Character.valueOf('#'), WirelessRedstone.blockWirelessT });
	}

	public static void addNames() {
		ModLoader.addName(itemRemote, "Wireless Remote");
	}

	private static void loadConfig() {
		remoteID = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("ID", Integer.class, new Integer(remoteID));
		duraTogg = (Boolean) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("Durability", Boolean.class, new Boolean(duraTogg));
		pulseTime = (Long) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("PulseDurration", Long.class, new Long(pulseTime));
		maxPulseThreads = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"Remote").get("MaxPulseThreads", Integer.class,
				new Integer(maxPulseThreads));
	}

	public static boolean isRemoteOn(EntityPlayer entityplayer, String freq) {
		return remoteTransmitter == null ? false
				: remoteTransmitter.getFreq() == freq;
	}

	public static WirelessRemoteData getDeviceData(String index, int id,
			String name, World world, EntityPlayer entityplayer) {
		index = index + "[" + id + "]";
		// ModLoader.getLogger().warning("getData: " + index);
		WirelessRemoteData data = (WirelessRemoteData) world.loadItemData(
				WirelessRemoteData.class, index);
		if (data == null) {
			data = new WirelessRemoteData(index);
			world.setItemData(index, data);
			data.setID(id);
			data.setName(name);
			data.setDimension(world);
			data.setFreq("0");
			data.setState(false);
		}
		// ModLoader.getLogger().warning("afterGet: " + "ID["+data.getID()+"]" +
		// " Freq[" + data.getFreq() + "]");
		return data;
	}

	public static WirelessRemoteData getDeviceData(ItemStack itemstack,
			World world, EntityPlayer entityplayer) {
		String index = itemstack.getItem().getItemName();
		int id = itemstack.getItemDamage();
		String name = "Wireless Remote";
		return getDeviceData(index, id, name, world, entityplayer);
	}

	public static void activateRemote(World world, EntityPlayer entityplayer) {
		if (remoteTransmitter != null) {
			if (remoteTransmitter.isBeingHeld())
				return;

			deactivateRemote(world, entityplayer);
		}
		remoteTransmitter = new WirelessRemoteDevice(world, entityplayer);
		remoteTransmitter.activate();
	}

	public static boolean deactivateRemote(World world,
			EntityPlayer entityplayer) {
		if (remoteTransmitter == null) {
			return false;
		} else {
			remoteTransmitter.deactivate();
			remoteTransmitter = null;
			return true;
		}
	}

	public static int getIconFromDamage(String name, int i) {
		return 0;
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			WirelessRemoteData deviceData) {
		PacketHandlerWirelessRemote.PacketHandlerOutput
				.sendWirelessRemoteGuiPacket(entityplayer, deviceData.getID(), deviceData.getFreq());
	}
}
