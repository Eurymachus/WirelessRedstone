package net.minecraft.src.wirelessredstone.block;

import net.minecraft.src.wirelessredstone.WirelessRedstone;

public class BlockItemRedstoneWirelessT extends BlockItemRedstoneWireless {

	public BlockItemRedstoneWirelessT(int par1) {
		super(par1);
	}

	@Override
	protected int getRedstoneWirelessItemIndex() {
		return WirelessRedstone.spriteTItem;
	}

}
