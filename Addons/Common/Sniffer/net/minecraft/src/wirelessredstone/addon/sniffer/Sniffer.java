package net.minecraft.src.wirelessredstone.addon.sniffer;

import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.RedstoneWirelessSnifferPageNumber;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class Sniffer {
	public World world;
	public EntityPlayer owner;
	public ItemStack itemstack;

	public Sniffer(World world, EntityPlayer entityplayer) {
		this.itemstack = entityplayer.getCurrentEquippedItem();
		this.world = world;
		this.owner = entityplayer;
	}

	protected int getPage() {
		return RedstoneWirelessSnifferPageNumber.getInstance(this.world)
				.getPage(this.itemstack);
	}

	protected void setPage(int pageNumber) {
		RedstoneWirelessSnifferPageNumber.getInstance(this.world).setPage(
				this.itemstack, pageNumber);
	}

	protected void killSniffer() {
		RedstoneWirelessSnifferPageNumber.getInstance(this.world).remMem(
				this.itemstack.getItemDamage());
	}
}
