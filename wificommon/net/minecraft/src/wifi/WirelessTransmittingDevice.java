package net.minecraft.src.wifi;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.wifi.core.Vector3;

public interface WirelessTransmittingDevice
{
	public Vector3 getPosition();
	public int getDimension();
	public String getFreq();
	EntityLiving getAttachedEntity();
}