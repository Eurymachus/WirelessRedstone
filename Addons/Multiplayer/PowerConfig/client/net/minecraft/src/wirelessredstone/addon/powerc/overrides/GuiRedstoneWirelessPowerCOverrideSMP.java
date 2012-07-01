package net.minecraft.src.wirelessredstone.addon.powerc.overrides;

import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.addon.powerc.network.PacketHandlerPowerConfig;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessInventoryOverride;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class GuiRedstoneWirelessPowerCOverrideSMP implements
		GuiRedstoneWirelessInventoryOverride {

	@Override
	public boolean beforeFrequencyChange(TileEntityRedstoneWireless entity,
			Object command, Object newDirection) {
		if (ModLoader.getMinecraftInstance().theWorld.isRemote) {
			PacketHandlerPowerConfig.PacketHandlerOutput.sendPowerConfigPacket(
					command.toString(), entity.xCoord, entity.yCoord,
					entity.zCoord, Integer.parseInt(newDirection.toString()));
		}
		return false;
	}
}
