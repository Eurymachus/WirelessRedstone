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
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.Remote;
import net.minecraft.src.wirelessredstone.addon.remote.network.packet.PacketWirelessRemoteGui;
import net.minecraft.src.wirelessredstone.addon.remote.network.packet.PacketWirelessRemoteSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;


public class PacketHandlerWirelessRemote {
	
	public static void handlePacket(PacketUpdate packet, World world, EntityPlayer player)
	{
		if ( packet instanceof PacketWirelessRemoteSettings )
			PacketHandlerInput.handleWirelessRemote((PacketWirelessRemoteSettings)packet);
		else if ( packet instanceof PacketWirelessRemoteGui )
			PacketHandlerInput.handleWirelessRemoteGui((PacketWirelessRemoteGui)packet);
	}
	

	private static class PacketHandlerInput {
		private static void handleWirelessRemote(PacketWirelessRemoteSettings packet)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessRemotePacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		}

		public static void handleWirelessRemoteGui(PacketWirelessRemoteGui packet)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessRemoteGuiPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessRemotePacket(String command, Remote remote)
		{
			PacketWirelessRemoteSettings packet = new PacketWirelessRemoteSettings(command);
			packet.setPosition(remote.getCoords().getX(), remote.getCoords().getY(), remote.getCoords().getZ());
			packet.setFreq(remote.getFreq());
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendRedstoneEtherPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}
}