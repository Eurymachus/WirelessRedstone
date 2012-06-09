package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;

public interface WirelessRedstoneRemoteOverride {
	public boolean beforeTransmitRemote(String command, World world,
			WirelessRemoteDevice remote);
}
