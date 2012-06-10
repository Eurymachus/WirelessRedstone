package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSniffer;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.RedstoneWirelessSnifferPageNumber;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.data.WirelessDevice;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class WirelessSnifferDevice extends WirelessDevice {
	public ItemStack itemstack;

	public WirelessSnifferDevice(World world, EntityPlayer entityplayer) {
		this.itemstack = entityplayer.getCurrentEquippedItem();
		this.world = world;
		this.owner = entityplayer;
	}

	public int getPage() {
		return RedstoneWirelessSnifferPageNumber.getInstance(this.world)
				.getPage(this.itemstack);
	}

	public void setPage(int pageNumber) {
		RedstoneWirelessSnifferPageNumber.getInstance(this.world).setPage(
				this.itemstack, pageNumber);
	}

	protected void killSniffer() {
		RedstoneWirelessSnifferPageNumber.getInstance(this.world).remMem(
				this.itemstack.getItemDamage());
	}

	public boolean isInPlayerInventory() {
		for (int i = 0; i < this.owner.inventory.getSizeInventory(); ++i) {
			ItemStack currentStack = this.owner.inventory.getStackInSlot(i);
			if (currentStack != null
				&& currentStack.getItem() == WirelessSniffer.itemSniffer
				&& currentStack.getItemDamage() == this.itemstack.getItemDamage())
				return true;
		}
		return false;
	}
	
	@Override
	public void activate() {
		if (this.itemstack != null)
		RedstoneWirelessItemStackMem.getInstance(this.world).setState(
				this.itemstack, true);
	}

	@Override
	public void deactivate() {
		if (this.itemstack != null)
		RedstoneWirelessItemStackMem.getInstance(this.world).setState(
				this.itemstack, false);
	}
}
