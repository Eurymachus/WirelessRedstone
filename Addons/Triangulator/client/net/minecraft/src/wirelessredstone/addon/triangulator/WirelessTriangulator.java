package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;

public class WirelessTriangulator {
	public static Item itemTriang;
	public static int triangID=6246;
	
	public static TextureTriangulatorFX tex;
	
	public static boolean initialize()
	{
		loadConfig();
		itemTriang = (new ItemRedstoneWirelessTriangulator(triangID - 256)).setItemName("triang");
		loadItemTextures();
		AddRecipes();
		ModLoader.addName(itemTriang, "Wireless Triangulator");
		ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(tex);
		return true;
	}

	public static void loadItemTextures() {
		itemTriang.setIconIndex(ModLoader.getUniqueSpriteIndex("/gui/items.png"));
		tex = new TextureTriangulatorFX(ModLoader.getMinecraftInstance());
	}

	public static void AddRecipes() {
		ModLoader.addRecipe(new ItemStack(itemTriang, 1), new Object[] {
            "C", "X", Character.valueOf('X'), WirelessRedstone.blockWirelessR, Character.valueOf('C'), Item.compass
        });
	}

	private static void loadConfig() {
		triangID = (Integer) ConfigStoreRedstoneWireless.getInstance("Triangulator").get("ID", Integer.class, new Integer(triangID));
	}
}
