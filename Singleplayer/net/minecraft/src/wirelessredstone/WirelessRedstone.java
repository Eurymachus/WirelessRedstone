package net.minecraft.src.wirelessredstone;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;
import net.minecraft.src.wirelessredstone.block.BlockItemRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.block.BlockItemRedstoneWirelessT;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessT;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWireless;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessT;
import net.minecraft.src.wirelessredstone.presentation.TileEntityRedstoneWirelessRenderer;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

/**
 * WirelessRedstone class
 * 
 * To allow abstraction from the BaseMod code
 * 
 * @author Eurymachus & Aliz4
 * 
 */
public class WirelessRedstone {
	/**
	 * Wireless Receiver Block
	 */
	public static Block blockWirelessR;
	/**
	 * Wireless Transmitter Block
	 */
	public static Block blockWirelessT;
	/**
	 * Wireless Receiver GUI
	 */
	public static GuiRedstoneWireless guiWirelessR;
	/**
	 * Wireless Transmitter GUI
	 */
	public static GuiRedstoneWireless guiWirelessT;

	/**
	 * Wireless Receiver Block ID
	 */
	public static int rxID = 127;
	/**
	 * Wireless Transmitter Block ID
	 */
	public static int txID = 126;

	/**
	 * Block texture, top, on state.
	 */
	public static int spriteTopOn;
	/**
	 * Block texture, top, off state.
	 */
	public static int spriteTopOff;
	/**
	 * Wireless Receiver Block texture, on state.
	 */
	public static int spriteROn;
	/**
	 * Wireless Receiver Block texture, off state.
	 */
	public static int spriteROff;
	/**
	 * Wireless Transmitter Block texture, on state.
	 */
	public static int spriteTOn;
	/**
	 * Wireless Transmitter Block texture, off state.
	 */
	public static int spriteTOff;
	/**
	 * Wireless Transmitter Item texture.
	 */
	public static int spriteTItem;
	/**
	 * Wireless Receiver Item texture.
	 */
	public static int spriteRItem;
	/**
	 * Wireless Redstone Ether maximum nodes
	 */
	public static int maxEtherFrequencies = 10000;
	
	public static List<BaseModOverride> overrides;
	
	/**
	 * Loads configurations and initializes objects. Loads ModLoader related
	 * stuff.<br>
	 * - Load Block textures<br>
	 * - Register Blocks and Tile Entities<br>
	 * - Recipes
	 */
	public static void initialize() {
		overrides = new ArrayList<BaseModOverride>();
		loadConfig();
		initBlocks();
		initGUIs();
		loadBlockTextures();
		loadItemTextures();
		registerBlocks();
		addRecipes();
	}

	/**
	 * Loads all Block textures from ModLoader override and stores the indices
	 * into the sprite integers.
	 */
	public static void loadBlockTextures() {
		spriteTopOn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/topOn.png");
		spriteTopOff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/topOff.png");
		spriteROn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/rxOn.png");
		spriteROff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/rxOff.png");
		spriteTOn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/txOn.png");
		spriteTOff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/txOff.png");
	}

	/**
	 * Loads all Item textures from ModLoader override and stores the indices
	 * into the sprite integers.
	 */
	public static void loadItemTextures() {
		spriteTItem = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/txOn.png");
		spriteRItem = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/rxOn.png");
	}

	/**
	 * Initializes Block objects.
	 */
	public static void initBlocks() {
		blockWirelessR = (new BlockRedstoneWirelessR(rxID, 1.0F, 8.0F))
				.setBlockName("wifir");
		blockWirelessT = (new BlockRedstoneWirelessT(txID, 1.0F, 8.0F))
				.setBlockName("wifit");
	}

	/**
	 * Initializes GUI objects.
	 */
	public static void initGUIs() {
		guiWirelessR = new GuiRedstoneWirelessR();
		guiWirelessT = new GuiRedstoneWirelessT();
	}

	/**
	 * Registers the Blocks and TileEntities with ModLoader
	 */
	public static void registerBlocks() {
		ModLoader.registerBlock(blockWirelessR,
				BlockItemRedstoneWirelessR.class);
		ModLoader.addName(blockWirelessR, "Wireless Receiver");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessR.class,
				"Wireless Receiver", new TileEntityRedstoneWirelessRenderer());

		ModLoader.registerBlock(blockWirelessT,
				BlockItemRedstoneWirelessT.class);
		ModLoader.addName(blockWirelessT, "Wireless Transmitter");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessT.class,
				"Wireless Transmitter",
				new TileEntityRedstoneWirelessRenderer());
	}

	/**
	 * Registers receipts with ModLoader
	 */
	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(blockWirelessR, 1), new Object[] {
				"IRI", "RLR", "IRI", Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone, Character.valueOf('L'),
				Block.lever });
		ModLoader.addRecipe(new ItemStack(blockWirelessT, 1), new Object[] {
				"IRI", "RTR", "IRI", Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone, Character.valueOf('T'),
				Block.torchRedstoneActive });
	}

	/**
	 * Loads configurations from the properties file.<br>
	 * - Receiver block ID: (Receiver.ID)<br>
	 * - Transmitter block ID: (Transmitter.ID)<br>
	 */
	private static void loadConfig() {
		rxID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Receiver.ID", Integer.class,
				new Integer(rxID));
		txID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Transmitter.ID", Integer.class,
				new Integer(txID));
	}

	/**
	 * Retrieves the world object without parameters
	 * 
	 * @return the world
	 */
	public static World getWorld() {
		return ModLoader.getMinecraftInstance().theWorld;
	}

	/**
	 * Retrieves the world object
	 * 
	 * @param network
	 *            the Network manager (used for Server)
	 * @return the world
	 */
	public static World getWorld(NetworkManager network) {
		return getWorld();
	}

	/**
	 * Retrieves the player object
	 * 
	 * @param network
	 *            the Network manager (used for Server)
	 * @return the player
	 */
	public static EntityPlayer getPlayer(NetworkManager network) {
		return ModLoader.getMinecraftInstance().thePlayer;
	}

/*	public static WirelessDeviceData getWirelessData(WirelessDeviceData wirelessDeviceData, ItemStack itemstack, World world, EntityPlayer entityplayer) {
		wirelessDeviceData.setDeviceID(itemstack);
		wirelessDeviceData.setDeviceName(itemstack.getItemNameandInformation().get(0).toString());
		wirelessDeviceData.setDeviceOwner(entityplayer);
		wirelessDeviceData.setDeviceDimension(world);
		wirelessDeviceData.setDeviceFreq(RedstoneWirelessItemStackMem.getInstance(world).getFreq(itemstack));
		wirelessDeviceData.markDirty();
		return wirelessDeviceData;
	}*/
	
	public static void addOverride(BaseModOverride override) {
		overrides.add(override);
	}

	public static void openGUI(EntityPlayer entityplayer, World world, TileEntity tileentity) {
		boolean prematureExit = false;
		for (BaseModOverride override : overrides) {
			if (override.beforeOpenGui(entityplayer, world, tileentity))
				prematureExit = true;
		}
		
		if (!prematureExit)
			if (tileentity instanceof TileEntityRedstoneWirelessR) {
				guiWirelessR.assTileEntity((TileEntityRedstoneWirelessR)tileentity);
				ModLoader.openGUI(entityplayer, guiWirelessR);
			}
			if (tileentity instanceof TileEntityRedstoneWirelessT) {
				guiWirelessT.assTileEntity((TileEntityRedstoneWirelessT)tileentity);
				ModLoader.openGUI(entityplayer, guiWirelessT);
			}
	}	
}
