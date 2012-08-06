package net.minecraft.src.wirelessredstone;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.smp.overrides.BaseModOverrideSMP;
import net.minecraft.src.wirelessredstone.smp.overrides.BlockRedstoneWirelessOverrideSMP;
import net.minecraft.src.wirelessredstone.smp.overrides.GuiRedstoneWirelessInventoryOverrideSMP;
import net.minecraft.src.wirelessredstone.smp.overrides.RedstoneEtherOverrideSMP;
import net.minecraft.src.wirelessredstone.smp.overrides.TileEntityRedstoneWirelessOverrideSMP;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * WirelessRedstoneSMP class
 * 
 * To allow abstraction from the BaseModSMP code
 * 
 * @author Eurymachus
 * 
 */
public class WirelessRedstoneSMP {
	public static boolean isLoaded = false;

	public static boolean initialize() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection("WIFI"));

		GuiRedstoneWirelessInventoryOverrideSMP GUIOverride = new GuiRedstoneWirelessInventoryOverrideSMP();
		WirelessRedstone.addGuiOverrideToReceiver(GUIOverride);
		WirelessRedstone.addGuiOverrideToTransmitter(GUIOverride);

		BlockRedstoneWirelessOverrideSMP blockOverride = new BlockRedstoneWirelessOverrideSMP();
		WirelessRedstone.addOverrideToReceiver(blockOverride);
		WirelessRedstone.addOverrideToTransmitter(blockOverride);

		TileEntityRedstoneWirelessOverrideSMP tileOverride = new TileEntityRedstoneWirelessOverrideSMP();
		TileEntityRedstoneWireless.addOverride(tileOverride);

		RedstoneEtherOverrideSMP etherOverrideSMP = new RedstoneEtherOverrideSMP();
		RedstoneEther.getInstance().addOverride(etherOverrideSMP);

		BaseModOverrideSMP baseModOverride = new BaseModOverrideSMP();
		WirelessRedstone.addOverride(baseModOverride);
		return true;
	}
}
