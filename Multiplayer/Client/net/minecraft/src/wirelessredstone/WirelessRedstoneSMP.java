package net.minecraft.src.wirelessredstone;

import java.util.HashMap;

import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_WirelessRedstoneSMP;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
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
	public static HashMap <String, NetworkConnections> redstoneWirelessConnections;

	public static boolean initialize() {
		redstoneWirelessConnections = new HashMap();

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

	public static void addConnection(
			NetworkConnections connection) {
		if (!redstoneWirelessConnections.containsKey(connection.channel)) {
			redstoneWirelessConnections.put(connection.channel, connection);
		}
	}
	
	public static NetworkConnections getConnection(String channel) {
		if (redstoneWirelessConnections.containsKey(channel)) {
			return redstoneWirelessConnections.get(channel);
		}
		return null;
	}
	
	public static void removeConnection(String channel) {
		if (redstoneWirelessConnections.containsKey(channel)) {
			redstoneWirelessConnections.remove(channel);
		}
	}

/*	private static void registerConnHandler() {
		redstoneWirelessConnection = new RedstoneWirelessConnection("WIFI");
		//MinecraftForge.registerConnectionHandler(redstoneWirelessConnection);
		ModLoader.registerPacketChannel(mod_WirelessRedstoneSMP.instance, redstoneWirelessConnection.channel);
		ModLoader.getLogger().warning("Mod is: " + FMLCommonHandler.instance().getModForChannel(redstoneWirelessConnection.channel).getName());
	}*/
}
