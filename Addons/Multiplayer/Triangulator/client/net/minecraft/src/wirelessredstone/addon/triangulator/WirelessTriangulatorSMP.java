package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.wirelessredstone.addon.triangulator.smp.overrides.GuiRedstoneWirelessTriangulatorOverrideSMP;
import net.minecraft.src.wirelessredstone.addon.triangulator.smp.overrides.RedstoneEtherOverrideTriangulator;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
import net.minecraft.src.wirelessredstone.smp.overrides.BaseModOverrideSMP;

public class WirelessTriangulatorSMP {
	public static boolean isLoaded = false;
	private static int ticksInGame = 0;
	private static int ticksInGUI = 0;
	public static NetworkConnections triangulatorConnection;
	public static String channel = "WR-TRIANG";

	public static boolean initialize() {
		try {
			// ModLoader.setInGameHook(mod_WirelessTriangulator.instance, true,
			// true);

			addBaseModOverride();

			addGuiOverride();

			addEtherOverride();

			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessTriangulatorSMP.class
									.toString())).write(
					"Initialization failed.",
					LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}

	private static void addEtherOverride() {
		RedstoneEtherOverrideTriangulator override = new RedstoneEtherOverrideTriangulator();
		RedstoneEther.getInstance().addOverride(override);
	}

	private static void addGuiOverride() {
		GuiRedstoneWirelessTriangulatorOverrideSMP override = new GuiRedstoneWirelessTriangulatorOverrideSMP();
		WirelessTriangulator.guiTriang.addOverride(override);
	}

	private static void addBaseModOverride() {
		BaseModOverrideSMP override = new BaseModOverrideSMP();
		WirelessTriangulator.addOverride(override);
	}

	/*
	 * public static boolean tick(Minecraft mc) { EntityPlayer entityplayer =
	 * mc.thePlayer; World world = mc.theWorld; if (mc.currentScreen instanceof
	 * GuiRedstoneWirelessTriangulator) { ++ticksInGUI; return true; } else if
	 * (ticksInGUI != 0) { ticksInGUI = 0; } if (!(ticksInGUI > 0)) { if
	 * (ticksInGame == 40) { ticksInGame = 0; if (entityplayer.inventory
	 * .hasItem(WirelessTriangulator.triangID) &&
	 * entityplayer.inventory.getCurrentItem().equals(
	 * WirelessTriangulator.itemTriang)) { WirelessTriangulatorData data =
	 * WirelessTriangulator .getDeviceData(
	 * entityplayer.inventory.getCurrentItem(), world, entityplayer); if
	 * (mc.theWorld.isRemote)
	 * PacketHandlerWirelessTriangulator.PacketHandlerOutput
	 * .sendWirelessTriangulatorPacket(mc.thePlayer, "requestTriangulation",
	 * data.getFreq()); } } ++ticksInGame; return true; } return true; }
	 */
}
