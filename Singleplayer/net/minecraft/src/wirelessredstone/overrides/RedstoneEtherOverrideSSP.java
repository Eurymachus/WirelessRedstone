package net.minecraft.src.wirelessredstone.overrides;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.ether.RedstoneEtherOverride;

public class RedstoneEtherOverrideSSP implements RedstoneEtherOverride {
	@Override
	public boolean beforeAddTransmitter(World world, int i, int j, int k,
			String freq) {
		return false;
	}

	@Override
	public void afterAddTransmitter(World world, int i, int j, int k,
			String freq) {
	}

	@Override
	public boolean beforeRemTransmitter(World world, int i, int j, int k,
			String freq) {
		return false;
	}

	@Override
	public void afterRemTransmitter(World world, int i, int j, int k,
			String freq) {
		
	}

	@Override
	public boolean beforeSetTransmitterState(World world, int i, int j, int k,
			String freq, boolean state) {
		return false;
	}

	@Override
	public void afterSetTransmitterState(World world, int i, int j, int k,
			String freq, boolean state) {
		
	}

	@Override
	public boolean beforeAddReceiver(World world, int i, int j, int k,
			String freq) {
		return false;
	}

	@Override
	public void afterAddReceiver(World world, int i, int j, int k, String freq) {		
	}

	@Override
	public boolean beforeRemReceiver(World world, int i, int j, int k,
			String freq) {
		return false;
	}

	@Override
	public void afterRemReceiver(World world, int i, int j, int k, String freq) {
	}

	@Override
	public boolean beforeGetFreqState(World world, String freq) {
		return false;
	}

	@Override
	public boolean afterGetFreqState(World world, String freq,
			boolean returnState) {
		return returnState;
	}

	@Override
	public boolean beforeIsLoaded(World world, int i, int j, int k) {
		return false;
	}

	@Override
	public boolean afterIsLoaded(World world, int i, int j, int k, boolean returnState) {
		LoggerRedstoneWireless.getInstance("RedstoneEtherOverrideSSP").write("isLoaded(world, "+i+", "+j+", "+k+")", LoggerRedstoneWireless.LogLevel.DEBUG);
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
		
		return returnState;
	}
}