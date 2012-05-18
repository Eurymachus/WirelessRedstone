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

import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessLimitedSignal;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class ThreadWirelessLimitedSignal implements Runnable {
	private float SNR;
	private float range;
	private int i;
	private int j;
	private int k;
	private World world;
	
	public ThreadWirelessLimitedSignal(int i, int j, int k, World world, float SNR, float range) {
		this.SNR = SNR;
		this.range = range;
		this.i = i;
		this.j = j;
		this.k = k;
		this.world = world;
	}

	@Override
	public void run() {
		if ( range > mod_WirelessLimitedSignal.getMaxRange() ) return;
		
		try {
			Thread.sleep((long) Math.ceil(SNR*mod_WirelessLimitedSignal.getRangeMultiplier()*range));
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone.LimitedSignal").writeStackTrace(e);
		}
		
		((BlockRedstoneWirelessR)WirelessRedstone.blockWirelessR).updateRedstoneWirelessTick(world, i, j, k, null);
		((mod_WirelessLimitedSignal)mod_WirelessLimitedSignal.instance).remTicking(i, j, k);
	}
}
