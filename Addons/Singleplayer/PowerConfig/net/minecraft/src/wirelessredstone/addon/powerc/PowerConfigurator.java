package net.minecraft.src.wirelessredstone.addon.powerc;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.BaseModOverride;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.powerc.overrides.BlockRedstoneWirelessROverridePC;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class PowerConfigurator {
	public static boolean isLoaded = false;
	public static Item itemPowDir;
	public static int pdID = 6243;
	private static List<BaseModOverride> overrides;

	public static boolean initialize() {
		try {
			overrides = new ArrayList<BaseModOverride>();
			loadConfig();
			itemPowDir = (new ItemRedstoneWirelessPowerDirector(pdID))
					.setItemName("powdir");
			loadItemTextures();
			addRecipes();
			ModLoader.addName(itemPowDir, "Power Configurator");
			WirelessRedstone
					.addOverrideToReceiver(new BlockRedstoneWirelessROverridePC());
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless
					.getInstance(
							LoggerRedstoneWireless
									.filterClassName(PowerConfigurator.class
											.toString())).write(
							"Initialization failed.",
							LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}

	public static void loadItemTextures() {
		itemPowDir.setIconIndex(ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/pd.png"));
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemPowDir, 1), new Object[] { "R R",
				" X ", "R R", Character.valueOf('X'),
				WirelessRedstone.blockWirelessR, Character.valueOf('R'),
				Item.redstone });
	}

	private static void loadConfig() {
		pdID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"PowerConfigurator")
				.get("ID", Integer.class, new Integer(pdID));
	}

	public void addOverride(BaseModOverride override) {
		overrides.add(override);
	}

	public static void openGUI(EntityPlayer entityplayer, World world,
			TileEntity tileentity) {
		boolean prematureExit = false;
		for (BaseModOverride override : overrides) {
			if (override.beforeOpenGui(entityplayer, world, tileentity))
				prematureExit = true;
		}
		if (!prematureExit)
			if (tileentity instanceof TileEntityRedstoneWirelessR)
				ModLoader.openGUI(entityplayer,
						new GuiRedstoneWirelessPowerDirector(
								(TileEntityRedstoneWirelessR) tileentity));
	}
}
