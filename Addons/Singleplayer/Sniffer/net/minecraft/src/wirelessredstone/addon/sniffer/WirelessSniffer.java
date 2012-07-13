package net.minecraft.src.wirelessredstone.addon.sniffer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessSniffer;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferData;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferDevice;
import net.minecraft.src.wirelessredstone.addon.sniffer.presentation.GuiRedstoneWirelessSniffer;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;

public class WirelessSniffer {
	public static boolean isLoaded = false;
	public static Item itemSniffer;
	public static GuiRedstoneWirelessSniffer guiSniffer;
	public static WirelessSnifferDevice snifferReceiver;
	public static int snifferOn, snifferOff;
	public static int ticksInGame = 0;
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
		snifferOff = ModLoader.addOverride("/gui/items.png",
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

	public static String[] getActiveFrequencies(World world) {
		String[] activeFreqs = new String[WirelessRedstone.maxEtherFrequencies];
		int j = 0;
		for (int i = 0; i < WirelessRedstone.maxEtherFrequencies; ++i) {
			if (RedstoneEther.getInstance().getFreqState(world,
					String.valueOf(i))) {
				activeFreqs[j] = String.valueOf(i);
				++j;
			}
		}
		String[] newActiveFreqs = new String[j];
		for (int i = 0; i < j; ++i) {
			newActiveFreqs[i] = activeFreqs[i];
		}
		return newActiveFreqs;
	}

	public static void activateGUI(World world, EntityPlayer entityplayer,
			WirelessSnifferData wirelessSnifferData) {
		guiSniffer.assWirelessDevice(wirelessSnifferData, entityplayer);
		ModLoader.openGUI(entityplayer, guiSniffer);
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			WirelessSnifferData wirelessSnifferData) {
		boolean prematureExit = false;

		for (BaseModOverride override : overrides) {
			if (override
					.beforeOpenGui(world, entityplayer, wirelessSnifferData)) {
				prematureExit = true;
			}
		}

		if (!prematureExit)
			activateSniffer(world, entityplayer);
		guiSniffer.setActiveFreqs(getActiveFrequencies(world));
		activateGUI(world, entityplayer, wirelessSnifferData);
	}

	public static void activateSniffer(World world, EntityPlayer entityplayer) {
		if (snifferReceiver != null) {
			if (snifferReceiver.isBeingHeld()) {
				guiSniffer.setActiveFreqs(getActiveFrequencies(world));
				return;
			}
			deactivateSniffer(world, entityplayer);
		}
		snifferReceiver = new WirelessSnifferDevice(world, entityplayer);
		snifferReceiver.activate();
	}

	public static boolean deactivateSniffer(World world,
			EntityPlayer entityplayer) {
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

	private static void processSniffer(World world, EntityPlayer entityplayer,
			GuiScreen gui, MovingObjectPosition mop) {
		if (ticksInGame >= 40) {
			if (snifferReceiver != null
					&& (gui == null || !(gui instanceof GuiRedstoneWirelessSniffer))) {
				deactivateSniffer(world, entityplayer);
			}
			if (snifferReceiver != null
					&& gui instanceof GuiRedstoneWirelessSniffer) {
				activateSniffer(world, entityplayer);
			}
			ticksInGame = 0;
		} else
			ticksInGame += 1;
	}

	public static int getIconFromDamage(String itemName, int i) {
		return snifferOff;
	}
}
