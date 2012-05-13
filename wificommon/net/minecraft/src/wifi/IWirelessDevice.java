package net.minecraft.src.wifi;

public interface IWirelessDevice
{
	void setFreq(Object freq);
	
	Object getFreq();
	
	void setState(boolean state);
	
	boolean getState();
	
	void setPosition(int x, int y, int z);
	
	WirelessCoords getPosition();
}