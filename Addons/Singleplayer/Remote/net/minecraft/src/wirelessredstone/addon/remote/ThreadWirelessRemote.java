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
package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.smp.client.network.PacketHandlerRedstoneWireless;

public class ThreadWirelessRemote implements Runnable {
	protected int i;
	protected int j;
	protected int k;
	String freq;
	protected World world;
	protected EntityPlayer player;
	public static int tc = 0;

	public ThreadWirelessRemote(EntityPlayer player, String freq) {
		this.i = (int) player.posX;
		this.j = (int) player.posY;
		this.k = (int) player.posZ;
		this.freq = freq;
		this.player = player;
		this.world = player.worldObj;
	}

	@Override
	public void run() {
		tc++;
		RedstoneEther.getInstance().addTransmitter(world, i, j, k, freq);
		if (world.isRemote)
			PacketHandlerRedstoneWireless.PacketHandlerOutput
					.sendRedstoneEtherPacket("addTransmitter", i, j, k, freq,
							false);

		WirelessRemote.itemRemote.setIconIndex(WirelessRemote.remoteon);
		RedstoneEther.getInstance().setTransmitterState(world, i, j, k, freq,
				true);
		if (world.isRemote)
			PacketHandlerRedstoneWireless.PacketHandlerOutput
					.sendRedstoneEtherPacket("setTransmitterState", i, j, k,
							freq, true);

		if (WirelessRemote.pulseTime > 0) {
			try {
				Thread.sleep(WirelessRemote.pulseTime);
			} catch (InterruptedException e) {
				LoggerRedstoneWireless.getInstance("WirelessRedstone.Remote")
						.writeStackTrace(e);
			}
		}

		WirelessRemote.itemRemote.setIconIndex(WirelessRemote.remoteoff);
		RedstoneEther.getInstance().remTransmitter(world, i, j, k, freq);
		if (world.isRemote)
			PacketHandlerRedstoneWireless.PacketHandlerOutput
					.sendRedstoneEtherPacket("remTransmitter", i, j, k, freq,
							false);
		tc--;
	}

	private boolean playerChangedPosition(EntityPlayer entityplayer) {
		if ((int) entityplayer.posX == i && (int) entityplayer.posY == j
				&& (int) entityplayer.posZ == k) {
			return false;
		}
		return true;
	}

	public static void pulse(EntityPlayer entityplayer, String freq) {
		if (tc < WirelessRemote.maxPulseThreads) {
			Thread thr = new Thread(
					new ThreadWirelessRemote(entityplayer, freq));
			thr.setName("WirelessRemoteThread");
			thr.start();
		}
	}
}
