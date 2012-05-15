package net.minecraft.server;

import forge.NetworkMod;
import wifi.WirelessRedstone;

public class mod_WirelessRedstone extends NetworkMod
{
    public static NetworkMod instance;

    public mod_WirelessRedstone()
    {
        instance = this;
        WirelessRedstone.load();
    }

    public void load()
    {
    }

    public String getVersion()
    {
        return "1.6";
    }

    public String toString()
    {
        return "WirelessRedstone " + this.getVersion();
    }

	@Override
	public boolean clientSideRequired()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean serverSideRequired()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
