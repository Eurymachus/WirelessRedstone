package net.minecraft.src.wifi;

import net.minecraft.src.mod_WirelessRedstone;

public class BlockItemRedstoneWirelessT extends BlockItemRedstoneWireless {

	public BlockItemRedstoneWirelessT(int par1) {
		super(par1);
	}

	@Override
	protected int getRedstoneWirelessItemIndex() {
		return WirelessRedstone.spriteTItem;
	}

}