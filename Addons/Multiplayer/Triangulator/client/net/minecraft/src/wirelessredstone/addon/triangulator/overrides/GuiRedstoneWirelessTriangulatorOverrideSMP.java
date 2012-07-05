package net.minecraft.src.wirelessredstone.addon.triangulator.overrides;

import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.PacketHandlerWirelessTriangulator.PacketHandlerOutput;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessDeviceOverride;

public class GuiRedstoneWirelessTriangulatorOverrideSMP implements
		GuiRedstoneWirelessDeviceOverride {

	@Override
	public boolean beforeFrequencyChange(WirelessDeviceData data,
			Object oldFreq, Object newFreq) {
		if (WirelessRedstone.getWorld().isRemote) {
			int OLD = Integer.parseInt(oldFreq.toString());
			int NEW = Integer.parseInt(newFreq.toString());
			if (OLD != NEW)
				PacketHandlerOutput.sendWirelessTriangulatorPacket(
						"changeFreq", data.getID(), (NEW - OLD));
		}
		return false;
	}
}
