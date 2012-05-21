package net.minecraft.src.wirelessredstone.addon.clocker;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;

public class WirelessClocker
{
	public static Block blockClock;
	
	public static int clockID=128;
	
	public static int spriteSidesOff;
	public static int spriteSidesOn;
	
	public static boolean initialize()
	{
		loadConfig();
		loadBlockTextures();
		initBlock();
		addRecipes();
		addBlock();
		return true;
	}

	
	private static void loadBlockTextures() 
	{
		spriteSidesOff 	= 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/clockerSideOff.png");
		spriteSidesOn 	= 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/clockerSideOn.png");
	}
	private static void loadConfig() 
	{
		clockID = (Integer) ConfigStoreRedstoneWireless.getInstance("Clocker").get("ID", Integer.class, new Integer(clockID));
	}

	private static void initBlock()
	{
		blockClock = (new BlockRedstoneWirelessClocker(clockID, 1.0F, 8.0F)).setBlockName("clocker");
	}
	
	private static void addRecipes() 
	{
		ModLoader.addRecipe(new ItemStack(blockClock, 1), new Object[] {
            "R R", " # ", "R R", Character.valueOf('R'), Item.redstoneRepeater, Character.valueOf('#'), WirelessRedstone.blockWirelessT
        });
	}
	private static void addBlock()
	{
		ModLoader.registerBlock(blockClock);
		ModLoader.addName(blockClock, "Wireless Clocker");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessClocker.class, "Wireless Clocker");
	}
}
