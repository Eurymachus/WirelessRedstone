package net.minecraft.src.wifi;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class RedstoneEtherInjector {
	public static boolean isOtherLoaded(int i, int j, int k) {
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