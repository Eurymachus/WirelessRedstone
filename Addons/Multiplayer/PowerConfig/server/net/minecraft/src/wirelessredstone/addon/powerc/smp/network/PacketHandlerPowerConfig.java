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
package net.minecraft.src.wirelessredstone.addon.powerc.smp.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.powerc.smp.network.packet.PacketPowerConfigOpenGui;
import net.minecraft.src.wirelessredstone.addon.powerc.smp.network.packet.PacketPowerConfigSettings;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.PacketHandlerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class PacketHandlerPowerConfig {

	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketPowerConfigOpenGui) {
			PacketHandlerInput.openGUI((PacketPowerConfigOpenGui) packet,
					world, entityplayer);
		} else if (packet instanceof PacketPowerConfigSettings) {
			PacketHandlerInput.handlePowerConfig(
					(PacketPowerConfigSettings) packet, world, entityplayer);
		}
	}

	private static class PacketHandlerInput {
		private static void openGUI(PacketPowerConfigOpenGui packet,
				World world, EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"openGUI:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity entity = packet.getTarget(world);
			if (entity != null && entity instanceof TileEntityRedstoneWirelessR) {
				if (entityplayer.canPlayerEdit(packet.xPosition,
						packet.yPosition, packet.zPosition)) {
					PacketHandlerOutput.sendPowerConfigGuiPacket(
							(EntityPlayerMP) entityplayer,
							(TileEntityRedstoneWirelessR) packet
									.getTarget(world));
				}
			}
		}

		private static void handlePowerConfig(PacketPowerConfigSettings packet,
				World world, EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handlePowerConfigPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity tileentity = packet.getTarget(world);
			if (tileentity != null
					&& tileentity instanceof TileEntityRedstoneWirelessR) {
				TileEntityRedstoneWirelessR receiver = (TileEntityRedstoneWirelessR) tileentity;
				if (packet.getCommand().equals("Power Direction")) {
					receiver.flipPowerDirection(packet.getValue());
				}
				if (packet.getCommand().equals("Indirect Power")) {
					receiver.flipIndirectPower(packet.getValue());
				}
				int i = receiver.getBlockCoord(0);
				int j = receiver.getBlockCoord(1);
				int k = receiver.getBlockCoord(2);
				receiver.onInventoryChanged();
				BlockRedstoneWireless.notifyNeighbors(world, i, j, k);
				world.markBlockNeedsUpdate(i, j, k);
				PacketHandlerRedstoneWireless.PacketHandlerOutput
						.sendWirelessTileToAll(receiver, world, 16);
			}
		}
	}

	public static class PacketHandlerOutput {
		public static void sendPowerConfigGuiPacket(
				EntityPlayerMP entityplayer, TileEntity tileentity) {
			PacketPowerConfigOpenGui packet = new PacketPowerConfigOpenGui(
					tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendPowerConfigGuiPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			entityplayer.playerNetServerHandler.netManager
					.addToSendQueue(packet.getPacket());
		}
	}
}
