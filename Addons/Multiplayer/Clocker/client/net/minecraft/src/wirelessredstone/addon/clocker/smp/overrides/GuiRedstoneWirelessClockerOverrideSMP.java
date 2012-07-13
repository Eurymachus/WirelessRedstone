package net.minecraft.src.wirelessredstone.addon.clocker.smp.overrides;

import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.network.PacketHandlerWirelessClocker;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessInventoryOverride;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class GuiRedstoneWirelessClockerOverrideSMP implements
		GuiRedstoneWirelessInventoryOverride {

	@Override
	public boolean beforeFrequencyChange(TileEntityRedstoneWireless entity,
			Object oldFreq, Object newFreq) {
		if (WirelessRedstone.getWorld().isRemote) {
			int OLD = Integer.parseInt(oldFreq.toString());
			int NEW = Integer.parseInt(newFreq.toString());
			PacketHandlerWirelessClocker.PacketHandlerOutput
					.sendWirelessClockerPacket(entity.getBlockCoord(0),
							entity.getBlockCoord(1), entity.getBlockCoord(2),
							(NEW - OLD));
			return true;
		}
		return false;
	}
}
