 package net.minecraft.src.wirelessredstone.smp.packet;

import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

/**
 * Used to send Redstone Ether packet data
 * 
 * @author Eurymachus
 *
 */
public class PacketRedstoneEther extends PacketWireless {
	public PacketRedstoneEther() {
		super(PacketIds.WIFI_ETHER);
	}
	
	public PacketRedstoneEther(String command) {
		super(PacketIds.WIFI_ETHER, new PacketPayload(0,0,2,1));
		setCommand(command);
	}
	
	public PacketRedstoneEther(TileEntityRedstoneWireless entity, World world)
	{
		super(PacketIds.WIFI_ETHER, new PacketPayload(0,0,2,1));
		this.setPosition(entity.getBlockCoord(0), entity.getBlockCoord(1), entity.getBlockCoord(2));
		if ( entity instanceof TileEntityRedstoneWirelessR) {
			setState(((BlockRedstoneWireless)WirelessRedstone.blockWirelessR).getState(world, this.xPosition, this.yPosition, this.zPosition));
			setCommand("addReceiver");
		} else if ( entity instanceof TileEntityRedstoneWirelessT) {
			setState(((BlockRedstoneWireless)WirelessRedstone.blockWirelessT).getState(world, this.xPosition, this.yPosition, this.zPosition));
			setCommand("addTransmitter");
		}
		setFreq(entity.getFreq());
	}

	@Override
	public String toString() {
		return this.getCommand()+"("+xPosition+","+yPosition+","+zPosition+")["+this.getFreq()+"]:"+this.getState();
	}
	
	public String getCommand()
	{
		return this.payload.getStringPayload(0);
	}
	
	public void setCommand(String command)
	{
		this.payload.setStringPayload(0, command);
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write("setCommand("+command+")", LoggerRedstoneWireless.LogLevel.DEBUG);
	}
	
	public String getFreq()
	{
		return this.payload.getStringPayload(1);
	}

	public void setFreq(Object freq) {
		this.payload.setStringPayload(1, freq.toString());
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write("setFreq("+freq.toString()+")", LoggerRedstoneWireless.LogLevel.DEBUG);
	}

	public void setState(boolean state) {
		this.payload.setBoolPayload(0, state);
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write("setState("+state+")", LoggerRedstoneWireless.LogLevel.DEBUG);
	}

	public boolean getState() {
		return this.payload.getBoolPayload(0);
	}
}
