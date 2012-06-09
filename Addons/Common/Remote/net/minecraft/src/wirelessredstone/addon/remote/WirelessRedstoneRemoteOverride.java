package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.World;

public interface WirelessRedstoneRemoteOverride {
	public boolean beforeTransmitRemote(String command, World world,
			Remote remote);
}
