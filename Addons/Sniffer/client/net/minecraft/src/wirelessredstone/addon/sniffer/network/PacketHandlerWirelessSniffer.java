package net.minecraft.src.wirelessredstone.addon.sniffer.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.RedstoneWirelessSnifferFreqCoordsMem;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;

public class PacketHandlerWirelessSniffer {
	
	public static void handlePacket(PacketUpdate packet, World world, EntityPlayer entityplayer)
	{
		if ( packet instanceof PacketWirelessSnifferSettings ) {
			PacketHandlerInput.handleWirelessSniffer((PacketWirelessSnifferSettings)packet, world, entityplayer);
		}
	}
	

	private static class PacketHandlerInput {
		private static void handleWirelessSniffer(PacketWirelessSnifferSettings packet, World world, EntityPlayer entityplayer)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessSnifferPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			RedstoneWirelessSnifferFreqCoordsMem.getInstance(world).setFreq(0, 0, 0, packet.getFreq(), packet.getState());
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessSnifferPacket(EntityPlayer player, Object freq) {
			player.addChatMessage("Sending for freq: " + freq.toString());
			PacketWirelessSnifferSettings packet = new PacketWirelessSnifferSettings(freq.toString());
			packet.setPosition((int)player.posX, (int)player.posY, (int)player.posZ);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendWirelessSnifferPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}
}
