package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.sniffer.smp.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.sniffer.smp.overrides.GuiRedstoneWirelessSnifferOverrideSMP;
import net.minecraft.src.wirelessredstone.addon.triangulator.WirelessTriangulatorSMP;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.overrides.BaseModOverrideSMP;

public class WirelessSnifferSMP {
	public static boolean isLoaded = false;

	public static boolean initialize() {
		try {
			registerConnHandler();

			addBaseOverride();
			addGuiOverride();
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

	private static void addGuiOverride() {
		GuiRedstoneWirelessSnifferOverrideSMP override = new GuiRedstoneWirelessSnifferOverrideSMP();
		WirelessSniffer.guiSniffer.addSnifferOverride(override);
	}

	private static void addBaseOverride() {
		BaseModOverrideSMP override = new BaseModOverrideSMP();
		WirelessSniffer.addOverride(override);
	}

	private static void registerConnHandler() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}
}
