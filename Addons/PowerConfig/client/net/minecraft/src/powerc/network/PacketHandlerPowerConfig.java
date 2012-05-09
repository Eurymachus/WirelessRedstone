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
package net.minecraft.src.powerc.network;

import net.minecraft.src.IInventory;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.powerc.GuiRedstoneWirelessPowerDirector;
import net.minecraft.src.wifi.BlockRedstoneWirelessR;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessR;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessT;
import net.minecraft.src.wifi.LoggerRedstoneWireless.LogLevel;
import net.minecraft.src.wifi.WirelessRedstone;
import net.minecraft.src.wifi.network.PacketOpenWindowRedstoneWireless;
import net.minecraft.src.wifi.network.PacketRedstoneEther;
import net.minecraft.src.wifi.network.PacketUpdate;

public class PacketHandlerPowerConfig {
	
	public static void handlePacket(PacketUpdate packet)
	{
		if ( packet instanceof PacketPowerConfigGui ) {
			PacketHandlerInput.openGUI((PacketPowerConfigGui)packet);
		} else if ( packet instanceof PacketPowerConfig ) {
			PacketHandlerInput.handlePowerConfig((PacketPowerConfig)packet);
		}
	}
	

	private static class PacketHandlerInput {
		private static void openGUI(PacketPowerConfigGui packet)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("openGUI:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity entity = packet.getTarget(ModLoader.getMinecraftInstance().theWorld);
			if (entity != null && entity instanceof TileEntityRedstoneWirelessR)
			{
				ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiRedstoneWirelessPowerDirector((TileEntityRedstoneWirelessR)entity));
			}
		}

		private static void handlePowerConfig(PacketPowerConfig packet)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handlePowerConfigPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity entity = packet.getTarget(ModLoader.getMinecraftInstance().theWorld);
			if (entity != null && entity instanceof TileEntityRedstoneWirelessR)
			{
				TileEntityRedstoneWirelessR receiver = (TileEntityRedstoneWirelessR)entity;
				if (packet.getCommand() == "Power Direction") receiver.flipPowerDirection(packet.getValue());
				if (packet.getCommand() == "Indirect Power") receiver.flipIndirectPower(packet.getValue());
				int i = receiver.getBlockCoord(0);
				int j = receiver.getBlockCoord(1);
				int k = receiver.getBlockCoord(2);
				((BlockRedstoneWirelessR)WirelessRedstone.blockWirelessR).notifyNeighbors(receiver.worldObj, i, j, k);
			}
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendPowerConfigPacket(String command, int i, int j, int k, int value) {
			PacketPowerConfig packet = new PacketPowerConfig(command);
			packet.setValue(value);
			packet.setPosition(i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendPowerConfigPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}
}