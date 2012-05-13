package net.minecraft.src.wifi;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class BlockRedstoneWirelessTInjector {
	public static void onBlockRedstoneWirelessActivated(EntityPlayer entityplayer, TileEntityRedstoneWirelessT tileentity) {
		WirelessRedstone.guiWirelessT.assTileEntity(tileentity);
		ModLoader.openGUI(entityplayer, WirelessRedstone.guiWirelessT);
	}
}
