package net.minecraft.src.wirelessredstone.injectors;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class BlockRedstoneWirelessRInjector {
	public static void onBlockRedstoneWirelessActivated(EntityPlayer entityplayer, TileEntityRedstoneWirelessR tileentity) {
		WirelessRedstone.guiWirelessR.assTileEntity(tileentity);
		ModLoader.openGUI(entityplayer,WirelessRedstone.guiWirelessR);
	}
	
	public static void updateRedstoneWirelessTick(World world, int i, int j, int k) {
		
	}
}
