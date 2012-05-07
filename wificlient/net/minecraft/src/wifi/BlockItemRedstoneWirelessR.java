package net.minecraft.src.wifi;

import net.minecraft.src.mod_WirelessRedstone;

public class BlockItemRedstoneWirelessR extends BlockItemRedstoneWireless {

	public BlockItemRedstoneWirelessR(int par1) {
		super(par1);
	}

	@Override
	protected int getRedstoneWirelessItemIndex() {
		return WirelessRedstone.spriteRItem;
	}

}
