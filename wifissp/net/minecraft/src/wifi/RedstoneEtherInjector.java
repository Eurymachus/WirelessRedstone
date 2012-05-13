package net.minecraft.src.wifi;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class RedstoneEtherInjector {
	/**
	 * Checks if a block is loaded on the world.
	 * 
	 * @param world the world object
	 * @param i world X coordinate
	 * @param j world Y coordinate
	 * @param k world Z coordinate
	 * @return false if the block is not loaded, true if it is.
	 */
	public static boolean isBlockLoaded(World world, int i, int j, int k) {
		LoggerRedstoneWireless.getInstance("RedstoneEther").write("isBlockLoaded(world, "+i+", "+j+", "+k+")", LoggerRedstoneWireless.LogLevel.DEBUG);

		if ( world != null && world.getBlockId(i, j, k) != 0 && world.getBlockTileEntity(i, j, k) != null )  // Is loaded
			return true;


		if ( ModLoader.getMinecraftInstance() == null || ModLoader.getMinecraftInstance().thePlayer == null )
			return false;

		int[] a = {i,j,k};
		int[] b = {
				(int) ModLoader.getMinecraftInstance().thePlayer.posX,
				(int) ModLoader.getMinecraftInstance().thePlayer.posY,
				(int) ModLoader.getMinecraftInstance().thePlayer.posZ
		};
		if ( RedstoneEther.pythagoras(a, b) < 1 ) // Is player
			return true;

		return false;
	}
}