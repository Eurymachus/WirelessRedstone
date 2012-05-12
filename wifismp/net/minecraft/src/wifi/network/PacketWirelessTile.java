package net.minecraft.src.wifi.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.wifi.BlockRedstoneWireless;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.LoggerRedstoneWireless.LogLevel;
import net.minecraft.src.wifi.TileEntityRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessR;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessT;
import net.minecraft.src.wifi.WirelessRedstone;


public class PacketWirelessTile extends PacketWifiSMP {
	public PacketWirelessTile() {
		super(PacketIds.WIFI_TILE);
	}
	
	public PacketWirelessTile(String command, TileEntityRedstoneWireless entity)
	{
		this();
		this.xPosition = entity.getBlockCoord(0);
		this.yPosition = entity.getBlockCoord(1);
		this.zPosition = entity.getBlockCoord(2);
		LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).write("[fetchTile]" + this.xPosition + this.yPosition + this.zPosition, LogLevel.DEBUG);
		this.payload = new PacketPayload(0, 0, 2, 12);
		setCommand(command);
		setFreq(entity.getFreq());
		setPowerDirections(entity.getPowerDirections());
		setInDirectlyPowering(entity.getInDirectlyPowering());
		ModLoader.getLogger().fine("Freq: " + entity.getFreq());
	}
	
	private void setCommand(String command)
	{
		this.payload.setStringPayload(0, command);
	}

	public String toString() {
		return this.getCommand()+"("+xPosition+","+yPosition+","+zPosition+")["+this.getFreq()+"]";
	}
	
	public String getCommand()
	{
		return this.payload.getStringPayload(0);
	}
	
	public String getFreq()
	{
		return this.payload.getStringPayload(1);
	}
	
	public void setPosition(int i, int j, int k) {
		this.xPosition = i;
		this.yPosition = j;
		this.zPosition = k;
	}

	public void setFreq(Object freq) {
		this.payload.setStringPayload(1, freq.toString());
	}

	public void setPowerDirections(boolean[] dir) {
		for (int i = 0; i < 6; i++)
		{
			this.payload.setBoolPayload(i, dir[i]);
		}
	}

	public void setInDirectlyPowering(boolean[] indir) {
		int j = 6;
		for (int i = 0; i < 6; i++)
		{
			this.payload.setBoolPayload(j, indir[i]);
			j++;
		}
	}
	
	public boolean[] getPowerDirections()
	{
		boolean[] dir = new boolean[6];
		for (int i = 0; i < 6; i++)
		{
			dir[i] = this.payload.getBoolPayload(i);
		}
		return dir;
	}
	
	public boolean[] getInDirectlyPowering()
	{
		boolean[] indir = new boolean[6];
		int j = 6;
		for (int i = 0; i < 6; i++)
		{
			indir[i] = this.payload.getBoolPayload(j);
			j++;
		}
		return indir;
	}	
}
