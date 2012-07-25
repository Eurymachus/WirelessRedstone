package net.minecraft.src.wirelessredstone.tileentity;

import net.minecraft.src.wirelessredstone.data.IRedstoneWirelessData;

public interface TileEntityRedstoneWirelessOverride {
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity);

	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity);

	public boolean beforeHandleData(
			TileEntityRedstoneWireless tileEntityRedstoneWireless,
			IRedstoneWirelessData data);
}
