package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;

public class WirelessSnifferData extends WirelessDeviceData {
	
	public WirelessSnifferData(String index) {
		super(index);
	}

	public int getPage() {
		return Integer.valueOf(this.getFreq());
	}

	public void setPage(Object pageNumber) {
		this.freq = pageNumber.toString();
		this.markDirty();
	}
}
