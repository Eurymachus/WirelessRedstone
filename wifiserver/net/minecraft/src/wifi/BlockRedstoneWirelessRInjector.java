package net.minecraft.src.wifi;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wifi.network.PacketHandlerRedstoneWireless;

public class BlockRedstoneWirelessRInjector {
	public static void onBlockRedstoneWirelessActivated(EntityPlayer entityplayer, TileEntityRedstoneWirelessR tileentity) {
		PacketHandlerRedstoneWireless.PacketHandlerOutput.sendGuiPacketTo(
				(EntityPlayerMP)entityplayer, 
				tileentity,
				0
		);
	}
	
	public static void updateRedstoneWirelessTick(World world, int i, int j, int k) {
		TileEntity entity = world.getBlockTileEntity(i, j, k);
		if ( entity instanceof TileEntityRedstoneWireless )
			PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTileToAll(
					(TileEntityRedstoneWireless)entity,
					world,
					0
			);
	}
}
