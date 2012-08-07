package net.minecraft.src.wirelessredstone.addon.clocker;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.clocker.block.BlockRedstoneWirelessClocker;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.network.PacketHandlerWirelessClocker;
import net.minecraft.src.wirelessredstone.addon.clocker.tileentity.TileEntityRedstoneWirelessClocker;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;

public class WirelessClocker {
	public static boolean wirelessClocker = false;

	public static Block blockClock;

	public static int clockID = 128;

	public static int spriteSidesOff;
	public static int spriteSidesOn;

	public static int maxClockFreq = 2000000000;
	public static int minClockFreq = 200;

	public static NetworkConnections wirelessClockerConnection;

	public static boolean initialize() {
		registerConnHandler();
		loadConfig();
		loadBlockTextures();
		initBlock();
		addRecipes();
		addBlock();
		return true;
	}

	private static void registerConnHandler() {
	}

	private static void loadBlockTextures() {
		spriteSidesOff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/clockerSideOff.png");
		spriteSidesOn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/clockerSideOn.png");
	}

	private static void loadConfig() {
		clockID = (Integer) ConfigStoreRedstoneWireless.getInstance("Clocker")
				.get("ID", Integer.class, new Integer(clockID));
		maxClockFreq = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"Clocker").get("maxFreq", Integer.class,
				new Integer(maxClockFreq));
		minClockFreq = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"Clocker").get("minFreq", Integer.class,
				new Integer(minClockFreq));
	}

	private static void initBlock() {
		blockClock = (new BlockRedstoneWirelessClocker(clockID, 1.0F, 8.0F))
				.setBlockName("wirelessredstone.clocker");
	}

	private static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(blockClock, 1), new Object[] { "R R",
				" # ", "R R", Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('#'), WirelessRedstone.blockWirelessT });
	}

	private static void addBlock() {
		ModLoader.registerBlock(blockClock);
		ModLoader.addName(blockClock, "Wireless Clocker");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessClocker.class,
				"Wireless Clocker");
	}

	public static void openGUI(TileEntityRedstoneWirelessClocker tileentity,
			World world, EntityPlayer entityplayer) {
		PacketHandlerWirelessClocker.PacketHandlerOutput
				.sendWirelessClockerGuiPacket(entityplayer,
						tileentity.getClockFreq(), tileentity.xCoord,
						tileentity.yCoord, tileentity.zCoord);

	}
}
