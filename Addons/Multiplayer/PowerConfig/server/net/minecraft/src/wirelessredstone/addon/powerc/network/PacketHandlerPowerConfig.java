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
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.powerc.network.packet.PacketPowerConfigGui;
import net.minecraft.src.wirelessredstone.addon.powerc.network.packet.PacketPowerConfigSettings;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class PacketHandlerPowerConfig {
	
	public static void handlePacket(PacketUpdate packet, World world, EntityPlayer entityplayer)
	{
		if ( packet instanceof PacketPowerConfigGui ) {
			PacketHandlerInput.openGUI((PacketPowerConfigGui)packet, world, entityplayer);
		} else if ( packet instanceof PacketPowerConfigSettings ) {
			PacketHandlerInput.handlePowerConfig((PacketPowerConfigSettings)packet, world, entityplayer);
		}
	}
	

	private static class PacketHandlerInput {
		private static void openGUI(PacketPowerConfigGui packet, World world, EntityPlayer entityplayer)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("openGUI:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity entity = packet.getTarget(world);
			if (entity != null && entity instanceof TileEntityRedstoneWirelessR)
			{
				if (entityplayer.canPlayerEdit(packet.xPosition, packet.yPosition, packet.zPosition))
				{
					PacketHandlerOutput.sendPowerConfigGuiPacket((EntityPlayerMP)entityplayer, packet.xPosition, packet.yPosition, packet.zPosition);
				}
			}
		}

		private static void handlePowerConfig(PacketPowerConfigSettings packet, World world, EntityPlayer entityplayer)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handlePowerConfigPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity entity = packet.getTarget(world);
			if (entity != null && entity instanceof TileEntityRedstoneWirelessR)
			{
				TileEntityRedstoneWirelessR receiver = (TileEntityRedstoneWirelessR)entity;
				if (packet.getCommand().equals("Power Direction")) {
					receiver.flipPowerDirection(packet.getValue());
				}
				if (packet.getCommand().equals("Indirect Power")) {
					receiver.flipIndirectPower(packet.getValue());
				}
				int i = receiver.getBlockCoord(0);
				int j = receiver.getBlockCoord(1);
				int k = receiver.getBlockCoord(2);
				BlockRedstoneWireless.notifyNeighbors(world, i, j, k);
				receiver.onInventoryChanged();
			}
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendPowerConfigGuiPacket(EntityPlayerMP entityplayer, int i, int j, int k) {
			PacketPowerConfigGui packet = new PacketPowerConfigGui(i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendPowerConfigGuiPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			entityplayer.playerNetServerHandler.netManager.addToSendQueue(packet.getPacket());
		}
	}
}
