package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.SwingUtilities;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.wifi.WirelessRedstone;
import net.minecraft.src.wifi.network.NetworkConnection;

public class mod_WirelessRedstone extends NetworkMod
{
	public static NetworkMod instance;
	
	public mod_WirelessRedstone() {
		instance = this;
    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
		WirelessRedstone.load();
	}
	
	@Override
	public void load()
	{
	}
	
	@Override
	public String getVersion() {
		return "1.6";
	}

	@Override
	public String toString() {
		return "WirelessRedstone "+getVersion();
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
		return true;
	}
}
