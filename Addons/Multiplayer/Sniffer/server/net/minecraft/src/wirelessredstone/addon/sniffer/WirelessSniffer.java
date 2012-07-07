package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessSnifferSMP;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferData;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferDevice;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.PacketHandlerWirelessSniffer;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class WirelessSniffer {
	public static boolean isLoaded = false;
	public static Item itemSniffer;
	public static int snifferOff, snifferOn;
	public static WirelessSnifferDevice snifferReceiver;
	public static int sniffID = 6244;
	public static int ticksInGame = 0;

	public static boolean initialize() {
		ModLoader.setInGameHook(mod_WirelessSnifferSMP.instance, true, true);
		registerConnHandler();

		loadConfig();
		loadItemTextures();

		initItem();

		addNames();
		addRecipes();
		return true;
	}

	private static void addNames() {
		ModLoader.addName(itemSniffer, "Wireless Sniffer");
	}

	private static void initItem() {
		itemSniffer = (new ItemRedstoneWirelessSniffer(sniffID - 256))
				.setItemName("sniffer");
	}

	private static void registerConnHandler() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
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
		if (snifferReceiver != null) {
			if (snifferReceiver.isBeingHeld()) {
				PacketHandlerWirelessSniffer.PacketHandlerOutput
						.sendWirelessSnifferEtherCopy(entityplayer,
								getActiveFrequencies(world));
				return;
			}
			deactivateSniffer(world, entityplayer);
		}
		snifferReceiver = new WirelessSnifferDevice(world, entityplayer);
		snifferReceiver.activate();
	}

	public static boolean deactivateSniffer(World world,
			EntityPlayer entityplayer) {
		if (snifferReceiver == null) {
			return false;
		} else {
			snifferReceiver.deactivate();
			return true;
		}
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			WirelessSnifferData deviceData) {
		PacketHandlerWirelessSniffer.PacketHandlerOutput
				.sendWirelessSnifferGuiPacket(entityplayer, deviceData.getID());
		activateSniffer(world, entityplayer);
	}

	public static boolean onTickInGame(MinecraftServer mcServer) {
		if (snifferReceiver != null) {
			if (ticksInGame >= 20) {
				activateSniffer(snifferReceiver.getWorld(),
						snifferReceiver.getOwner());
				ticksInGame = 0;
				return true;
			}
			ticksInGame += 1;
		}
		return true;
	}
}
