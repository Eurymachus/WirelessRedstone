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

import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.wifi.BlockRedstoneWirelessR;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessR;
import net.minecraft.src.wifi.WirelessRedstone;
import net.minecraft.src.wifi.network.PacketUpdate;


public class PacketHandlerWirelessRemote {
	
	public static void handlePacket(PacketUpdate packet)
	{
		if ( packet instanceof PacketWirelessRemote ) {
			PacketHandlerInput.handleWirelessRemote((PacketWirelessRemote)packet);
		}
	}
	

	private static class PacketHandlerInput {
		private static void handleWirelessRemote(PacketWirelessRemote packet)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessRemotePacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity entity = packet.getTarget(ModLoader.getMinecraftInstance().theWorld);
			if (entity != null && entity instanceof TileEntityRedstoneWirelessR)
			{
				TileEntityRedstoneWirelessR receiver = (TileEntityRedstoneWirelessR)entity;
				int i = receiver.getBlockCoord(0);
				int j = receiver.getBlockCoord(1);
				int k = receiver.getBlockCoord(2);
				((BlockRedstoneWirelessR)WirelessRedstone.blockWirelessR).notifyNeighbors(receiver.worldObj, i, j, k);
			}
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessRemotePacket(boolean state, int i, int j, int k, String freq) {
			PacketWirelessRemoteSettings packet = new PacketWirelessRemoteSettings(state);
			packet.setPosition(i, j, k);
			packet.setFreq(freq);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendWirelessRemotePacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}
}