package net.minecraft.src.wirelessredstone.overrides;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.ether.*;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.network.PacketHandlerRedstoneWireless;

public class RedstoneEtherOverrideServer implements RedstoneEtherOverride {

	@Override
	public boolean beforeAddTransmitter(World world, int i, int j, int k, String freq) {
		return false;
	}

	@Override
	public void afterAddTransmitter(World world, int i, int j, int k, String freq) {
		RedstoneEtherNode node = new RedstoneEtherNode(i,j,k);
		node.freq = freq;
		PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherNodeTileToAll(node,0);
	}

	@Override
	public boolean beforeRemTransmitter(World world, int i, int j, int k, String freq) {
		return false;
	}

	@Override
	public void afterRemTransmitter(World world, int i, int j, int k, String freq) {}

	@Override
	public boolean beforeSetTransmitterState(World world, int i, int j, int k, String freq, boolean state) {
		return false;
	}

	@Override
	public void afterSetTransmitterState(World world, int i, int j, int k, String freq, boolean state) {
		PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherFrequencyTilesToAll(
				RedstoneEther.getInstance().getTXNodes(),
				RedstoneEther.getInstance().getRXNodes(),
				WirelessRedstone.stateUpdate
		);
	}

	@Override
	public boolean beforeAddReceiver(World world, int i, int j, int k, String freq) {
		return false;
	}

	@Override
	public void afterAddReceiver(World world, int i, int j, int k, String freq) {
		RedstoneEtherNode node = new RedstoneEtherNode(i,j,k);
		node.freq = freq;
		
		PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherNodeTileToAll(node,0);
	}

	@Override
	public boolean beforeRemReceiver(World world, int i, int j, int k, String freq) {
		return false;
	}

	@Override
	public void afterRemReceiver(World world, int i, int j, int k, String freq) {}

	@Override
	public boolean beforeGetFreqState(World world, String freq) {
		return false;
	}

	@Override
	public boolean afterGetFreqState(World world, String freq, boolean returnState) {
		return returnState;
	}

	@Override
	public boolean beforeIsLoaded(World world, int i, int j, int k) {
		return false;
	}

	@Override
	public boolean afterIsLoaded(World world, int i, int j, int k, boolean returnState) {
		LoggerRedstoneWireless.getInstance("RedstoneEtherOverrideServer").write("isLoaded(world, "+i+", "+j+", "+k+")", LoggerRedstoneWireless.LogLevel.DEBUG);
		for (int b = 0; b < world.playerEntities.size(); b++)
		{
			EntityPlayerMP player = (EntityPlayerMP)world.playerEntities.get(b);
			if (player != null) {
				if ((int)player.posX == i && (int)player.posY+1 == j && (int)player.posZ == k) return true;
			}
		}
		return returnState;
	}

}
