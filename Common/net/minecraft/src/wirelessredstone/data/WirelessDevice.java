package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class WirelessDevice implements IWirelessDevice {
	
	protected World world;
	protected EntityPlayer owner;
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

	@Override
	public void setFreq(String freq) {
		this.freq = freq;
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}
}
