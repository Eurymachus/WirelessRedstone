package net.minecraft.src.wirelessredstone.addon.clocker;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.overrides.GuiRedstoneWirelessClockerOverrideSMP;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;
import net.minecraft.src.wirelessredstone.smp.overrides.BaseModOverrideSMP;
import net.minecraft.src.wirelessredstone.smp.overrides.BlockRedstoneWirelessOverrideSMP;
import net.minecraft.src.wirelessredstone.smp.overrides.GuiRedstoneWirelessInventoryOverrideSMP;

public class WirelessClockerSMP {
	public static boolean isLoaded = false;

	public static boolean initialize() {
		try {
			registerConnHandler();
			addBlockOverride();
			addBaseOverride();
			addGuiOverride();

			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessClockerSMP.class
									.toString())).write(
					"Initialization failed.",
					LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}

	private static void addBaseOverride() {
		BaseModOverride override = new BaseModOverrideSMP();
		WirelessClocker.addOverride(override);
	}

	private static void registerConnHandler() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	private static void addBlockOverride() {
		BlockRedstoneWirelessOverride override = new BlockRedstoneWirelessOverrideSMP();
		((BlockRedstoneWireless) WirelessClocker.blockClock)
				.addOverride(override);
	}

	private static void addGuiOverride() {
		GuiRedstoneWirelessInventoryOverrideSMP override = new GuiRedstoneWirelessInventoryOverrideSMP();
		WirelessClocker.guiClock.addOverride(override);
		GuiRedstoneWirelessClockerOverrideSMP clockerOverride = new GuiRedstoneWirelessClockerOverrideSMP();
		WirelessClocker.guiClock.addClockerOverride(clockerOverride);
	}
}