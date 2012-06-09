package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.World;

public abstract class WirelessDevice implements IWirelessDevice {
	protected World world;
	protected String freq;
	protected WirelessCoordinates coords;

	@Override
	public String getFreq() {
		return this.freq;
	}

	@Override
	public WirelessCoordinates getCoords() {
		return this.coords;
	}
}
