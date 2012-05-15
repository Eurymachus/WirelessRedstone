package net.minecraft.src.wirelessredstone.addon.clocker;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.clocker.network.PacketHandlerWirelessClocker;

public class BlockRedstoneWirelessClockerInjector {
	public static boolean onBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if (entityplayer.canPlayerEdit(i,  j, k)) {
			TileEntityRedstoneWirelessClocker twc = (TileEntityRedstoneWirelessClocker)world.getBlockTileEntity(i, j, k);
			PacketHandlerWirelessClocker.PacketHandlerOutput.sendWirelessClockerGuiPacket(entityplayer, twc.getClockFreq(), i, j, k);
			return true;
		}
		return false;
	}
}