package net.minecraft.src.wirelessredstone.addon.sniffer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessSniffer;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferData;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferDevice;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;

public class WirelessSniffer {
	public static boolean isLoaded = false;
	public static Item itemSniffer;
	public static GuiRedstoneWirelessSniffer guiSniffer;
	public static WirelessSnifferDevice snifferReceiver;
	public static int snifferon, snifferoff;
	public static List<BaseModOverride> overrides;
	public static int sniffID = 6244;

	public static boolean initialize() {
		try {
			overrides = new ArrayList();
			ModLoader.setInGameHook(mod_WirelessSniffer.instance, true, true);
					
			loadConfig();
			loadItemTextures();

			initItem();
			initGui();

			addNames();
			addRecipes();
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessSniffer.class.toString()))
					.write("Initialization failed.",
							LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}
	
	private static void initGui() {
		guiSniffer = new GuiRedstoneWirelessSniffer();
	}

	public static void addOverride(BaseModOverride override) {
		overrides.add(override);
	}

	private static void loadConfig() {
		sniffID = (Integer) ConfigStoreRedstoneWireless.getInstance("Sniffer")
				.get("ID", Integer.class, new Integer(sniffID));
	}

	public static void loadItemTextures() {
		snifferoff = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/sniff.png");
	}

	private static void initItem() {
		itemSniffer = (new ItemRedstoneWirelessSniffer(sniffID - 256))
				.setItemName("wirelessredstone.sniffer");
	}

	private static void addNames() {
		ModLoader.addName(itemSniffer, "Wireless Sniffer");
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemSniffer, 1), new Object[] {
				"X X", " X ", "X X", Character.valueOf('X'),
				WirelessRedstone.blockWirelessR });
	}

	public static WirelessSnifferData getDeviceData(String index, int id,
			String name, World world, EntityPlayer entityplayer) {
		index = index + "[" + id + "]";
		WirelessSnifferData data = (WirelessSnifferData) world.loadItemData(
				WirelessSnifferData.class, index);
		if (data == null) {
			data = new WirelessSnifferData(index);
			world.setItemData(index, data);
			data.setID(id);
			data.setName(name);
			data.setDimension(world);
			data.setPage(0);
		}
		return data;
	}

	public static WirelessSnifferData getDeviceData(ItemStack itemstack,
			World world, EntityPlayer entityplayer) {
		String index = itemstack.getItem().getItemName();
		int id = itemstack.getItemDamage();
		String name = itemstack.getItem().getItemDisplayName(itemstack);
		return getDeviceData(index, id, name, world, entityplayer);
	}
	
	public static void activateGUI(WirelessSnifferData data, World world,
			EntityPlayer entityplayer) {
		guiSniffer.assWirelessDevice(data, entityplayer);
		activateSniffer(world, entityplayer);
		ModLoader.openGUI(entityplayer, guiSniffer);
	}

	public static void openGUI(WirelessSnifferData data, World world,
			EntityPlayer entityplayer) {
		boolean prematureExit = false;
		
		for (BaseModOverride override : overrides) {
			if (override.beforeOpenGui(world, entityplayer, data)) {
				prematureExit = true;
			}
		}
		
		if (!prematureExit)
			activateGUI(data, world, entityplayer);
	}
	
	public static void activateSniffer(World world, EntityPlayer entityplayer) {
		if (snifferReceiver != null) {
			if (snifferReceiver.isBeingHeld()) {
				return;
			}
			deactivateSniffer(world, entityplayer);
		}
		snifferReceiver = new WirelessSnifferDevice(world, entityplayer);
		snifferReceiver.activate();
	}
	
	public static boolean deactivateSniffer(World world, EntityPlayer entityplayer) {
		if (snifferReceiver == null) {
			return false;
		} else {
			snifferReceiver.deactivate();
			return true;
		}
	}

	public static void tick(float tick, Minecraft mc) {
		processSniffer(mc.theWorld, mc.thePlayer, mc.currentScreen,
				mc.objectMouseOver);
	}

	private static void processSniffer(World world,
			EntityPlayer entityplayer, GuiScreen gui,
			MovingObjectPosition mop) {
		if (snifferReceiver != null && (gui == null || !(gui instanceof GuiRedstoneWirelessSniffer))) {
			deactivateSniffer(world, entityplayer);
		}
	}
}
