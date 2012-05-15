package net.minecraft.src.clocker;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockRedstoneWirelessClockerInjector {
	public static boolean onBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
	
			if ( tileentity instanceof TileEntityRedstoneWirelessClocker ) {
				WirelessClocker.guiClock.assTileEntity((TileEntityRedstoneWirelessClocker)tileentity);
				ModLoader.openGUI(entityplayer, WirelessClocker.guiClock);
			}
			return true;
		}
		return false;
	}
}
