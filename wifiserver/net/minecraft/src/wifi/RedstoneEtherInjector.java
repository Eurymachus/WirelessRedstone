package net.minecraft.src.wifi;

import java.util.Iterator;
import java.util.List;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wifiremote.WirelessRemote;

public class RedstoneEtherInjector {
	public static boolean isBlockLoaded(World world, int i, int j, int k) {
		LoggerRedstoneWireless.getInstance("RedstoneEther").write(
				"isBlockLoaded(world, "+i+", "+j+", "+k+
				"):["+(world.getBlockId(i, j, k) != 0)+"&"+(world.getBlockTileEntity(i, j, k) != null)+"]",
		LoggerRedstoneWireless.LogLevel.DEBUG);
		if (world.getBlockId(i, j, k) != 0 ||
				world.getBlockTileEntity(i, j, k) != null) return true;

		for (int p = 0; p < world.playerEntities
				.size(); j++)
		{
			EntityPlayerMP player = (EntityPlayerMP)world.playerEntities.get(j);

			if ((int)player.posX == i && (int)player.posY == j && (int)player.posZ == k) return true;
		}
		return false;
	}
}