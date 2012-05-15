package net.minecraft.src.clocker;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.clocker.network.PacketHandlerWirelessClocker;

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