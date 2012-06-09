package net.minecraft.src.wirelessredstone.block;

import net.minecraft.src.wirelessredstone.WirelessRedstone;

public class BlockItemRedstoneWirelessR extends BlockItemRedstoneWireless {

	public BlockItemRedstoneWirelessR(int par1) {
		super(par1);
	}

	@Override
	protected int getRedstoneWirelessItemIndex() {
		return WirelessRedstone.spriteRItem;
	}

}
