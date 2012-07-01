package net.minecraft.src.wirelessredstone.addon.clocker.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.clocker.TileEntityRedstoneWirelessClocker;
import net.minecraft.src.wirelessredstone.addon.clocker.network.packet.PacketWirelessClockerGui;
import net.minecraft.src.wirelessredstone.addon.clocker.network.packet.PacketWirelessClockerSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketHandlerWirelessClocker {

	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer player) {
		if (packet instanceof PacketWirelessClockerSettings) {
			PacketHandlerInput.handleWirelessClockerSettings(
					(PacketWirelessClockerSettings) packet, world, player);
		}
	}

	private static class PacketHandlerInput {
		private static void handleWirelessClockerSettings(
				PacketWirelessClockerSettings packet, World world,
				EntityPlayer player) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessClockerPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity tileentity = packet.getTarget(world);
			if (tileentity != null
					&& tileentity instanceof TileEntityRedstoneWirelessClocker) {
				int dFreq = Integer.parseInt(packet.getClockFreq());
				int oldFreq = ((TileEntityRedstoneWirelessClocker) tileentity)
						.getClockFreq();
				TileEntityRedstoneWirelessClocker twc = (TileEntityRedstoneWirelessClocker) tileentity;
				twc.setClockFreq(oldFreq + dFreq);
				twc.onInventoryChanged();
				player.worldObj.markBlockNeedsUpdate(packet.xPosition,
						packet.yPosition, packet.zPosition);
			}
		}
	}

	public static class PacketHandlerOutput {
		public static void sendWirelessClockerPacket(EntityPlayer player,
				String clockFreq, int i, int j, int k) {
			PacketWirelessClockerSettings packet = new PacketWirelessClockerSettings(
					clockFreq, i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessClockerPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			((EntityPlayerMP) player).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessClockerGuiPacket(EntityPlayer player,
				int clockFreq, int i, int j, int k) {
			PacketWirelessClockerGui packet = new PacketWirelessClockerGui(
					clockFreq, i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessClockerGuiPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			((EntityPlayerMP) player).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}
	}
}
