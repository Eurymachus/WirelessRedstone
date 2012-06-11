package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferData;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;

public class WirelessSniffer {
	public static boolean isLoaded = false;
	public static Item itemSniffer;
	public static int snifferon, snifferoff;
	public static int sniffID = 6244;

	public static boolean initialize() {
		try {
			loadConfig();
			itemSniffer = (new ItemRedstoneWirelessSniffer(sniffID - 256))
					.setItemName("wirelessredstone.sniffer");
			loadItemTextures();
			ModLoader.addName(itemSniffer, "Wireless Sniffer");
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

	public static void loadItemTextures() {
		snifferoff = ModLoader.addOverride("/gui/items.png",
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

	public static WirelessSnifferData newSnifferDevice(String fullitemname, String indexname, int id,
			World world, EntityPlayer entityplayer) {
		String snifferName = indexname + "_" + id;
		WirelessSnifferData wirelessSnifferData = new WirelessSnifferData(snifferName);
		world.setItemData(snifferName, wirelessSnifferData);
		wirelessSnifferData.setID(id);
		wirelessSnifferData.setName(fullitemname);
		wirelessSnifferData.setDimension(world);
		wirelessSnifferData.setFreq("0");
		return wirelessSnifferData;
	}

	public static void openGUI(WirelessSnifferData data, World world, EntityPlayer entityplayer) {
		GuiRedstoneWirelessSniffer guiSniffer = new GuiRedstoneWirelessSniffer(
				data, world, entityplayer);
		ModLoader.openGUI(entityplayer, guiSniffer);
	}
}
