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
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.triangulator.WirelessTriangulator;
import net.minecraft.src.wirelessredstone.addon.triangulator.data.WirelessTriangulatorData;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.packet.PacketWirelessTriangulatorGui;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.packet.PacketWirelessTriangulatorSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessPlayerEtherCoordsMem;
import net.minecraft.src.wirelessredstone.data.WirelessCoordinates;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketHandlerWirelessTriangulator {

	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketWirelessTriangulatorGui) {
			PacketHandlerInput
					.handleWirelessTriangulator(
							(PacketWirelessTriangulatorGui) packet, world,
							entityplayer);
		}
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
			ModLoader.getLogger().warning("TriangPacket:" + packet.getCommand());
			RedstoneWirelessPlayerEtherCoordsMem.getInstance(world).setCoords(
					entityplayer, new WirelessCoordinates(packet.getCoords()));
		}

		public static void handleWirelessTriangulator(
				PacketWirelessTriangulatorGui packet, World world,
				EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessTriangulatorGuiPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			String index = WirelessTriangulator.itemTriang.getItemName();
			WirelessTriangulatorData deviceData = WirelessTriangulator
					.getDeviceData(index, packet.getDeviceID(),
							"Wireless Triangulator", world, entityplayer);
			deviceData.setFreq(packet.getFreq());
			WirelessTriangulator.activateGUI(world, entityplayer, deviceData);
		}
	}

	public static class PacketHandlerOutput {
		public static void sendWirelessTriangulatorPacket(EntityPlayer player,
				String command, String freq) {
			PacketWirelessTriangulatorSettings packet = new PacketWirelessTriangulatorSettings(
					command);
			packet.setPosition((int) player.posX, (int) player.posY,
					(int) player.posZ);
			packet.setFreq(freq);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendRedstoneEtherPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessTriangulatorPacket(String command,
				int id, Object freq) {
			PacketWirelessTriangulatorSettings packet = new PacketWirelessTriangulatorSettings(
					command);
			packet.setDeviceID(id);
			packet.setFreq(freq);
			ModLoader.getLogger().warning(packet.getCommand() + "[" + packet.getDeviceID() + "].toFreq[" + packet.getFreq() + "]");
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendRedstoneEtherPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}
	}
}