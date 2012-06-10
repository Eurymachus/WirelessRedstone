package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MapCoord;
import net.minecraft.src.MapInfo;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.WorldSavedData;
import net.minecraft.src.wirelessredstone.data.IWirelessDevice;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;

public class WirelessSnifferData extends WirelessDeviceData {
	
	protected int pageNumber;
	
	public WirelessSnifferData(String par1Str) {
		super(par1Str);
	}
	
	public int getPageNumber() {
		return this.pageNumber;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		this.markDirty();
	}
	
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.pageNumber = nbttagcompound.getInteger("pagenumber");
	}
    
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("pagenumber", this.pageNumber);
	}
}
