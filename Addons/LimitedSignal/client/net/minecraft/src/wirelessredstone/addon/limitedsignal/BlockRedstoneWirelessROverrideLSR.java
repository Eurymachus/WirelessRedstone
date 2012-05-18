/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
*/
package net.minecraft.src.wirelessredstone.addon.limitedsignal;

import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessLimitedSignal;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.wirelessredstone.RedstoneEther;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessR;

public class BlockRedstoneWirelessROverrideLSR implements BlockRedstoneWirelessOverride {
	@Override
	public boolean beforeUpdateRedstoneWirelessTick(World world, int i, int j, int k, Random random) {
		if ( world.isRemote ) return false;
		
		if ( ((mod_WirelessLimitedSignal)mod_WirelessLimitedSignal.instance).isTicking(i, j, k) ) return true;
		((mod_WirelessLimitedSignal)mod_WirelessLimitedSignal.instance).addTicking(i, j, k);
		
		String freq = ((BlockRedstoneWirelessR)WirelessRedstone.blockWirelessR).getFreq(world,i,j,k);
		
		int[] tx = RedstoneEther.getInstance().getClosestActiveTransmitter(i, j, k, freq);
		float SNR = 0.0f;
		float range = 0.0f;
		int[] rx = {i,j,k};
		if ( tx != null) {
			SNR = mod_WirelessLimitedSignal.getSNRMultiplier(rx, tx, world);
			range = mod_WirelessLimitedSignal.getRange(rx, tx);
		} else {
			tx = RedstoneEther.getInstance().getClosestTransmitter(i, j, k, freq);
			if ( tx != null) {
				SNR = mod_WirelessLimitedSignal.getSNRMultiplier(rx, tx, world);
				range = mod_WirelessLimitedSignal.getRange(rx, tx);
			}
		}
		
		Thread th = new Thread(new ThreadWirelessLimitedSignal(i,j,k,world,SNR,range));
		th.setName("WirelessLimitedSignalThread");
		th.start();
		
		return true;
	}

	@Override
	public void afterBlockRedstoneWirelessAdded(World world, int i, int j, int k) {}

	@Override
	public void afterBlockRedstoneWirelessRemoved(World world, int i, int j, int k) {}

	@Override
	public void afterBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {}

	@Override
	public void afterBlockRedstoneWirelessNeighborChange(World world, int i, int j, int k, int l) {}

	@Override
	public boolean beforeBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public boolean beforeBlockRedstoneWirelessAdded(World world, int i, int j, int k) {
		return false;
	}

	@Override
	public boolean beforeBlockRedstoneWirelessRemoved(World world, int i, int j, int k) {
		return false;
	}

	@Override
	public boolean beforeBlockRedstoneWirelessNeighborChange(World world, int i, int j, int k, int l) {
		return false;
	}

	@Override
	public void afterUpdateRedstoneWirelessTick(World world, int i, int j, int k, Random random) {
	}
}
