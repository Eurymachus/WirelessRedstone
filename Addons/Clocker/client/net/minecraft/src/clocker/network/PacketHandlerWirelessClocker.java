package net.minecraft.src.clocker.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.clocker.TileEntityRedstoneWirelessClocker;
import net.minecraft.src.clocker.WirelessClocker;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.network.PacketUpdate;

public class PacketHandlerWirelessClocker {
	
	public static void handlePacket(PacketUpdate packet, EntityPlayer player)
	{
		if ( packet instanceof PacketWirelessClockerSettings ) {
			PacketHandlerInput.handleWirelessClockerSettings((PacketWirelessClockerSettings)packet, player);
		} else if ( packet instanceof PacketWirelessClockerGui ) {
			PacketHandlerInput.handleWirelessClockerGui((PacketWirelessClockerGui)packet, player);
		} else if ( packet instanceof PacketWirelessClockerTile ) {
			PacketHandlerInput.handleWirelessClockerTile((PacketWirelessClockerTile)packet, player);
		}
	}
	

	private static class PacketHandlerInput {
		private static void handleWirelessClockerSettings(PacketWirelessClockerSettings packet, EntityPlayer player)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessClockerSettingsPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		}

		private static void handleWirelessClockerGui(PacketWirelessClockerGui packet, EntityPlayer player) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessClockerGuiPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity tileentity = packet.getTarget(player.worldObj);
			if (tileentity != null && tileentity instanceof TileEntityRedstoneWirelessClocker) {
				WirelessClocker.guiClock.assTileEntity((TileEntityRedstoneWirelessClocker)tileentity);
				ModLoader.openGUI(player, WirelessClocker.guiClock);
			}
		}

		public static void handleWirelessClockerTile(PacketWirelessClockerTile packet, EntityPlayer player) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessClockerTilePacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity tileentity = packet.getTarget(player.worldObj);
			if (tileentity != null && tileentity instanceof TileEntityRedstoneWirelessClocker) {
				((TileEntityRedstoneWirelessClocker)tileentity).setClockFreq(Integer.parseInt(packet.getClockFreq()));
				((TileEntityRedstoneWirelessClocker)tileentity).setFreq(packet.getFreq());
				tileentity.onInventoryChanged();
				player.worldObj.markBlockAsNeedsUpdate(packet.xPosition, packet.yPosition, packet.zPosition);
			}
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessClockerPacket(EntityPlayer player, String clockFreq, int i, int j, int k) {
			PacketWirelessClockerSettings packet = new PacketWirelessClockerSettings(clockFreq, i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendWirelessClockerPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}

}
