package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.triangulator.data.WirelessTriangulatorData;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.PacketHandlerWirelessTriangulator;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class WirelessTriangulator {
	public static boolean isLoaded = false;
	public static Item itemTriang;
	public static int triangID = 6246;
	public static int pulseTime = 2500;
	public static int maxPulseThreads = 5;
	public static boolean isServer = true;

	public static boolean initialize() {
		try {
			registerConnHandler();

			loadConfig();
			loadItemTextures();

			initItem();

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
			return false;
		}
	}

	private static void registerConnHandler() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	private static void addNames() {
		ModLoader.addName(itemTriang, "Wireless Triangulator");
	}

	public static int getIconFromDamage(String itemName, int i) {
		return 0;
	}

	private static void initItem() {
		itemTriang = (new ItemRedstoneWirelessTriangulator(triangID - 256))
				.setItemName("wirelessredstone.triang");
	}

	public static void loadItemTextures() {
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemTriang, 1), new Object[] { "C",
				"X", Character.valueOf('X'), WirelessRedstone.blockWirelessR,
				Character.valueOf('C'), Item.compass });
	}

	private static void loadConfig() {
		triangID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"Triangulator").get("ID", Integer.class, new Integer(triangID));
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
		String name = "Wireless Triangulator";
		return getDeviceData(index, id, name, world, entityplayer);
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			WirelessTriangulatorData deviceData) {
		PacketHandlerWirelessTriangulator.PacketHandlerOutput
				.sendWirelessTriangulatorGuiPacket(entityplayer,
						deviceData.getID());
	}

	/*
	 * public static boolean tick(MinecraftServer mc) { World[] worlds =
	 * DimensionManager.getWorlds(); for (int i = 0; i < worlds.length; i++) {
	 * for (int j = 0; j < worlds[i].playerEntities .size(); j++) {
	 * EntityPlayerMP player = (EntityPlayerMP)worlds[i].playerEntities.get(j);
	 * 
	 * if (Math.abs(player.posX) <= 16 && Math.abs(player.posY) <= 16 &&
	 * Math.abs(player.posZ) <= 16) {
	 * 
	 * } } } return false; }
	 */
}
