package net.minecraft.src.wifi;

import net.minecraft.src.World;
import net.minecraft.src.wifi.network.PacketHandlerRedstoneWireless;

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

}
