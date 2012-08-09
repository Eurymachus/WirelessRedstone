package net.minecraft.src.wirelessredstone.addon.sniffer;

import java.util.HashMap;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessSnifferSMP;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferData;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferDevice;
import net.minecraft.src.wirelessredstone.addon.sniffer.smp.network.PacketHandlerWirelessSniffer;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.WirelessReadWriteLock;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;

public class WirelessSniffer {
	public static boolean isLoaded = false;
	public static Item itemSniffer;
	public static int snifferOff, snifferOn;
	public static int sniffID = 6244;
	public static int ticksInGame = 0;
	public static HashMap<EntityPlayer, WirelessSnifferDevice> sniffers;
	private static WirelessReadWriteLock lock;
	public static NetworkConnections snifferConnection;
	public static String channel = "WR-SNIFFER";

	public static boolean initialize() {
		try {
			ModLoader
					.setInGameHook(mod_WirelessSnifferSMP.instance, true, true);
			sniffers = new HashMap<EntityPlayer, WirelessSnifferDevice>();
			registerConnHandler();

			loadConfig();
			loadItemTextures();

			initItem();

			addNames();
			addRecipes();
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessSniffer.class.toString()))
					.write("Initialization failed.",
							LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}

	private static void addNames() {
		ModLoader.addName(itemSniffer, "Wireless Sniffer");
	}

	private static void initItem() {
		itemSniffer = (new ItemRedstoneWirelessSniffer(sniffID - 256))
				.setItemName("sniffer");
	}

	private static void registerConnHandler() {
	}

	public static void loadItemTextures() {
		snifferOff = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/sniff.png");
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemSniffer, 1), new Object[] {
				"X X", " X ", "X X", Character.valueOf('X'),
				WirelessRedstone.blockWirelessR });
	}

	private static void loadConfig() {
		sniffID = (Integer) ConfigStoreRedstoneWireless.getInstance("Sniffer")
				.get("ID", Integer.class, new Integer(sniffID));
	}

	public static WirelessSnifferData getDeviceData(String index, int id,
			String name, World world, EntityPlayer entityplayer) {
		index = index + "[" + id + "]";
		WirelessSnifferData data = (WirelessSnifferData) world.loadItemData(
				WirelessSnifferData.class, index);
		if (data == null) {
			data = new WirelessSnifferData(index);
			world.setItemData(index, data);
			data.setID(id);
			data.setName(name);
			data.setDimension(world);
			data.setPage(0);
		}
		return data;
	}

	public static WirelessSnifferData getDeviceData(ItemStack itemstack,
			World world, EntityPlayer entityplayer) {
		String index = itemstack.getItem().getItemName();
		int id = itemstack.getItemDamage();
		String name = "Wireless Sniffer";
		return getDeviceData(index, id, name, world, entityplayer);
	}

	public static int getIconFromDamage(String itemName, int i) {
		return 0;
	}

	public static String[] getActiveFrequencies(World world) {
		String[] activeFreqs = new String[WirelessRedstone.maxEtherFrequencies];
		int j = 0;
		for (int i = 0; i < WirelessRedstone.maxEtherFrequencies; ++i) {
			if (RedstoneEther.getInstance().getFreqState(world,
					String.valueOf(i))) {
				activeFreqs[j] = String.valueOf(i);
				++j;
			}
		}
		String[] newActiveFreqs = new String[j];
		for (int i = 0; i < j; ++i) {
			newActiveFreqs[i] = activeFreqs[i];
		}
		return newActiveFreqs;
	}

	public static void activateSniffer(World world, EntityPlayer entityplayer) {
		try {
			if (sniffers.containsKey(entityplayer)) {
				if (sniffers.get(entityplayer).isBeingHeld()) {
					PacketHandlerWirelessSniffer.PacketHandlerOutput
							.sendWirelessSnifferEtherCopy(entityplayer,
									getActiveFrequencies(world));
					return;
				}

				deactivateSniffer(world, entityplayer);
			}
			sniffers.put(entityplayer, new WirelessSnifferDevice(world,
					entityplayer));
			sniffers.get(entityplayer).activate();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessSniffer.class.toString()))
					.write("activateSniffer failed.",
							LoggerRedstoneWireless.LogLevel.DEBUG);
		}
	}

	public static boolean deactivateSniffer(World world,
			EntityPlayer entityplayer) {
		try {
			if (sniffers.containsKey(entityplayer)) {
				sniffers.get(entityplayer).deactivate();
				sniffers.remove(entityplayer);
				return true;
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessSniffer.class.toString()))
					.write("deactivateSniffer failed.",
							LoggerRedstoneWireless.LogLevel.DEBUG);
		}
		return false;
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			WirelessSnifferData deviceData) {
		PacketHandlerWirelessSniffer.PacketHandlerOutput
				.sendWirelessSnifferGuiPacket(entityplayer, deviceData.getID(),
						deviceData.getPage());
		activateSniffer(world, entityplayer);
	}

	public static boolean onTickInGame(MinecraftServer mcServer) {
		List players = mcServer.configManager.playerEntities;
		for (int j = 0; j < players.size(); j++) {
			EntityPlayerMP player = (EntityPlayerMP) players.get(j);
			if (sniffers.containsKey(player)) {
				if (sniffers.get(player).isBeingHeld()) {
					activateSniffer(player.worldObj, player);
				}
			}
		}
		return true;
	}
}
