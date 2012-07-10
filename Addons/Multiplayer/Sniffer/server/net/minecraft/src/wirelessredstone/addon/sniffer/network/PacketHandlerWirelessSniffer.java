package net.minecraft.src.wirelessredstone.addon.sniffer.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSniffer;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferData;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferEtherCopy;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferOpenGui;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
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

			WirelessSnifferData sniffer = WirelessSniffer.getDeviceData(
					WirelessSniffer.itemSniffer.getItemName(),
					packet.getDeviceID(), "Wireless Sniffer", world,
					entityplayer);

			sniffer.setPage(packet.getPageNumber());
		}

		public static void handleWirelessSniffer(
				PacketWirelessSnifferOpenGui packet, World world,
				EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessSnifferGuiPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			String[] activeFreqs = new String[WirelessRedstone.maxEtherFrequencies];
			int j = 0;
			for (int i = 0; i < WirelessRedstone.maxEtherFrequencies; ++i) {
				if (RedstoneEther.getInstance().getFreqState(world,
						String.valueOf(i))) {
					activeFreqs[j] = String.valueOf(i);
					++j;
				}
			}
			String[] newActiveFreqs = new String[j];
			for (int i = 0; i < j; ++i) {
				newActiveFreqs[i] = activeFreqs[i];
			}
			PacketHandlerOutput.sendWirelessSnifferEtherCopy(entityplayer,
					newActiveFreqs);
		}
	}

	public static class PacketHandlerOutput {
		public static void sendWirelessSnifferPacket(EntityPlayer player,
				Object freq, boolean state) {
		}

		public static void sendWirelessSnifferEtherCopy(
				EntityPlayer entityplayer, String[] activeFreqs) {
			PacketWirelessSnifferEtherCopy packet = new PacketWirelessSnifferEtherCopy(
					activeFreqs);
			((EntityPlayerMP) entityplayer).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessSnifferGuiPacket(
				EntityPlayer entityplayer, int deviceID, int pageNumber) {
			PacketWirelessSnifferOpenGui packet = new PacketWirelessSnifferOpenGui(
					deviceID);
			packet.setPageNumber(pageNumber);
			((EntityPlayerMP) entityplayer).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}
	}
}
