package net.minecraft.src.wirelessredstone.addon.sniffer.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.sniffer.GuiRedstoneWirelessSniffer;
import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSniffer;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferData;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferEtherCopy;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferOpenGui;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketHandlerWirelessSniffer {

	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketWirelessSnifferSettings) {
			PacketHandlerInput
					.handleWirelessSniffer(
							(PacketWirelessSnifferSettings) packet, world,
							entityplayer);
		}
		if (packet instanceof PacketWirelessSnifferEtherCopy) {
			PacketHandlerInput.handleWirelessSniffer(
					(PacketWirelessSnifferEtherCopy) packet, world,
					entityplayer);
		}
		if (packet instanceof PacketWirelessSnifferOpenGui) {
			PacketHandlerInput.handleWirelessSniffer(
					(PacketWirelessSnifferOpenGui) packet, world, entityplayer);
		}
	}

	private static class PacketHandlerInput {
		private static void handleWirelessSniffer(
				PacketWirelessSnifferSettings packet, World world,
				EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessSnifferPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			// RedstoneWirelessSnifferFreqCoordsMem.getInstance(world).setFreq(0,
			// 0, 0, packet.getFreq(), packet.getState());
		}

		public static void handleWirelessSniffer(
				PacketWirelessSnifferOpenGui packet, World world,
				EntityPlayer entityplayer) {
			String index = WirelessSniffer.itemSniffer.getItemName();
			WirelessSnifferData data = WirelessSniffer
					.getDeviceData(index, packet.getDeviceID(),
							"Wireless Sniffer", world, entityplayer);
			data.setPage(packet.getPageNumber());
			WirelessSniffer.activateGUI(world, entityplayer, data);
		}

		public static void handleWirelessSniffer(
				PacketWirelessSnifferEtherCopy packet, World world,
				EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessSnifferEtherCopy:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			GuiScreen theScreen = ModLoader.getMinecraftInstance().currentScreen;
			if (theScreen != null
					&& theScreen instanceof GuiRedstoneWirelessSniffer) {
				WirelessSniffer.guiSniffer.setActiveFreqs(packet
						.getActiveFreqs());
			}
		}
	}

	public static class PacketHandlerOutput {
		public static void sendWirelessSnifferPacket(String command,
				int pageNumber, int deviceID) {
			PacketWirelessSnifferSettings packet = new PacketWirelessSnifferSettings(
					command);
			packet.setDeviceID(deviceID);
			packet.setPageNumber(pageNumber);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessSnifferPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessSnifferGuiPacket(int deviceID) {
			PacketWirelessSnifferOpenGui packet = new PacketWirelessSnifferOpenGui(
					deviceID);
			packet.setState(false);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessSnifferOpenGui:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}
	}
}
