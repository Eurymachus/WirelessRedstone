package net.minecraft.src.clocker;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_WirelessClocker;
import net.minecraft.src.clocker.GuiRedstoneWirelessClocker;
import net.minecraft.src.wifi.ConfigStoreRedstoneWireless;
import net.minecraft.src.wifi.GuiRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessRenderer;
import net.minecraft.src.wifi.WirelessRedstone;

public class WirelessClocker
{
	public static GuiRedstoneWireless guiClock;
	public static Block blockClock;
	
	public static int clockID=128;
	
	public static int spriteSidesOff;
	public static int spriteSidesOn;
	
	public static boolean initialize()
	{
		loadConfig();
		loadBlockTextures();
		blockClock = (new BlockRedstoneWirelessClocker(clockID, 1.0F, 8.0F)).setBlockName("clocker");
		WirelessClocker.guiClock = new GuiRedstoneWirelessClocker();
		addRecipes();
		ModLoader.registerBlock(blockClock);
		ModLoader.addName(blockClock, "Wireless Clocker");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessClocker.class, "Wireless Clocker", new TileEntityRedstoneWirelessClockerRenderer());
		return true;
	}
	
	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(blockClock, 1), new Object[] {
            "R R", " # ", "R R", Character.valueOf('R'), Item.redstoneRepeater, Character.valueOf('#'), WirelessRedstone.blockWirelessT
        });
	}
	
	public static void loadBlockTextures() {
		spriteSidesOff 	= 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/clockerSideOff.png");
		spriteSidesOn 	= 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/clockerSideOn.png");
	}

	private static void loadConfig() {
		clockID = (Integer) ConfigStoreRedstoneWireless.getInstance("Clocker").get("ID", Integer.class, new Integer(clockID));
	}
}
