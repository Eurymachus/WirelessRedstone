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
package net.minecraft.src.wirelessredstone.addon.remote.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteData;
import net.minecraft.src.wirelessredstone.addon.remote.network.packet.PacketWirelessRemoteOpenGui;
import net.minecraft.src.wirelessredstone.addon.remote.network.packet.PacketWirelessRemoteSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketHandlerWirelessRemote {
	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketWirelessRemoteSettings) {
			PacketHandlerInput.handleWirelessRemoteSettings(
					(PacketWirelessRemoteSettings) packet, world, entityplayer);
		}
	}

	private static class PacketHandlerInput {
		private static void handleWirelessRemoteSettings(
				PacketWirelessRemoteSettings packet, World world,
				EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessRemotePacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			if (packet.getCommand().equals("activateRemote")) {
				WirelessRemote.activateRemote(world, entityplayer);
			}
			if (packet.getCommand().equals("deactivateRemote")) {
				WirelessRemote.deactivateRemote(world, entityplayer);
			}
			if (packet.getCommand().equals("changeFreq")) {
				String index = WirelessRemote.itemRemote.getItemName();
				ModLoader.getLogger().warning(index + "[Freq:" +
				packet.getFreq() + "]");
				WirelessRemoteData data = WirelessRemote.getDeviceData(index,
						packet.getRemoteID(), "Wireless Remote", world,
						entityplayer);
				int freq = Integer.parseInt(packet.getFreq());
				int oldfreq = Integer.parseInt(data.getFreq());
				data.setFreq(Integer.toString(oldfreq + freq));
			}
		}
	}

	public static class PacketHandlerOutput {
		public static void sendWirelessRemotePacket(EntityPlayer entityplayer,
				String freq, int i, int j, int k) {
			PacketWirelessRemoteSettings packet = new PacketWirelessRemoteSettings(
					freq);
			packet.setPosition(i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessRemotePacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			((EntityPlayerMP) entityplayer).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessRemoteGuiPacket(
				EntityPlayer entityplayer, int deviceID, Object freq) {
			PacketWirelessRemoteOpenGui packet = new PacketWirelessRemoteOpenGui(
					deviceID);
			packet.setFreq(freq);
			((EntityPlayerMP) entityplayer).playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}
	}
}