package net.minecraft.src.wifi;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class BlockRedstoneWirelessRInjector {
	public static void onBlockRedstoneWirelessActivated(EntityPlayer entityplayer, TileEntityRedstoneWirelessR tileentity) {
		WirelessRedstone.guiWirelessR.assTileEntity(tileentity);
		ModLoader.openGUI(entityplayer,WirelessRedstone.guiWirelessR);
	}
	
	public static void updateRedstoneWirelessTick(World world, int i, int j, int k) {
		
	}
}
