package net.minecraft.src.wirelessredstone.data;

public interface IWirelessDevice {
	
	public String getFreq();
	
	public WirelessCoordinates getCoords();

	void activate();

	void deactivate();
}
