package net.minecraft.src.wirelessredstone.addon.triangulator.overrides;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneEtherCoordsPlayerMem;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;
import net.minecraft.src.wirelessredstone.ether.RedstoneEtherOverride;

public class RedstoneEtherOverrideTriangulator implements RedstoneEtherOverride {

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
		// TODO Auto-generated method stub

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
	public boolean afterIsLoaded(World world, int i, int j, int k,
			boolean returnState) {
		return returnState;
	}

	@Override
	public int[] beforeGetClosestActiveTransmitter(int i, int j, int k,
			String freq) {
		if (WirelessRedstone.getWorld().isRemote) {
			World world = WirelessRedstone.getWorld();
			EntityPlayer entityplayer = WirelessRedstone.getPlayer();
			WirelessCoordinates wirelessCoords = RedstoneEtherCoordsPlayerMem
					.getInstance(world).getCoords(entityplayer);
			int[] coords = null;
			try {
				if (wirelessCoords != null
						&& wirelessCoords.getCoordinateArray() != null) {
					coords = wirelessCoords.getCoordinateArray();
					return coords;
				}
			} catch (Exception e) {
				LoggerRedstoneWireless.getInstance(
						"WirelessRedstone: " + this.getClass().toString())
						.writeStackTrace(e);
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
