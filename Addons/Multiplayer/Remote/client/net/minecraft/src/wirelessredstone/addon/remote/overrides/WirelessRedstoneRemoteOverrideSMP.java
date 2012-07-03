package net.minecraft.src.wirelessredstone.addon.remote.overrides;

import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;

public class WirelessRedstoneRemoteOverrideSMP implements WirelessRedstoneRemoteOverride {

	@Override
	public boolean beforeTransmitRemote(String command, World world,
			WirelessRemoteDevice remote) {
		
		return true;
	}
}
