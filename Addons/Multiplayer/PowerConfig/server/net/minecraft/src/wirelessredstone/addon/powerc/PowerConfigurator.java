package net.minecraft.src.wirelessredstone.addon.powerc;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_PowerConfiguratorSMP;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.powerc.smp.network.PacketHandlerPowerConfig;
import net.minecraft.src.wirelessredstone.addon.powerc.smp.network.PowerConfigConnection;
import net.minecraft.src.wirelessredstone.addon.powerc.smp.overrides.BlockRedstoneWirelessROverridePC;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class PowerConfigurator {
	public static boolean isLoaded = false;
	public static Item itemPowDir;
	public static int pdID = 6243;
	public static int spritePowerC;
	public static NetworkConnections powerConfigConnection;

	public static boolean initialize() {
		try {
			registerConnHandler();

			loadConfig();
			loadItemTextures();

			initItem();

			addRecipes();
			addOverrides();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static void addOverrides() {
		WirelessRedstone
				.addOverrideToReceiver(new BlockRedstoneWirelessROverridePC());
	}

	private static void registerConnHandler() {
		powerConfigConnection = new PowerConfigConnection("WIFI-POWERC");
		powerConfigConnection.onLogin(null, null,
				mod_PowerConfiguratorSMP.instance);
	}

	private static void initItem() {
		itemPowDir = (new ItemRedstoneWirelessPowerDirector(pdID))
				.setItemName("wirelessredstone.powdir");
		ModLoader.addName(itemPowDir, "Power Configurator");
	}

	public static void loadItemTextures() {
		spritePowerC = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/pd.png");
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

	public static void openGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		if (tileentity instanceof TileEntityRedstoneWirelessR)
			PacketHandlerPowerConfig.PacketHandlerOutput
					.sendPowerConfigGuiPacket((EntityPlayerMP) entityplayer,
							(TileEntityRedstoneWirelessR) tileentity);
	}
}
