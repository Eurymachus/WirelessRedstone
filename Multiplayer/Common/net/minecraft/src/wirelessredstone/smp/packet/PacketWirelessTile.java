package net.minecraft.src.wirelessredstone.smp.packet;

import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless.LogLevel;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;


public class PacketWirelessTile extends PacketWireless {
	public PacketWirelessTile() {
		super(PacketIds.WIFI_TILE);
	}
	
	public PacketWirelessTile(String command, TileEntityRedstoneWireless entity)
	{
		super(PacketIds.WIFI_TILE, new PacketPayload(0,0,2,13));
		this.setPosition(entity.getBlockCoord(0), entity.getBlockCoord(1), entity.getBlockCoord(2));
		LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).write("[fetchTile]" + this.xPosition + this.yPosition + this.zPosition, LogLevel.INFO);
		this.setCommand(command);
		this.setFreq(entity.getFreq());
		this.setState(RedstoneEther.getInstance().getFreqState(entity.worldObj, entity.getFreq()));
		this.setPowerDirections(entity.getPowerDirections());
		this.setInDirectlyPowering(entity.getInDirectlyPowering());
	}

	@Override
	public String toString() {
		return this.getCommand()+"("+xPosition+","+yPosition+","+zPosition+")["+this.getFreq()+"]";
	}
	
	public boolean[] getPowerDirections() {
		int j = this.payload.getBoolSize() - 12;
		boolean[] dir = new boolean[6];
		for (int i = 0; i < 6; i++)
		{
			dir[i] = this.payload.getBoolPayload(j);
			j++;
		}
		return dir;
	}

	public void setPowerDirections(boolean[] dir) {
		int j = this.payload.getBoolSize() - 12;
		for (int i = 0; i < 6; i++)
		{
			this.payload.setBoolPayload(j, dir[i]);
			j++;
		}
	}
	
	public boolean[] getInDirectlyPowering() {
		int j = this.payload.getBoolSize() - 6;
		boolean[] indir = new boolean[6];
		for (int i = 0; i < 6; i++)
		{
			indir[i] = this.payload.getBoolPayload(j);
			j++;
		}
		return indir;
	}	

	public void setInDirectlyPowering(boolean[] indir) {
		int j = this.payload.getBoolSize() - 6;
		for (int i = 0; i < 6; i++)
		{
			this.payload.setBoolPayload(j, indir[i]);
			j++;
		}
	}
}
