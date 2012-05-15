package net.minecraft.src.clocker.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.clocker.TileEntityRedstoneWirelessClocker;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.network.PacketUpdate;

public class PacketHandlerWirelessClocker {
	
	public static void handlePacket(PacketUpdate packet, EntityPlayerMP player)
	{
		if ( packet instanceof PacketWirelessClockerSettings ) {
			PacketHandlerInput.handleWirelessClockerSettings((PacketWirelessClockerSettings)packet, player);
		}
	}
	

	private static class PacketHandlerInput {
		private static void handleWirelessClockerSettings(PacketWirelessClockerSettings packet, EntityPlayerMP player)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessClockerPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity tileentity = packet.getTarget(player.worldObj);
			if (tileentity != null && tileentity instanceof TileEntityRedstoneWirelessClocker) {
				TileEntityRedstoneWirelessClocker twc = (TileEntityRedstoneWirelessClocker)tileentity;
				twc.setClockFreq(Integer.parseInt(packet.getClockFreq()));
				twc.onInventoryChanged();
				player.worldObj.markBlockNeedsUpdate(packet.xPosition, packet.yPosition, packet.zPosition);
			}
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessClockerPacket(EntityPlayer player, String clockFreq, int i, int j, int k) {
			PacketWirelessClockerSettings packet = new PacketWirelessClockerSettings(clockFreq, i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendWirelessClockerPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			((EntityPlayerMP)player).playerNetServerHandler.netManager.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessClockerGuiPacket(EntityPlayer player, int clockFreq, int i,
				int j, int k) {
			PacketWirelessClockerGui packet = new PacketWirelessClockerGui(clockFreq, i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendWirelessClockerGuiPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			((EntityPlayerMP)player).playerNetServerHandler.netManager.addToSendQueue(packet.getPacket());
		}
	}
}
