package net.minecraft.src.wirelessredstone.addon.remote.data;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;

public class WirelessRemoteData extends WirelessDeviceData {
	private boolean state;

	public WirelessRemoteData(String par1Str) {
		super(par1Str);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.state = nbttagcompound.getBoolean("state");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("state", this.state);
	}

	public boolean getDeviceState() {
		return this.state;
	}

	public void setDeviceState(boolean state) {
		this.state = state;
		this.markDirty();
	}
}
