package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.mod_WirelessSnifferSMP;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.smp.network.SnifferConnection;
import net.minecraft.src.wirelessredstone.addon.sniffer.smp.overrides.GuiRedstoneWirelessSnifferOverrideSMP;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.overrides.BaseModOverrideSMP;

public class WirelessSnifferSMP {
	public static boolean isLoaded = false;
	public static SnifferConnection snifferConnection;
	public static String channel = "WR-SNIFFER";

	public static boolean initialize() {
		try {
			registerConnHandler();

			addBaseOverride();
			addGuiOverride();
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessSnifferSMP.class
									.toString())).write(
					"Initialization failed.",
					LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}

	private static void addGuiOverride() {
		GuiRedstoneWirelessSnifferOverrideSMP override = new GuiRedstoneWirelessSnifferOverrideSMP();
		WirelessSniffer.guiSniffer.addSnifferOverride(override);
	}

	private static void addBaseOverride() {
		BaseModOverrideSMP override = new BaseModOverrideSMP();
		WirelessSniffer.addOverride(override);
	}

	private static void registerConnHandler() {
		snifferConnection = new SnifferConnection(WirelessRedstone.getPlayer(),
				"SNIFFER");
		snifferConnection.onLogin(null, null, mod_WirelessSnifferSMP.instance);
	}
}
