/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package net.minecraft.src.wirelessredstone.addon.triangulator.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.triangulator.WirelessTriangulator;
import net.minecraft.src.wirelessredstone.addon.triangulator.data.WirelessTriangulatorData;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.packet.PacketWirelessTriangulatorGui;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.packet.PacketWirelessTriangulatorSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketHandlerWirelessTriangulator {

	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketWirelessTriangulatorSettings) {
			PacketHandlerInput.handleWirelessTriangulator(
					(PacketWirelessTriangulatorSettings) packet, world,
					entityplayer);
		}
	}

	private static class PacketHandlerInput {
		private static void handleWirelessTriangulator(
				PacketWirelessTriangulatorSettings packet, World world,
				EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessTriangulatorPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			if (packet.getCommand().equals("requestTriangulation")) {
				int[] tx = RedstoneEther.getInstance()
						.getClosestActiveTransmitter(packet.xPosition,
								packet.yPosition, packet.zPosition,
								packet.getFreq());
				if (tx != null) {
					PacketHandlerWirelessTriangulator.PacketHandlerOutput
							.sendWirelessTriangulatorPacket(entityplayer, tx,
									packet.getFreq());
				} else
					PacketHandlerWirelessTriangulator.PacketHandlerOutput
							.sendWirelessTriangulatorPacket(entityplayer,
									packet.getCoords(), "Reset");
			}
			if (packet.getCommand().equals("changeFreq")) {
				String index = WirelessTriangulator.itemTriang.getItemName();
				WirelessTriangulatorData data = WirelessTriangulator
						.getDeviceData(index, packet.getDeviceID(),
								"Wireless Triangulator", world, entityplayer);
				data.setFreq(packet.getFreq());
			}
		}
	}

	public static class PacketHandlerOutput {
		public static void sendWirelessTriangulatorPacket(
				EntityPlayer entityplayer, int[] tx, String freq) {
			PacketWirelessTriangulatorSettings packet = new PacketWirelessTriangulatorSettings(
					freq);
			packet.setPosition(tx[0], tx[1], tx[2]);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessTriangulatorPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			((EntityPlayerMP) entityplayer).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessTriangulatorZeroPacket(
				EntityPlayer entityplayer, String freq) {
			PacketWirelessTriangulatorSettings packet = new PacketWirelessTriangulatorSettings(
					freq);
			packet.setPosition((int) entityplayer.posX,
					(int) entityplayer.posY, (int) entityplayer.posZ);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessTriangulatorPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			((EntityPlayerMP) entityplayer).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessTriangulatorGuiPacket(
				EntityPlayer entityplayer, int deviceID) {
			PacketWirelessTriangulatorGui packet = new PacketWirelessTriangulatorGui(
					deviceID);
			((EntityPlayerMP) entityplayer).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}
	}
}