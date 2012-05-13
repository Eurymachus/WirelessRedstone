package net.minecraft.src.wifi;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wifi.network.PacketHandlerRedstoneWireless;

public class BlockRedstoneWirelessTInjector {
	public static void onBlockRedstoneWirelessActivated(EntityPlayer entityplayer, TileEntityRedstoneWirelessT tileentity) {
		PacketHandlerRedstoneWireless.PacketHandlerOutput.sendGuiPacketTo(
				(EntityPlayerMP)entityplayer, 
				tileentity,
				0
		);
	}
}
