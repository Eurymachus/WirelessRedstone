package net.minecraft.src.wirelessredstone.injectors;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.wirelessredstone.smp.network.PacketHandlerRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class BlockRedstoneWirelessTInjector {
	public static void onBlockRedstoneWirelessActivated(EntityPlayer entityplayer, TileEntityRedstoneWirelessT tileentity) {
		PacketHandlerRedstoneWireless.PacketHandlerOutput.sendGuiPacketTo(
				(EntityPlayerMP)entityplayer, 
				tileentity,
				0
		);
	}
}
