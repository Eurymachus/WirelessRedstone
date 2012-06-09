package net.minecraft.src.wirelessredstone.injectors;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class BlockRedstoneWirelessTInjector {
	public static void onBlockRedstoneWirelessActivated(
			EntityPlayer entityplayer, TileEntityRedstoneWirelessT tileentity) {
		WirelessRedstone.guiWirelessT.assTileEntity(tileentity);
		ModLoader.openGUI(entityplayer, WirelessRedstone.guiWirelessT);
	}
}
