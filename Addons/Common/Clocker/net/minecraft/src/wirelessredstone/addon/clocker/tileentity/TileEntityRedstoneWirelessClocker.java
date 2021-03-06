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
package net.minecraft.src.wirelessredstone.addon.clocker.tileentity;

import java.util.concurrent.TimeUnit;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.wirelessredstone.addon.clocker.ThreadWirelessClocker;
import net.minecraft.src.wirelessredstone.addon.clocker.WirelessClocker;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class TileEntityRedstoneWirelessClocker extends
		TileEntityRedstoneWirelessT {
	private int clockFreq;
	private boolean running;

	public TileEntityRedstoneWirelessClocker() {
		super();
		clockFreq = 1000;
		running = false;
		this.blockRedstoneWireless = (BlockRedstoneWireless) WirelessClocker.blockClock;
	}

	@Override
	protected void onUpdateEntity() {
		ThreadWirelessClocker.getInstance().addTileEntity(this);
	}

	@Override
	public String getInvName() {
		return "Wireless Clocker";
	}

	public int getClockFreq() {
		return clockFreq;
	}

	public void setClockFreq(int i) {
		if (i > WirelessClocker.maxClockFreq)
			i = WirelessClocker.maxClockFreq;
		if (i < WirelessClocker.minClockFreq)
			i = WirelessClocker.minClockFreq;

		clockFreq = i;

		setClockState(running);
	}

	public String getClockFreqString() {
		String timer = "";

		if (this.clockFreq < 1000)
			timer = String.valueOf(this.clockFreq) + "ms";
		else if (this.clockFreq < 60000) {
			long seconds, milliseconds;
			seconds = TimeUnit.MILLISECONDS.toSeconds(this.clockFreq);
			milliseconds = TimeUnit.MILLISECONDS.toMillis(this.clockFreq)
					- TimeUnit.SECONDS.toMillis(seconds);
			timer = seconds + "sec " + milliseconds + "ms";
		} else {
			long minutes, seconds, milliseconds;
			minutes = TimeUnit.MILLISECONDS.toMinutes(this.clockFreq);
			seconds = TimeUnit.MILLISECONDS.toSeconds(this.clockFreq)
					- TimeUnit.MINUTES.toSeconds(minutes);
			milliseconds = TimeUnit.MILLISECONDS.toMillis(this.clockFreq)
					- TimeUnit.MINUTES.toMillis(minutes)
					- TimeUnit.SECONDS.toMillis(seconds);
			timer = minutes + "m " + seconds + "s " + milliseconds + "ms";
		}

		return timer;
	}

	public void setClockState(boolean state) {
		running = state;
	}

	public boolean getClockState() {
		return running;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		NBTTagList nbttaglist3 = nbttagcompound.getTagList("ClockFrequency");
		NBTTagCompound nbttagcompound3 = (NBTTagCompound) nbttaglist3.tagAt(0);
		clockFreq = nbttagcompound3.getInteger("clockFreq");

		NBTTagList nbttaglist2 = nbttagcompound.getTagList("ClockState");
		NBTTagCompound nbttagcompound2 = (NBTTagCompound) nbttaglist2.tagAt(0);
		running = nbttagcompound2.getBoolean("clockState");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		NBTTagList nbttaglist3 = new NBTTagList();
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setInteger("clockFreq", clockFreq);
		nbttaglist3.appendTag(nbttagcompound1);
		nbttagcompound.setTag("ClockFrequency", nbttaglist3);

		NBTTagList nbttaglist2 = new NBTTagList();
		NBTTagCompound nbttagcompound2 = new NBTTagCompound();
		nbttagcompound2.setBoolean("clockState", running);
		nbttaglist2.appendTag(nbttagcompound2);
		nbttagcompound.setTag("ClockState", nbttaglist2);
	}

	@Override
	public Packet getDescriptionPacket() {
		return TileEntityRedstoneWirelessClockerInjector
				.getDescriptionPacket(this);
	}
}
