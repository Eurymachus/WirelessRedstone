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
package net.minecraft.src.wifiremote.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.network.PacketUpdate;


public class PacketHandlerWirelessRemote {
	
	public static void handlePacket(PacketUpdate packet, EntityPlayerMP player)
	{
		if ( packet instanceof PacketWirelessRemoteSettings ) {
			PacketHandlerInput.handleWirelessRemoteSettings((PacketWirelessRemoteSettings)packet, player);
		}
	}
	

	private static class PacketHandlerInput {
		private static void handleWirelessRemoteSettings(PacketWirelessRemoteSettings packet, EntityPlayerMP player)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessRemotePacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			
			//ThreadWirelessRemote.pulse(
			//		player, packet.getFreq());
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessRemotePacket(EntityPlayer player, String freq, int i, int j, int k) {
			PacketWirelessRemoteSettings packet = new PacketWirelessRemoteSettings(freq);
			packet.setPosition(i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendWirelessRemotePacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			((EntityPlayerMP)player).playerNetServerHandler.netManager.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessRemoteGuiPacket(EntityPlayer player, int itemDamage, int i,
				int j, int k) {
			PacketWirelessRemoteGui packet = new PacketWirelessRemoteGui(itemDamage, i, j, k);
			((EntityPlayerMP)player).playerNetServerHandler.netManager.addToSendQueue(packet.getPacket());
		}
	}
}