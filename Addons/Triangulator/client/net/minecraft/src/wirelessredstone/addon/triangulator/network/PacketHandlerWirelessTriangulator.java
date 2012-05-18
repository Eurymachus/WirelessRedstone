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
import net.minecraft.src.wirelessredstone.addon.triangulator.network.packet.PacketWirelessTriangulatorSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessPlayerEtherCoordsMem;
import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;


public class PacketHandlerWirelessTriangulator {
	
	public static void handlePacket(PacketUpdate packet, World world, EntityPlayer entityplayer)
	{
		if ( packet instanceof PacketWirelessTriangulatorSettings ) {
			PacketHandlerInput.handleWirelessTriangulator((PacketWirelessTriangulatorSettings)packet, world, entityplayer);
		}
	}
	

	private static class PacketHandlerInput {
		private static void handleWirelessTriangulator(PacketWirelessTriangulatorSettings packet, World world, EntityPlayer entityplayer)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessTriangulatorPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			RedstoneWirelessPlayerEtherCoordsMem.getInstance(world).setCoords(entityplayer, packet.getCoords());
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessTriangulatorPacket(EntityPlayer player, String freq) {
			PacketWirelessTriangulatorSettings packet = new PacketWirelessTriangulatorSettings(freq);
			packet.setPosition((int)player.posX, (int)player.posY, (int)player.posZ);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendRedstoneEtherPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}
}