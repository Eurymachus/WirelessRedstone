package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;

public class WirelessSniffer
{
	public static Item itemSniffer;
	public static int sniffID=6244;
	
	public static boolean initialize()
	{
		loadConfig();
		itemSniffer = (new ItemRedstoneWirelessSniffer(sniffID - 256)).setItemName("sniffer");
		loadItemTextures();
		ModLoader.addName(itemSniffer, "Wireless Sniffer");
		addRecipes();
		return true;
	}

	public static void loadItemTextures() {
		itemSniffer.setIconIndex(ModLoader.addOverride("/gui/items.png", "/WirelessSprites/sniff.png"));
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemSniffer, 1), new Object[] {
            "X X", " X ", "X X", Character.valueOf('X'), WirelessRedstone.blockWirelessR
        });
	}
	private static void loadConfig() {
		sniffID = (Integer) ConfigStoreRedstoneWireless.getInstance("Sniffer").get("ID", Integer.class, new Integer(sniffID));
	}
}
