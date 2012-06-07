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
package net.minecraft.src.wirelessredstone.addon.powerc.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.powerc.GuiRedstoneWirelessPowerDirector;
import net.minecraft.src.wirelessredstone.addon.powerc.network.packet.PacketPowerConfigGui;
import net.minecraft.src.wirelessredstone.addon.powerc.network.packet.PacketPowerConfigSettings;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class PacketHandlerPowerConfig {
	
	public static void handlePacket(PacketUpdate packet, World world, EntityPlayer player)
	{
		if ( packet instanceof PacketPowerConfigGui ) {
			PacketHandlerInput.openGUI((PacketPowerConfigGui)packet, world, player);
		} else if ( packet instanceof PacketPowerConfigSettings ) {
			PacketHandlerInput.handlePowerConfig((PacketPowerConfigSettings)packet, world, player);
		}
	}
	

	private static class PacketHandlerInput {
		private static void openGUI(PacketPowerConfigGui packet, World world, EntityPlayer player)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("openGUI:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity entity = packet.getTarget(world);
			if (entity != null && entity instanceof TileEntityRedstoneWirelessR)
			{
				ModLoader.openGUI(player, new GuiRedstoneWirelessPowerDirector((TileEntityRedstoneWirelessR)entity));
			}
		}

		private static void handlePowerConfig(PacketPowerConfigSettings packet, World world, EntityPlayer player)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handlePowerConfigPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity entity = packet.getTarget(world);
			if (entity != null && entity instanceof TileEntityRedstoneWirelessR)
			{
				TileEntityRedstoneWirelessR receiver = (TileEntityRedstoneWirelessR)entity;
				if (packet.getCommand() == "Power Direction") receiver.flipPowerDirection(packet.getValue());
				if (packet.getCommand() == "Indirect Power") receiver.flipIndirectPower(packet.getValue());
				int i = receiver.getBlockCoord(0);
				int j = receiver.getBlockCoord(1);
				int k = receiver.getBlockCoord(2);
				BlockRedstoneWireless.notifyNeighbors(world, i, j, k);
			}
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendPowerConfigPacket(String command, int i, int j, int k, int value) {
			PacketPowerConfigSettings packet = new PacketPowerConfigSettings(command);
			packet.setValue(value);
			packet.setPosition(i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendPowerConfigPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}
}