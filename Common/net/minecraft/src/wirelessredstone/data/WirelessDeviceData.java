package net.minecraft.src.wirelessredstone.data;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.WorldSavedData;

public abstract class WirelessDeviceData extends WorldSavedData {
	protected int id;
	protected String name;
	protected Byte dimension;
	protected String freq;

	public WirelessDeviceData(String par1Str) {
		super(par1Str);
	}

	public WirelessDeviceData(String index, int id, String name, World world,
			EntityPlayer entityplayer) {
		this(index);
		this.setID(id);
		this.setName(name);
		this.setDimension(world);
		this.setFreq("0");
	}

	public void setID(ItemStack itemstack) {
		this.setID(itemstack.getItemDamage());
	}

	public void setID(int id) {
		this.id = id;
		this.markDirty();
	}

	public void setName(String name) {
		this.name = name;
		this.markDirty();
	}

	public void setDimension(World world) {
		this.dimension = (byte) world.worldProvider.worldType;
		this.markDirty();
	}

	public void setFreq(String freq) {
		this.freq = freq;
		this.markDirty();
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Byte getDimension() {
		return this.dimension;
	}

	public String getFreq() {
		return this.freq;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.id = nbttagcompound.getInteger("id");
		this.name = nbttagcompound.getString("name");
		this.dimension = nbttagcompound.getByte("dimension");
		this.freq = nbttagcompound.getString("freq");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("id", this.id);
		nbttagcompound.setString("name", this.name);
		nbttagcompound.setByte("dimension", this.dimension);
		nbttagcompound.setString("freq", this.freq);
	}

}
