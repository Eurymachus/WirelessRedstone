package net.minecraft.src.wifi;

import net.minecraft.src.World;

public class RedstoneEtherUncommon {
	public static boolean isBlockLoaded(World world, int i, int j, int k) {
		LoggerRedstoneWireless.getInstance("RedstoneEther").write(
				"isBlockLoaded(world, "+i+", "+j+", "+k+
				"):["+(world.getBlockId(i, j, k) != 0)+"&"+(world.getBlockTileEntity(i, j, k) != null)+"]",
		LoggerRedstoneWireless.LogLevel.DEBUG);
		return ( 
				world.getBlockId(i, j, k) != 0 &&
				world.getBlockTileEntity(i, j, k) != null
		);
	}
}
