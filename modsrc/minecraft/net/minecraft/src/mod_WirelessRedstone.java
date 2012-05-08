/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
*/
package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import net.minecraft.client.Minecraft;

/**
 * Wireless Redstone ModLoader initializing class.
 * 
 * @author ali4z
 */
public class mod_WirelessRedstone extends BaseMod {
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
	public static int rxID=127;
	/**
	 * Wireless Transmitter Block ID
	 */
	public static int txID=126;
	
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
	 * Instance object.
	 */
	public static BaseMod instance;
	
	/**
	 * Constructor sets the instance.
	 */
	public mod_WirelessRedstone() {
		instance = this;
	}
	
	/**
	 * Contains the mod's version.
	 */
	@Override
	public String getVersion() {
		return "1.5";
	}

	/**
	 * Returns the mod's name.
	 */
	@Override
	public String toString() {
		return "WirelessRedstone "+getVersion();
	}
	
	/**
	 * Loads ModLoader related stuff.<br>
	 * - Load Block textures<br>
	 * - Register Blocks and Tile Entities<br>
	 * - Recipes
	 */
	@Override
	public void load() {
		loadBlockTextures();
		loadItemTextures();
		registerBlocks();
		addReceipts();
	}

	/**
	 * Adds a Block override to the Receiver.
	 * 
	 * @param override Block override
	 */
	public static void addOverrideToReceiver(BlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+blockWirelessR.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		((BlockRedstoneWireless)blockWirelessR).addOverride(override);
	}
	
	/**
	 * Adds a Block override to the Transmitter.
	 * 
	 * @param override Block override
	 */
	public static void addOverrideToTransmitter(BlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+blockWirelessT.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		((BlockRedstoneWireless)blockWirelessT).addOverride(override);
	}

	/**
	 * Adds a GUI override to the Receiver.
	 * 
	 * @param override GUI override
	 */
	public static void addGuiOverrideToReceiver(GuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+guiWirelessR.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		guiWirelessR.addOverride(override);
	}

	/**
	 * Adds a GUI override to the Transmitter.
	 * 
	 * @param override GUI override
	 */
	public static void addGuiOverrideToTransmitter(GuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+guiWirelessT.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		guiWirelessT.addOverride(override);
	}

	/**
	 * Loads all Block textures from ModLoader override and stores the indices into the sprite integers.
	 */
	public void loadBlockTextures() {
		spriteTopOn = 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/topOn.png");
		spriteTopOff = 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/topOff.png");
		spriteROn = 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/rxOn.png");
		spriteROff =	ModLoader.addOverride("/terrain.png", "/WirelessSprites/rxOff.png");
		spriteTOn =		ModLoader.addOverride("/terrain.png", "/WirelessSprites/txOn.png");
		spriteTOff =	ModLoader.addOverride("/terrain.png", "/WirelessSprites/txOff.png");
	}

	/**
	 * Loads all Item textures from ModLoader override and stores the indices into the sprite integers.
	 */
	public void loadItemTextures() {
		spriteTItem =	ModLoader.addOverride("/gui/items.png", "/WirelessSprites/txOn.png");
		spriteRItem =	ModLoader.addOverride("/gui/items.png", "/WirelessSprites/rxOn.png");
	}
	
	/**
	 * Initializes Block objects.
	 */
	public static void initBlocks() {
		blockWirelessR = (new BlockRedstoneWirelessR(rxID)).setHardness(1.0F).setStepSound(Block.soundMetalFootstep).setBlockName("wifir");
		blockWirelessT = (new BlockRedstoneWirelessT(txID)).setHardness(1.0F).setStepSound(Block.soundMetalFootstep).setBlockName("wifit");
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
	public void registerBlocks() {
		ModLoader.registerBlock(blockWirelessR, BlockItemRedstoneWirelessR.class);
		ModLoader.addName(blockWirelessR, "Wireless Receiver");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessR.class, "Wireless Receiver", new TileEntityRedstoneWirelessRenderer());
		
		ModLoader.registerBlock(blockWirelessT, BlockItemRedstoneWirelessT.class);
		ModLoader.addName(blockWirelessT, "Wireless Transmitter");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessT.class, "Wireless Transmitter", new TileEntityRedstoneWirelessRenderer());
	}
	
	/**
	 * Registers receipts with ModLoader
	 */
	public void addReceipts() {
		ModLoader.addRecipe(new ItemStack(blockWirelessR, 1), new Object[] {
            "IRI", "RLR", "IRI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('L'), Block.lever
        });
		ModLoader.addRecipe(new ItemStack(blockWirelessT, 1), new Object[] {
            "IRI", "RTR", "IRI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('T'), Block.torchRedstoneActive
        });
	}
	
	/**
	 * Loads configurations from the properties file.<br>
	 * - Receiver block ID: (Receiver.ID)<br>
	 * - Transmitter block ID: (Transmitter.ID)<br>
	 */
	private static void loadConfig() {
		rxID = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Receiver.ID", Integer.class, new Integer(rxID));
		txID = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Transmitter.ID", Integer.class, new Integer(txID));
	}
	
	/**
	 * Loads configurations and initializes objects.
	 */
	static {
		loadConfig();
		initBlocks();
		initGUIs();
	}
}
