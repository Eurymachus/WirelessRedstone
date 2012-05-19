package net.minecraft.src.wirelessredstone.smp.packet;

import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

/**
 * Used to send Wireless Gui packet information
 * 
 * @author Eurymachus
 *
 */
public class PacketOpenWindowRedstoneWireless extends PacketWireless {
	public PacketOpenWindowRedstoneWireless() {
		super(PacketIds.WIFI_GUI);
	}
	public PacketOpenWindowRedstoneWireless(TileEntityRedstoneWireless entity) {
		super(PacketIds.WIFI_GUI);
		this.setPosition(entity.getBlockCoord(0), entity.getBlockCoord(1), entity.getBlockCoord(2));
		this.payload = new PacketPayload(1,0,2,1);
		if ( entity instanceof TileEntityRedstoneWirelessR) {
			this.setType(0);
		} else if ( entity instanceof TileEntityRedstoneWirelessT ) {
			this.setType(1);
		}
		this.setFreq(entity.currentFreq);
		this.setFirstTick(entity.firstTick);
	}

	@Override
	public String toString() {
		return this.payload.getIntPayload(0)+":("+xPosition+","+yPosition+","+zPosition+")["+this.payload.getStringPayload(0)+"]";
	}
	
	public int getType()
	{
		return this.payload.getIntPayload(0);
	}
	
	public void setType(int type)
	{
		this.payload.setIntPayload(0, type);
	}
	
	@Override
	public String getFreq()
	{
		return this.payload.getStringPayload(0);
	}
	
	@Override
	public void setFreq(Object freq)
	{
		this.payload.setStringPayload(0, freq.toString());
	}
	
	public boolean getFirstTick()
	{
		return this.payload.getBoolPayload(0);
	}
	
	public void setFirstTick(boolean firstTick)
	{
		this.payload.setBoolPayload(0, firstTick);
	}
}
