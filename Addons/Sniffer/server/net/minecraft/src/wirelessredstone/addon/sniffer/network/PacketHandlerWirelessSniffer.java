package net.minecraft.src.wirelessredstone.addon.sniffer.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.RedstoneEther;
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
			boolean state = RedstoneEther.getInstance().getFreqState(world, packet.getFreq());
			PacketHandlerOutput.sendWirelessSnifferPacket(entityplayer, packet.getFreq(), state);
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessSnifferPacket(EntityPlayer player, Object freq, boolean state) {
			PacketWirelessSnifferSettings packet = new PacketWirelessSnifferSettings(freq.toString(), state);
			packet.setPosition((int)player.posX, (int)player.posY, (int)player.posZ);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendRedstoneEtherPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			((EntityPlayerMP)player).playerNetServerHandler.netManager.addToSendQueue(packet.getPacket());
		}
	}
}
