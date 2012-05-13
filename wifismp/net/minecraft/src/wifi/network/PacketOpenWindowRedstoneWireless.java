package net.minecraft.src.wifi.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.wifi.TileEntityRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessR;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessT;

public class PacketOpenWindowRedstoneWireless extends PacketWifiSMP {
	public boolean firstTick = true;

	public PacketOpenWindowRedstoneWireless() {
		super(PacketIds.WIFI_GUI);
	}
	public PacketOpenWindowRedstoneWireless(TileEntityRedstoneWireless entity) {
		this();
		this.setPosition(entity.getBlockCoord(0), entity.getBlockCoord(1), entity.getBlockCoord(2));
		PacketPayload p = new PacketPayload(1,1,2,1);
		firstTick = entity.firstTick;
		p.setStringPayload(0, entity.currentFreq);
		p.setStringPayload(1, entity.getFreq().toString());
		if ( entity instanceof TileEntityRedstoneWirelessR) {
			p.setIntPayload(0, 0);
		} else if ( entity instanceof TileEntityRedstoneWirelessT ) {
			p.setIntPayload(0, 1);
		}
		this.payload = p;
	}

	public String toString() {
		return this.payload.getIntPayload(0)+":("+xPosition+","+yPosition+","+zPosition+")["+this.payload.getStringPayload(0)+"]";
	}
	
	public int getType()
	{
		return this.payload.getIntPayload(0);
	}
	
	public String getCurrentFreq()
	{
		return this.payload.getStringPayload(0);
	}
	
	public String getOldFreq()
	{
		return this.payload.getStringPayload(1);
	}

	@Override
	public void readData(DataInputStream datainputstream)
			throws IOException {
		super.readData(datainputstream);
		firstTick = datainputstream.readBoolean();
	}

	@Override
	public void writeData(DataOutputStream dataoutputstream)
			throws IOException {
		super.writeData(dataoutputstream);
		dataoutputstream.writeBoolean(firstTick);
	}
}
