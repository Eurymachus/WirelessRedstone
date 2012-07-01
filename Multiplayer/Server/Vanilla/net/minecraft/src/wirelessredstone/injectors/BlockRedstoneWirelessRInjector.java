package net.minecraft.src.wirelessredstone.injectors;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.smp.network.PacketHandlerRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class BlockRedstoneWirelessRInjector {	
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
