package net.minecraft.src;

public class BlockItemRedstoneWirelessT extends BlockItemRedstoneWireless {

	public BlockItemRedstoneWirelessT(int par1) {
		super(par1);
	}

	@Override
	protected int getRedstoneWirelessItemIndex() {
		return mod_WirelessRedstone.spriteTItem;
	}

}
