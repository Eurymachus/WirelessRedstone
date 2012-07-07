package net.minecraft.src.wirelessredstone.addon.sniffer.overrides;

import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.PacketHandlerWirelessSniffer;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;

public class GuiRedstoneWirelessSnifferOverrideSMP extends
		GuiRedstoneWirelessSnifferOverride {

	@Override
	public boolean beforeFrequencyChange(WirelessDeviceData data,
			Object oldFreq, Object newFreq) {
		return false;
	}

	@Override
	public boolean beforeSetPage(WirelessDeviceData data, int pageNumber) {
		if (ModLoader.getMinecraftInstance().theWorld.isRemote) {
			PacketHandlerWirelessSniffer.PacketHandlerOutput
					.sendWirelessSnifferPacket("changePage", pageNumber);
			return true;
		}
		return false;
	}

	@Override
	public boolean beforeGuiClosed(WirelessDeviceData data) {
		if (ModLoader.getMinecraftInstance().theWorld.isRemote) {
			PacketHandlerWirelessSniffer.PacketHandlerOutput
					.sendWirelessSnifferGuiPacket(data.getID());
			return true;
		}
		return false;
	}

}
