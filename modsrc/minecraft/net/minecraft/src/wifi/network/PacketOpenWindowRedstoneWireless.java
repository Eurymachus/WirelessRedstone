package net.minecraft.src.wifi.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.TileEntityRedstoneWireless;
import net.minecraft.src.TileEntityRedstoneWirelessR;
import net.minecraft.src.TileEntityRedstoneWirelessT;

public class PacketOpenWindowRedstoneWireless extends PacketUpdate {
	public boolean firstTick = true;

	public PacketOpenWindowRedstoneWireless() {
		super(PacketIds.WIFI_GUI);
	}
	public PacketOpenWindowRedstoneWireless(TileEntityRedstoneWireless entity) {
		this();
		xPosition = entity.getBlockCoord(0);
		yPosition = entity.getBlockCoord(1);
		zPosition = entity.getBlockCoord(2);
		PacketPayload p = new PacketPayload(1,1,2);
		firstTick = entity.firstTick;
		p.stringPayload[0] = entity.currentFreq;
		p.stringPayload[1] = entity.getFreq();
		if ( entity instanceof TileEntityRedstoneWirelessR) {
			p.intPayload[0] = 0;
		} else if ( entity instanceof TileEntityRedstoneWirelessT ) {
			p.intPayload[0] = 1;
		}
		p.floatPayload[0] = 0;
		this.payload = p;
	}

	public String toString() {
		return this.payload.intPayload[0]+":("+xPosition+","+yPosition+","+zPosition+")["+this.payload.stringPayload[0]+"]";
	}
	
	public int getType()
	{
		return this.payload.intPayload[0];
	}
	
	public String getCurrentFreq()
	{
		return this.payload.stringPayload[0];
	}
	
	public String getOldFreq()
	{
		return this.payload.stringPayload[1];
	}

	@Override
	public void readData(DataInputStream datainputstream)
			throws IOException {
		super.readData(datainputstream);
		//xPosition = datainputstream.readInt();
		//yPosition = datainputstream.readInt();
		//zPosition = datainputstream.readInt();
		firstTick = datainputstream.readBoolean();
		//currentFreq = this.readString(datainputstream, 15000);
		//oldFreq = this.readString(datainputstream, 15000);
		//type = datainputstream.readInt();
	}

	@Override
	public void writeData(DataOutputStream dataoutputstream)
			throws IOException {
		super.writeData(dataoutputstream);
		//dataoutputstream.writeInt(xPosition);
		//dataoutputstream.writeInt(yPosition);
		//dataoutputstream.writeInt(zPosition);
		dataoutputstream.writeBoolean(firstTick);
		//this.writeString(currentFreq.toString(), dataoutputstream);
		//this.writeString(oldFreq.toString(), dataoutputstream);
		//dataoutputstream.writeInt(type);
	}
}
