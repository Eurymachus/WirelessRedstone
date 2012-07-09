package net.minecraft.src.wirelessredstone.addon.triangulator.overrides;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessPlayerEtherCoordsMem;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;
import net.minecraft.src.wirelessredstone.ether.RedstoneEtherOverride;

public class RedstoneEtherOverrideTriangulator implements RedstoneEtherOverride {

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
		// TODO Auto-generated method stub
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

	@Override
	public int[] beforeGetClosestActiveTransmitter(int i, int j, int k,
			String freq) {
		if (WirelessRedstone.getWorld().isRemote) {
			int[] coords = (RedstoneWirelessPlayerEtherCoordsMem.getInstance(WirelessRedstone.getWorld()).getCoords(WirelessRedstone.getPlayer())).getCoordinateArray();
			if (coords != null) {
				ModLoader.getLogger().warning("Coords[" + coords[0] + "," + coords[1] + "," + coords[2]);
				return coords;
			}
		}
		return null;
	}

	@Override
	public int[] afterGetClosestActiveTransmitter(int i, int j, int k,
			String freq, int[] coords) {
		return coords;
	}

}
