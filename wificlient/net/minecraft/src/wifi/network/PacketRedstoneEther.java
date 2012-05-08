package net.minecraft.src.wifi.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.BlockRedstoneWireless;
import net.minecraft.src.TileEntityRedstoneWireless;
import net.minecraft.src.TileEntityRedstoneWirelessR;
import net.minecraft.src.TileEntityRedstoneWirelessT;


public class PacketRedstoneEther extends PacketUpdate {
	public boolean state = false;
	
	public PacketRedstoneEther() {
		super(PacketIds.WIFI_ETHER);
	}
	
	public PacketRedstoneEther(String command) {
		this();
		PacketPayload p = new PacketPayload(1,1,2);
		p.stringPayload[0] = "";
		p.stringPayload[1] = command;
		p.intPayload[0] = 0;
		p.floatPayload[0] = 0;
		this.payload = p;
	}
	
	public PacketRedstoneEther(TileEntityRedstoneWireless entity, World world)
	{
		this();
		this.xPosition = entity.getBlockCoord(0);
		this.yPosition = entity.getBlockCoord(1);
		this.zPosition = entity.getBlockCoord(2);
		PacketPayload p = new PacketPayload(1,1,2);
		p.stringPayload[0] = entity.getFreq();
		
		if ( entity instanceof TileEntityRedstoneWirelessR) {
			this.state = ((BlockRedstoneWireless)mod_WirelessRedstone.blockWirelessR).getState(world, this.xPosition, this.yPosition, this.zPosition);
			p.stringPayload[1] = "addReceiver";
		} else if ( entity instanceof TileEntityRedstoneWirelessT) {
			this.state = ((BlockRedstoneWireless)mod_WirelessRedstone.blockWirelessT).getState(world, this.xPosition, this.yPosition, this.zPosition);
			p.stringPayload[1] = "addTransmitter";
		}
		p.intPayload[0] = 0;
		p.floatPayload[0] = 0;
		this.payload = p;
	}

	public String toString() {
		return this.payload.stringPayload[1]+"("+xPosition+","+yPosition+","+zPosition+")["+this.payload.stringPayload[0]+"]:"+state;
	}
	
	public String getFreq()
	{
		return this.payload.stringPayload[0];
	}
	
	public String getCommand()
	{
		return this.payload.stringPayload[1];
	}
	
	public void setPosition(int i, int j, int k) {
		this.xPosition = i;
		this.yPosition = j;
		this.zPosition = k;
	}

	public void setFreq(Object freq) {
		this.payload.stringPayload[0] = freq.toString();
	}

	public void setState(boolean state) {
		this.state = state;
	}

	@Override
	public void readData(DataInputStream datainputstream)
			throws IOException {
		super.readData(datainputstream);
		//xPosition = datainputstream.readInt();
		//yPosition = datainputstream.readInt();
		//zPosition = datainputstream.readInt();
		//freq = this.readString(datainputstream, 15000);
		state = datainputstream.readBoolean();
		//command = this.readString(datainputstream, 20);
	}

	@Override
	public void writeData(DataOutputStream dataoutputstream)
			throws IOException {
		super.writeData(dataoutputstream);
		//dataoutputstream.writeInt(xPosition);
		//dataoutputstream.writeInt(yPosition);
		//dataoutputstream.writeInt(zPosition);
		//this.writeString(freq.toString(), dataoutputstream);
		dataoutputstream.writeBoolean(state);
		//this.writeString(command, dataoutputstream);
	}
	
}
