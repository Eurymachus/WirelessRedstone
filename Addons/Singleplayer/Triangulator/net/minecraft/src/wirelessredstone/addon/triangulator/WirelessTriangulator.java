package net.minecraft.src.wirelessredstone.addon.triangulator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.WorldSavedData;
import net.minecraft.src.mod_WirelessTriangulator;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.triangulator.data.WirelessTriangulatorData;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;

public class WirelessTriangulator {
	public static final boolean isServer = false;
	public static boolean isLoaded = false;
	public static int triangSprite;
	public static Item itemTriang;
	public static int triangID = 6246;
	public static GuiRedstoneWirelessTriangulator guiTriang;
	public static TextureTriangulatorFX tex;
	// public static TextureTriangulatorFX[] tex = new
	// TextureTriangulatorFX[WirelessRedstone.maxEtherFrequencies];
	public static int pulseTime = 2500;
	public static int maxPulseThreads = 5;
	private static List<BaseModOverride> overrides;

	public static boolean initialize() {
		try {
			ModLoader.setInGameHook(mod_WirelessTriangulator.instance, true,
					true);
			overrides = new ArrayList();

			loadConfig();

			initGui();
			initItem();

			loadItemTextures();
			addRecipes();
			addNames();
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessTriangulator.class
									.toString())).write(
					"Initialization failed.",
					LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}

	private static void initGui() {
		guiTriang = new GuiRedstoneWirelessTriangulator();
	}

	private static void initItem() {
		itemTriang = (new ItemRedstoneWirelessTriangulator(triangID - 256))
				.setItemName("wirelessredstone.triang");
	}

	public static void loadItemTextures() {
		triangSprite = ModLoader.getUniqueSpriteIndex("/gui/items.png");
		tex = new TextureTriangulatorFX(ModLoader.getMinecraftInstance(), 0);
		ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(tex);
		/*
		 * for (int i = 0; i < WirelessRedstone.maxEtherFrequencies; ++i) {
		 * tex[i] = new TextureTriangulatorFX(ModLoader.getMinecraftInstance(),
		 * i);
		 * ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(tex
		 * [i]); }
		 */
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemTriang, 1), new Object[] { "C",
				"X", Character.valueOf('X'), WirelessRedstone.blockWirelessR,
				Character.valueOf('C'), Item.compass });
	}

	private static void addNames() {
		ModLoader.addName(itemTriang, "Wireless Triangulator");
	}

	public static WirelessTriangulatorData getDeviceData(String index, int id,
			String name, World world, EntityPlayer entityplayer) {
		index = index + "[" + id + "]";
		WirelessTriangulatorData data = (WirelessTriangulatorData) world
				.loadItemData(WirelessTriangulatorData.class, index);
		if (data == null) {
			data = new WirelessTriangulatorData(index);
			world.setItemData(index, data);
			data.setID(id);
			data.setName(name);
			data.setDimension(world);
			data.setFreq("0");
		}
		return data;
	}

	public static WirelessTriangulatorData getDeviceData(ItemStack itemstack,
			World world, EntityPlayer entityplayer) {
		String index = itemstack.getItem().getItemName();
		int id = itemstack.getItemDamage();
		String name = itemstack.getItem().getItemDisplayName(itemstack);
		return getDeviceData(index, id, name, world, entityplayer);
	}

	private static void loadConfig() {
		triangID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"Triangulator").get("ID", Integer.class, new Integer(triangID));
	}

	public static void activateGUI(World world, EntityPlayer entityplayer,
			WorldSavedData deviceData) {
		if (deviceData instanceof WirelessTriangulatorData) {
			guiTriang.assWirelessDevice((WirelessTriangulatorData) deviceData,
					entityplayer);
			ModLoader.openGUI(entityplayer, guiTriang);
		}
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			WorldSavedData deviceData) {
		boolean prematureExit = false;
		for (BaseModOverride override : overrides) {
			if (override.beforeOpenGui(world, entityplayer, deviceData))
				prematureExit = true;
		}
		if (!prematureExit)
			activateGUI(world, entityplayer, deviceData);
	};

	public static void onTickInGame(float tick, Minecraft mc) {
	}

	public static int getIconFromDamage(String itemName, int i) {
		return triangSprite;
	}
}