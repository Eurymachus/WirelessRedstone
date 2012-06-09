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
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWireless;

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
	protected static List<WirelessRedstoneRemoteOverride> overrides;
	static int ticksInGui;

	public static boolean initialize() {
		try {
			ModLoader.setInGameHook(mod_WirelessRemote.instance, true, true);
			overrides = new ArrayList<WirelessRedstoneRemoteOverride>();
			loadConfig();
			mouseDown = false;
			wasMouseDown = false;
			remotePulsing = false;
			itemRemote = (new ItemRedstoneWirelessRemote(remoteID - 256))
					.setItemName("wirelessredstone.remote");
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

	public static void openGUI(EntityPlayer entityplayer, World world) {
		ModLoader.openGUI(entityplayer, new GuiRedstoneWirelessRemote(
				world, entityplayer));
	}

	public static void activateRemote(World world, EntityPlayer entityplayer) {
		if (remoteTransmitter != null) {
			if (remoteTransmitter.isBeingHeld())
				return;

			deactivateRemote(world, entityplayer);
		}
		remoteTransmitter = new WirelessRemoteDevice(entityplayer, world);
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
				&& gui != null && gui instanceof GuiRedstoneWireless
				&& !entityplayer.isSneaking() && WirelessRemote.ticksInGui > 0)
			activateRemote(world, entityplayer);
	}

	public static boolean isRemoteOn(EntityPlayer entityplayer, String freq) {
		return remoteTransmitter == null ? false : remoteTransmitter.getFreq() == freq;
	}

	public static WirelessRemoteData getRemoteData(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		String remoteName = "remote_"+itemstack.getItemDamage();
		WirelessRemoteData wirelessRemoteData = (WirelessRemoteData)world.loadItemData(WirelessRemoteData.class, remoteName);
		if (wirelessRemoteData == null) {
			wirelessRemoteData = new WirelessRemoteData(remoteName);
			world.setItemData(remoteName, wirelessRemoteData);
			wirelessRemoteData.setDeviceID(itemstack);
			wirelessRemoteData.setDeviceName(itemstack.getItemNameandInformation().get(0).toString());
			wirelessRemoteData.setDeviceOwner(entityplayer);
			wirelessRemoteData.setDeviceDimension(world);
			wirelessRemoteData.setDeviceFreq(RedstoneWirelessItemStackMem.getInstance(world).getFreq(itemstack));
			wirelessRemoteData.markDirty();
		}
		return wirelessRemoteData;
	}

	/**
	 * Adds a Remote override to the Remote.
	 * 
	 * @param override
	 *            Remote override.
	 */
	public void addOverride(WirelessRedstoneRemoteOverride override) {
		overrides.add(override);
	}
	/**
	 * Transmits Wireless Remote Signal
	 * 
	 * @param command
	 *            Command to be used
	 * @param world
	 *            World Transmitted to/from
	 * @param remote
	 *            Remote that is transmitting
	 */
	public static void transmitRemote(String command, World world, WirelessRemoteDevice remote) {
		boolean prematureExit = false;
		for (WirelessRedstoneRemoteOverride override : overrides) {
			prematureExit = override.beforeTransmitRemote(command, world,
					remote);
		}
		if (prematureExit)
			return;

		if (command.equals("deactivateRemote"))
			RedstoneEther.getInstance().remTransmitter(world,
					remote.getCoords().getX(), remote.getCoords().getY(),
					remote.getCoords().getZ(), remote.getFreq());
		else {
			RedstoneEther.getInstance().addTransmitter(world,
					remote.getCoords().getX(), remote.getCoords().getY(),
					remote.getCoords().getZ(), remote.getFreq());
			RedstoneEther.getInstance().setTransmitterState(world,
					remote.getCoords().getX(), remote.getCoords().getY(),
					remote.getCoords().getZ(), remote.getFreq(), true);
		}
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
}
