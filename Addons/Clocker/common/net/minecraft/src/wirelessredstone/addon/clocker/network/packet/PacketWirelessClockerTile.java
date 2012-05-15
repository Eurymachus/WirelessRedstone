package net.minecraft.src.wirelessredstone.addon.clocker.network.packet;

import net.minecraft.src.wirelessredstone.addon.clocker.TileEntityRedstoneWirelessClocker;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless.LogLevel;
import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketWirelessClockerTile extends PacketWirelessClocker {
	public PacketWirelessClockerTile() {
		super(PacketIds.WIFI_CLOCKERTILE);
	}
	
	public PacketWirelessClockerTile(TileEntityRedstoneWirelessClocker entity)
	{
		this();
		this.setPosition(entity.getBlockCoord(0), entity.getBlockCoord(1), entity.getBlockCoord(2));
		LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).write("[fetchClockerTile]" + this.xPosition + this.yPosition + this.zPosition, LogLevel.INFO);
		this.payload = new PacketPayload(0, 0, 2, 0);
		this.setClockFreq(String.valueOf(entity.getClockFreq()));
		this.setFreq(entity.getFreq());
	}

	@Override
	public String toString() {
		return this.getClockFreq()+"("+xPosition+","+yPosition+","+zPosition+")["+this.getFreq()+"]";
	}

	public String getFreq()
	{
		return this.payload.getStringPayload(0);
	}

	public void setFreq(Object freq) {
		this.payload.setStringPayload(0, freq.toString());
		LoggerRedstoneWireless.getInstance("PacketWirelessClockerTile").write("setFreq("+freq.toString()+")", LoggerRedstoneWireless.LogLevel.DEBUG);
	}
	
	public String getClockFreq() {
		return this.payload.getStringPayload(1);
	}

	public void setClockFreq(String clockFreq) {
		this.payload.setStringPayload(1, clockFreq);
	}
}
