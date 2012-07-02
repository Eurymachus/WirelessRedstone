package net.minecraft.src.wirelessredstone.smp.overrides;

import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessOverride;

public class TileEntityRedstoneWirelessOverrideSMP implements
		TileEntityRedstoneWirelessOverride {
	@Override
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity) {
		return tileentity.worldObj.isRemote;
	}

	@Override
	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity) {
	}
}