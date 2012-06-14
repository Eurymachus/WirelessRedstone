package net.minecraft.src.wirelessredstone.addon.clocker.overrides;

import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessInventoryOverride;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class GuiRedstoneWirelessClockerOverride implements GuiRedstoneWirelessInventoryOverride {
	@Override
	public boolean beforeFrequencyChange(TileEntityRedstoneWireless entity,
			Object oldFreq, Object newFreq) {
		return false;
	}
}
