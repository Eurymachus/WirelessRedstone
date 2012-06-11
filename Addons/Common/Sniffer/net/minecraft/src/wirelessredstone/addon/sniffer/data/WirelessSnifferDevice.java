package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSniffer;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.data.WirelessDevice;

public class WirelessSnifferDevice extends WirelessDevice {

	public WirelessSnifferDevice(World world, EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		this.world = world;
		this.owner = entityplayer;
		this.data = (WirelessSnifferData) WirelessRedstone.getDeviceData(
						WirelessSnifferData.class,
						itemstack.getItem().getItemName(),
						itemstack.getItemDamage(),
						world,
						entityplayer);
	}

	public int getPage() {
		return ((WirelessSnifferData)data).getPage();
	}

	public void setPage(int pageNumber) {
		((WirelessSnifferData)data).setPage(pageNumber);
	}

	public boolean isInPlayerInventory() {
		for (int i = 0; i < this.owner.inventory.getSizeInventory(); ++i) {
			ItemStack currentStack = this.owner.inventory.getStackInSlot(i);
			if (currentStack != null
					&& currentStack.getItem() == WirelessSniffer.itemSniffer
					&& currentStack.getItemDamage() == this.data.getID())
				return true;
		}
		return false;
	}

	@Override
	public void activate() {
		if (this.data != null) {}
	}

	@Override
	public void deactivate() {
		if (this.data != null) {}
	}
}
