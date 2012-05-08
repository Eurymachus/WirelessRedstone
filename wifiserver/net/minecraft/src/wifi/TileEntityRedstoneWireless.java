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
package net.minecraft.src.wifi;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.wifi.network.PacketPayload;
import net.minecraft.src.wifi.network.PacketRedstoneEther;

public abstract class TileEntityRedstoneWireless extends TileEntity implements IInventory {
	public boolean firstTick = true;
	public String oldFreq;
	public String currentFreq;
	protected boolean[] powerRoute;
	protected boolean[] indirPower;
    
	public TileEntityRedstoneWireless() {
		firstTick = true;
		oldFreq = "";
		currentFreq = "0";
		firstTick = false;
		flushPowerRoute();
		flushIndirPower();
	}
	
	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return null;
	}

	public String getFreq() {
		return currentFreq;
	}
	
	public void setFreq(String i) {
		try {			
			currentFreq = i;
			if ( worldObj != null )
				updateEntity();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}

	public int getBlockCoord(int i) {
		switch(i) {
			case 0:
				return this.xCoord;
			case 1:
				return this.yCoord;
			case 2:
				return this.zCoord;
			default:
				return 0;
		}
	}

	@Override
	public abstract void updateEntity();
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		onInventoryChanged();
	}

	@Override
	public abstract String getInvName();

	public boolean isPoweringDirection(int l) {
		if ( l < 6 )
			return powerRoute[l];
		else return false;
	}
	
	public void flipPowerDirection(int l) {
		if ( isPoweringIndirectly(l) && powerRoute[l] )
			flipIndirectPower(l);
		
		powerRoute[l] = !powerRoute[l];
	}
	
	public void flushPowerRoute() {
		powerRoute = new boolean[6];
		for ( int i = 0; i < powerRoute.length; i++ ) {
			powerRoute[i] = true;
		}
	}
	

	public void flipIndirectPower(int l) {
		if ( !isPoweringDirection(l) && !indirPower[l] )
			flipPowerDirection(l);
		
		indirPower[l] = !indirPower[l];
	}
	public boolean isPoweringIndirectly(int l) {
		if ( l < 6 )
			return indirPower[l];
		else return false;
	}

	public void flushIndirPower() {
		indirPower = new boolean[6];
		for ( int i = 0; i < indirPower.length; i++ ) {
			indirPower[i] = true;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		try {
			super.readFromNBT(nbttagcompound);
			
			NBTTagList nbttaglist3 = nbttagcompound.getTagList("Frequency");
			NBTTagCompound nbttagcompound3 = (NBTTagCompound)nbttaglist3.tagAt(0);
			currentFreq = nbttagcompound3.getString("freq");
	
			
			NBTTagList nbttaglist1 = nbttagcompound.getTagList("PowerRoute");
			if ( nbttaglist1.tagCount() == 6 ) {
				for(int i = 0; i < nbttaglist1.tagCount(); i++) {
					NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist1.tagAt(i);
					powerRoute[i] = nbttagcompound1.getBoolean("b");
				}
			} else {
				flushPowerRoute();
				writeToNBT(nbttagcompound);
			}
			
			NBTTagList nbttaglist4 = nbttagcompound.getTagList("IndirPower");
			if ( nbttaglist4.tagCount() == 6 ) {
				for(int i = 0; i < nbttaglist4.tagCount(); i++) {
					NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist4.tagAt(i);
					indirPower[i] = nbttagcompound1.getBoolean("b");
				}
			} else {
				flushIndirPower();
				writeToNBT(nbttagcompound);
			}
			
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		try {
			super.writeToNBT(nbttagcompound);
			
			NBTTagList nbttaglist3 = new NBTTagList();
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setString("freq", currentFreq.toString());
			nbttaglist3.appendTag(nbttagcompound1);
			nbttagcompound.setTag("Frequency", nbttaglist3);
			
			NBTTagList nbttaglist2 = new NBTTagList();
			for(int i = 0; i < powerRoute.length; i++) {
				NBTTagCompound nbttagcompound2 = new NBTTagCompound();
				nbttagcompound2.setBoolean("b", powerRoute[i]);
				nbttaglist2.appendTag(nbttagcompound2);
			}
			nbttagcompound.setTag("PowerRoute", nbttaglist2);
			
			NBTTagList nbttaglist4 = new NBTTagList();
			for(int i = 0; i < indirPower.length; i++) {
				NBTTagCompound nbttagcompound2 = new NBTTagCompound();
				nbttagcompound2.setBoolean("b", indirPower[i]);
				nbttaglist4.appendTag(nbttagcompound2);
			}
			nbttagcompound.setTag("IndirPower", nbttaglist4);
			
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
    
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		try {
			if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
				return false;
			}
			return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
			return false;
		}
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}
	
	public Packet getDescriptionPacket()
	{
		return getUpdatePacket();
	}
	
	public Packet getUpdatePacket()
	{
		return new PacketRedstoneEther(this).getPacket();
	}
}
