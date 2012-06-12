package net.minecraft.src.wirelessredstone.overrides;

import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public abstract interface GuiRedstoneWirelessOverride {

	boolean beforeFrequencyChange(TileEntityRedstoneWireless entity,
			Object oldFreq, Object newFreq);

	boolean beforeFrequencyChange(WirelessDeviceData data, Object oldFreq,
			Object newFreq);

}
