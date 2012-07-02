package net.minecraft.src.wirelessredstone.tileentity;

public interface TileEntityRedstoneWirelessOverride {
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity);

	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity);
}
