package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.wirelessredstone.addon.remote.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.remote.overrides.WirelessRedstoneRemoteOverrideSMP;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class WirelessRemoteSMP {
	public static boolean isLoaded = false;

	public static boolean initialize() {
		try {
			registerConnHandler();
			
			addRemoteOverride();
			
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

	private static void addRemoteOverride() {
		WirelessRedstoneRemoteOverrideSMP override = new WirelessRedstoneRemoteOverrideSMP();
		WirelessRemoteDevice.addOverride(override);
	}

	private static void registerConnHandler() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}
}
