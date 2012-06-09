package net.minecraft.src.wirelessredstone.addon.sniffer.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class RedstoneWirelessSnifferPageNumber {

	private static RedstoneWirelessSnifferPageNumber instance;
	private Map<Integer, SnifferPageNumber> pageNumber;
	private World world;

	private RedstoneWirelessSnifferPageNumber(World world) {
		pageNumber = new HashMap<Integer, SnifferPageNumber>();
		this.world = world;
	}

	public static RedstoneWirelessSnifferPageNumber getInstance(World world) {
		if (instance == null || instance.world.hashCode() != world.hashCode())
			instance = new RedstoneWirelessSnifferPageNumber(world);

		return instance;
	}

	private void addMem(ItemStack sniffer, int page) {
		SnifferPageNumber memnode = new SnifferPageNumber(sniffer, page);
		pageNumber.put(sniffer.getItemDamage(), memnode);
	}

	public void remMem(int itemdamage) {
		pageNumber.remove(itemdamage);
	}

	public void setPage(ItemStack sniffer, int page) {
		addMem(sniffer, page);
	}

	public int getPage(ItemStack sniffer) {
		SnifferPageNumber node = pageNumber.get(sniffer.getItemDamage());
		if (node == null) {
			addMem(sniffer, 0);
			return 0;
		} else {
			return node.pageNumber;
		}
	}
}
