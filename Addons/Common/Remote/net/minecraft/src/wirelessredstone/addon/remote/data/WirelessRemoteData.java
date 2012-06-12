package net.minecraft.src.wirelessredstone.addon.remote.data;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;

public class WirelessRemoteData extends WirelessDeviceData {
	private boolean state;

	public WirelessRemoteData(String index, int id, String name, World world,
			EntityPlayer entityplayer) {
		super(index, id, name, world, entityplayer);
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
