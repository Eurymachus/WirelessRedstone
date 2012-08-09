package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.WirelessRemoteConnection;
import net.minecraft.src.wirelessredstone.addon.remote.smp.overrides.GuiRedstoneWirelessRemoteOverrideSMP;
import net.minecraft.src.wirelessredstone.addon.remote.smp.overrides.WirelessRedstoneRemoteOverrideSMP;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.overrides.BaseModOverrideSMP;

public class WirelessRemoteSMP {
	public static boolean isLoaded = false;
	public static WirelessRemoteConnection wirelessRemoteConnection;
	public static String channel = "WR-REMOTE";

	public static boolean initialize() {
		try {
			addBaseModOverride();
			addRemoteOverride();
			addGuiOveride();

			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless
					.getInstance(
							LoggerRedstoneWireless
									.filterClassName(WirelessRemoteSMP.class
											.toString())).write(
							"Initialization failed.",
							LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}

	private static void addBaseModOverride() {
		BaseModOverrideSMP override = new BaseModOverrideSMP();
		WirelessRemote.addOverride(override);
	}

	private static void addGuiOveride() {
		GuiRedstoneWirelessRemoteOverrideSMP override = new GuiRedstoneWirelessRemoteOverrideSMP();
		WirelessRemote.guiRemote.addOverride(override);
	}

	private static void addRemoteOverride() {
		WirelessRedstoneRemoteOverrideSMP override = new WirelessRedstoneRemoteOverrideSMP();
		WirelessRemoteDevice.addOverride(override);
	}
}
