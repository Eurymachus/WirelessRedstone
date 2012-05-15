package net.minecraft.src.wirelessredstone.addon.powerc;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;

public class PowerConfigurator
{
	public static Item itemPowDir;
	public static int pdID=6243;
	
	public static boolean initialize()
	{
		try
		{
			loadConfig();
			itemPowDir = (new ItemRedstoneWirelessPowerDirector(pdID)).setItemName("powdir");
			loadItemTextures();
			addRecipes();
			ModLoader.addName(itemPowDir, "Power Configurator");
			mod_WirelessRedstone.addOverrideToReceiver(new BlockRedstoneWirelessROverridePC());
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
	
	public static void loadItemTextures()
	{
		itemPowDir.setIconIndex(ModLoader.addOverride("/gui/items.png", "/WirelessSprites/pd.png"));
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemPowDir, 1), new Object[] {
            "R R", " X ", "R R", Character.valueOf('X'), WirelessRedstone.blockWirelessR, Character.valueOf('R'), Item.redstone
        });
	}

	private static void loadConfig() {
		pdID = (Integer) ConfigStoreRedstoneWireless.getInstance("PowerConfigurator").get("ID", Integer.class, new Integer(pdID));
	}
}
