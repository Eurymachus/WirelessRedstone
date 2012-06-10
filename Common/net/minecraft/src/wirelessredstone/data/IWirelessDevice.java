package net.minecraft.src.wirelessredstone.data;

public interface IWirelessDevice {
	
	void setFreq(String freq);
	
	String getFreq();
	
	WirelessCoordinates getCoords();

	void activate();

	void deactivate();
}
