package net.minecraft.src.wirelessredstone.addon.remote;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessRemote;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteData;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessInventory;

import org.lwjgl.input.Mouse;

public class WirelessRemote {
	public static boolean isLoaded = false;
	public static Item itemRemote;
	public static int remoteon, remoteoff;
	public static int remoteID = 6245;

	public static WirelessRemoteDevice remoteTransmitter;

	public static boolean mouseDown, wasMouseDown, remotePulsing;

	public static long pulseTime = 2500;
	public static boolean duraTogg = true;
	public static int maxPulseThreads = 1;
	static int ticksInGui;

	public static boolean initialize() {
		try {
			ModLoader.setInGameHook(mod_WirelessRemote.instance, true, true);
			
			loadConfig();
			
			initListeners();
			initItems();
			
			loadItemTextures();
			addRecipes();
			addNames();
			
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(WirelessRemote.class
							.toString())).write("Initialization failed.",
					LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}

	private static void loadConfig() {
		remoteID = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("ID", Integer.class, new Integer(remoteID));
		duraTogg = (Boolean) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("Durability", Boolean.class, new Boolean(duraTogg));
		pulseTime = (Long) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("PulseDuration", Long.class, new Long(pulseTime));
		maxPulseThreads = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"Remote").get("MaxPulseThreads", Integer.class,
				new Integer(maxPulseThreads));
	}

	private static void initItems() {
		itemRemote = (new ItemRedstoneWirelessRemote(remoteID - 256))
				.setItemName("wirelessredstone.remote");
	}

	private static void initListeners() {
		mouseDown = false;
		wasMouseDown = false;
		remotePulsing = false;
	}

	public static void loadItemTextures() {
		remoteoff = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/remoteoff.png");
		remoteon = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/remote.png");
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemRemote, 1), new Object[] { "i",
				"#", Character.valueOf('i'), Block.torchRedstoneActive,
				Character.valueOf('#'), WirelessRedstone.blockWirelessT });
	}

	public static void addNames() {
		ModLoader.addName(itemRemote, "Wireless Remote");
	}

	public static void openGUI(EntityPlayer entityplayer, World world) {
		ModLoader.openGUI(entityplayer, new GuiRedstoneWirelessRemote(world,
				entityplayer));
	}

	public static void activateRemote(World world, EntityPlayer entityplayer) {
		if (remoteTransmitter != null) {
			if (remoteTransmitter.isBeingHeld())
				return;

			deactivateRemote(world, entityplayer);
		}
		remoteTransmitter = new WirelessRemoteDevice(world, entityplayer);
		remoteTransmitter.activate();
	}

	public static boolean deactivateRemote(World world,
			EntityPlayer entityplayer) {
		if (remoteTransmitter == null) {
			return false;
		} else {
			remoteTransmitter.deactivate();
			remoteTransmitter = null;
			return true;
		}
	}

	public static void processRemote(World world, EntityPlayer entityplayer,
			GuiScreen gui, MovingObjectPosition mop) {
		if (remoteTransmitter != null && !mouseDown && !remotePulsing) {
			ThreadWirelessRemote.pulse(entityplayer, "hold");
			deactivateRemote(world, entityplayer);
		}

		if (mouseClicked()
				&& remoteTransmitter == null
				&& entityplayer.inventory.getCurrentItem() != null
				&& entityplayer.inventory.getCurrentItem().getItem() == WirelessRemote.itemRemote
				&& gui != null && gui instanceof GuiRedstoneWirelessInventory
				&& !entityplayer.isSneaking() && WirelessRemote.ticksInGui > 0)
			activateRemote(world, entityplayer);
	}

	public static boolean isRemoteOn(EntityPlayer entityplayer, String freq) {
		return remoteTransmitter == null ? false
				: remoteTransmitter.getFreq() == freq;
	}

	public static WirelessRemoteData getDeviceData(String index, int id,
			String name, World world, EntityPlayer entityplayer) {
		index = index + "[" + id + "]";
		WirelessRemoteData data = (WirelessRemoteData) world.loadItemData(
				WirelessRemoteData.class, index);
		if (data == null) {
			data = new WirelessRemoteData(index, id, name, world, entityplayer);
			world.setItemData(index, data);
		}
		return data;
	}

	public static WirelessRemoteData getDeviceData(ItemStack itemstack,
			World world, EntityPlayer entityplayer) {
		String index = itemstack.getItem().getItemName();
		int id = itemstack.getItemDamage();
		String name = "Wireless Remote";
		return getDeviceData(index, id, name, world, entityplayer);
	}

	public static boolean mouseClicked() {
		return mouseDown && !wasMouseDown;
	}

	public static void checkMouseClicks() {
		wasMouseDown = mouseDown;
		mouseDown = Mouse.isButtonDown(1);
	}

	public static void tick(Minecraft mc) {
		checkMouseClicks();
		processRemote(mc.theWorld, mc.thePlayer, mc.currentScreen,
				mc.objectMouseOver);

		if (mc.currentScreen == null)
			ticksInGui = 0;
		else
			++ticksInGui;
	}

	public static int getIconFromDamage(String name, int i) {
		String index = name + "[" + i + "]";
		WirelessRemoteData data = (WirelessRemoteData) WirelessRedstone
				.getWorld().loadItemData(WirelessRemoteData.class, index);
		if (data == null || !data.getState())
			return WirelessRemote.remoteoff;
		return WirelessRemote.remoteon;
	}
}
