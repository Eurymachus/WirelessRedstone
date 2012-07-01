package net.minecraft.src.wirelessredstone.addon.clocker.overrides;

import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.addon.clocker.network.PacketHandlerWirelessClocker;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessInventoryOverride;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class GuiRedstoneWirelessClockerOverrideSMP implements
		GuiRedstoneWirelessInventoryOverride {

	@Override
	public boolean beforeFrequencyChange(TileEntityRedstoneWireless entity,
			Object oldFreq, Object newFreq) {
		if (ModLoader.getMinecraftInstance().theWorld.isRemote) {
			int OLD = Integer.parseInt(oldFreq.toString());
			int NEW = Integer.parseInt(newFreq.toString());
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage("OLD: "
					+ OLD + " NEW: " + NEW);
			PacketHandlerWirelessClocker.PacketHandlerOutput
					.sendWirelessClockerPacket(entity.getBlockCoord(0),
							entity.getBlockCoord(1), entity.getBlockCoord(2),
							(NEW - OLD));
			return true;
		}
		return false;
	}
}
