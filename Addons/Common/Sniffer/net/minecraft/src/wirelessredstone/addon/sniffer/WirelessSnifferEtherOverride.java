package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.ether.RedstoneEtherOverride;

public class WirelessSnifferEtherOverride implements RedstoneEtherOverride
{

	@Override
	public boolean beforeAddTransmitter(World world, int i, int j, int k,
			String freq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterAddTransmitter(World world, int i, int j, int k,
			String freq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeRemTransmitter(World world, int i, int j, int k,
			String freq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterRemTransmitter(World world, int i, int j, int k,
			String freq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeSetTransmitterState(World world, int i, int j, int k,
			String freq, boolean state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterSetTransmitterState(World world, int i, int j, int k,
			String freq, boolean state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeAddReceiver(World world, int i, int j, int k,
			String freq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterAddReceiver(World world, int i, int j, int k, String freq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeRemReceiver(World world, int i, int j, int k,
			String freq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterRemReceiver(World world, int i, int j, int k, String freq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeGetFreqState(World world, String freq) {
		return false;
	}

	@Override
	public boolean afterGetFreqState(World world, String freq,
			boolean returnState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean beforeIsLoaded(World world, int i, int j, int k) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean afterIsLoaded(World world, int i, int j, int k,
			boolean returnState) {
		// TODO Auto-generated method stub
		return false;
	}
}