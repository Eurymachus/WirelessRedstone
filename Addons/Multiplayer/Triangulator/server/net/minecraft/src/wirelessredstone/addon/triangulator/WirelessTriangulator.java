package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessTriangulatorSMP;
import net.minecraft.src.forge.DimensionManager;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.triangulator.data.WirelessTriangulatorData;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.PacketHandlerWirelessTriangulator;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class WirelessTriangulator {
	public static boolean isLoaded = false;
	public static Item itemTriang;
	public static int triangID = 6246;
	public static int pulseTime = 2500;
	public static int maxPulseThreads = 5;
	// public static HashMap<EntityPlayer, WirelessTriangulatorDevice>
	// triangulators;
	public static int ticksInGame = 0;

	public static boolean initialize() {
		try {
			// triangulators = new HashMap<EntityPlayer,
			// WirelessTriangulatorDevice>();
			ModLoader.setInGameHook(mod_WirelessTriangulatorSMP.instance, true,
					true);

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
						deviceData.getID(), deviceData.getFreq());
	}

	public static boolean tick(MinecraftServer mc) {
		if (ticksInGame >= 40) {
			World[] worlds = DimensionManager.getWorlds();
			for (int i = 0; i < worlds.length; i++) {
				for (int j = 0; j < worlds[i].playerEntities.size(); j++) {
					EntityPlayerMP player = (EntityPlayerMP) worlds[i].playerEntities
							.get(j);
					if (player.getCurrentEquippedItem() != null
							&& player.getCurrentEquippedItem().itemID == WirelessTriangulator.itemTriang.shiftedIndex) {
						WirelessTriangulatorData data = WirelessTriangulator
								.getDeviceData(player.getCurrentEquippedItem(),
										player.worldObj, player);
						int tx[] = RedstoneEther.getInstance()
								.getClosestActiveTransmitter((int) player.posX,
										(int) player.posY, (int) player.posZ,
										data.getFreq());
						if (tx == null) {
							PacketHandlerWirelessTriangulator.PacketHandlerOutput
									.sendWirelessTriangulatorZeroPacket(player,
											data.getID());
						} else {
							PacketHandlerWirelessTriangulator.PacketHandlerOutput
									.sendWirelessTriangulatorPacket(player, tx,
											data.getID());
						}
					}
				}
				ticksInGame = 0;
			}
		} else
			ticksInGame += 1;
		return true;
	}

}
