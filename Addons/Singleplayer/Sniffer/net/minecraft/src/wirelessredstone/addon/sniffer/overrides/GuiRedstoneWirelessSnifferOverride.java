package net.minecraft.src.wirelessredstone.addon.sniffer.overrides;

import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessDeviceOverride;

public abstract class GuiRedstoneWirelessSnifferOverride implements
		GuiRedstoneWirelessDeviceOverride {

	public abstract boolean beforeSetPage(WirelessDeviceData data,
			int pageNumber);

	public abstract boolean beforeGuiClosed(
			WirelessDeviceData wirelessDeviceData);
}
