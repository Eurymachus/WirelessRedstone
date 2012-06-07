package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class WirelessTriangulator
{
	public static final boolean isServer = false;
	public static boolean isLoaded = false;
	public static Item itemTriang;
	public static int triangID=6246;
	
	public static TextureTriangulatorFX tex;
	public static int pulseTime = 2500;
	public static int maxPulseThreads=5;
	
	public static boolean initialize()
	{
		try
		{
			loadConfig();
			itemTriang = (new ItemRedstoneWirelessTriangulator(triangID - 256)).setItemName("triang");
			loadItemTextures();
			addRecipes();
			ModLoader.addName(itemTriang, "Wireless Triangulator");
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(tex);
			return true;
		}
		catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(
			LoggerRedstoneWireless.filterClassName(
			WirelessTriangulator.class.toString()))
			.write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}

	public static void loadItemTextures()
	{
		itemTriang.setIconIndex(ModLoader.getUniqueSpriteIndex("/gui/items.png"));
		tex = new TextureTriangulatorFX(ModLoader.getMinecraftInstance());
	}

	public static void addRecipes() 
	{
		ModLoader.addRecipe(new ItemStack(itemTriang, 1), new Object[] {
            "C", "X", Character.valueOf('X'), WirelessRedstone.blockWirelessR, Character.valueOf('C'), Item.compass
        });
	}

	private static void loadConfig()
	{
		triangID = (Integer) ConfigStoreRedstoneWireless.getInstance("Triangulator").get("ID", Integer.class, new Integer(triangID));
	}

	public static void openGUI(EntityPlayer entityplayer, World world)
	{
		ModLoader.openGUI(entityplayer, new GuiRedstoneWirelessTriangulator(entityplayer, world));
	}
}