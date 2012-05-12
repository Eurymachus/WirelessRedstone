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
package net.minecraft.src.wifiremote;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessRemote;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.RedstoneEther;

public class ThreadWirelessRemote implements Runnable {
	protected int i;
	protected int j;
	protected int k;
	protected ItemStack itemstack;
	protected World world;
	protected static EntityPlayer player;
	public static int tc = 0;
	
	
	public ThreadWirelessRemote(int i, int j, int k, ItemStack itemstack, World world) {
		this.i = i;
		this.j = j;
		this.k = k;
		this.itemstack = itemstack;
		this.world = world;
	}


	@Override
	public void run() {
		tc++;
			String freq = MemRedstoneWirelessRemote.getInstance(world).getFreq(itemstack.hashCode());

			RedstoneEther.getInstance().addTransmitter(
					ModLoader.getMinecraftInstance().theWorld,
					i,j,k,
					freq
			);
	
	    	RedstoneEther.getInstance().setTransmitterState(
					ModLoader.getMinecraftInstance().theWorld,
					i,j,k,
					freq,
					true
	    	);
	    	
	    	if ( WirelessRemote.pulseTime > 0 ) {
				try {
					Thread.sleep(WirelessRemote.pulseTime);
				} catch (InterruptedException e) {
					LoggerRedstoneWireless.getInstance("WirelessRedstone.Remote").writeStackTrace(e);
				}
	    	}
			
			RedstoneEther.getInstance().remTransmitter(
					ModLoader.getMinecraftInstance().theWorld,
					i,j,k,
					freq
			);
		tc--;
	}
	
	private boolean playerChangedPosition() {
		if ((int)ModLoader.getMinecraftInstance().thePlayer.posX == i &&
			(int)ModLoader.getMinecraftInstance().thePlayer.posY == j &&
			(int)ModLoader.getMinecraftInstance().thePlayer.posZ == k) {
			return false;
		}
		return true;
	}


	public static void pulse(EntityPlayer entityplayer, World world) {
		player = entityplayer;
		int x, y, z;
		x = (int)player.posX;
		y = (int)player.posY;
		z = (int)player.posZ;
		if ( tc < WirelessRemote.maxPulseThreads ) {
			Thread thr = new Thread(new ThreadWirelessRemote(x,y,z,player.getCurrentEquippedItem(),world));
			thr.setName("WirelessRemoteThread");
			thr.start();
		}
	}
}
