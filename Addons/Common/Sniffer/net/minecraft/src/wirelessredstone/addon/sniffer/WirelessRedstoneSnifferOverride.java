package net.minecraft.src.wirelessredstone.addon.sniffer;

public interface WirelessRedstoneSnifferOverride
{
	public boolean beforeGetFreqState(int frequency);

	public boolean afterGetFreqState(int c, boolean returnState);
}
