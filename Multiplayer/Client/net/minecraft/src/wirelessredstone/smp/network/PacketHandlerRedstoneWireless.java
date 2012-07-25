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
package net.minecraft.src.wirelessredstone.smp.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWireless;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessInventory;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketRedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketRedstoneWirelessOpenGui;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketWirelessTile;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class PacketHandlerRedstoneWireless {

	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketRedstoneWirelessOpenGui) {
			PacketHandlerInput.openGUI((PacketRedstoneWirelessOpenGui) packet,
					world, entityplayer);
		} else if (packet instanceof PacketRedstoneEther) {
			PacketHandlerInput.handleEtherPacket((PacketRedstoneEther) packet,
					world, entityplayer);
		} else if (packet instanceof PacketWirelessTile) {
			PacketHandlerInput.handleTilePacket((PacketWirelessTile) packet,
					world, entityplayer);
		}
	}

	private static class PacketHandlerInput {
		private static void openGUI(PacketRedstoneWirelessOpenGui packet,
				World world, EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"openGUI:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity tileentity = packet.getTarget(world);
			WirelessRedstone.activateGUI(world, entityplayer, tileentity);
		}

		private static void handleTilePacket(PacketWirelessTile packet,
				World world, EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleTilePacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity tileentity = packet.getTarget(world);
			if (packet.getCommand().equals("fetchTile")) {
				if (tileentity != null && tileentity instanceof TileEntityRedstoneWireless) {
					TileEntityRedstoneWireless tileentityredstonewireless = (TileEntityRedstoneWireless)tileentity;
					if (tileentity instanceof TileEntityRedstoneWirelessT) {
						((TileEntityRedstoneWirelessT) tileentity).setFreq(packet
							.getFreq().toString());
						tileentity.onInventoryChanged();
						world.markBlockAsNeedsUpdate(packet.xPosition,
							packet.yPosition, packet.zPosition);
					}
					if (tileentity instanceof TileEntityRedstoneWirelessR) {
						TileEntityRedstoneWirelessR teR = (TileEntityRedstoneWirelessR) tileentity;
						teR.setFreq(packet.getFreq().toString());
						teR.setPowerDirections(packet.getPowerDirections());
						teR.setInDirectlyPowering(packet.getInDirectlyPowering());
						tileentity.onInventoryChanged();
						world.markBlockAsNeedsUpdate(packet.xPosition,
								packet.yPosition, packet.zPosition);
					}
					GuiScreen screen = ModLoader.getMinecraftInstance().currentScreen;
					if (screen instanceof GuiRedstoneWireless) {
						if (screen instanceof GuiRedstoneWirelessInventory) {
							if (((GuiRedstoneWirelessInventory)screen).compareInventory(tileentityredstonewireless)) {
								((GuiRedstoneWireless)screen).refreshGui();
							}
						}
					}
				}
			}
		}

		private static void handleEtherPacket(PacketRedstoneEther packet,
				World world, EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleEtherPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity tileentity = packet.getTarget(world);
			if (packet.getCommand().equals("addTransmitter")) {
				if (tileentity != null
						&& tileentity instanceof TileEntityRedstoneWirelessT) {
					((TileEntityRedstoneWireless) tileentity).setFreq(packet
							.getFreq().toString());
				} else {
					tileentity = new TileEntityRedstoneWirelessT();
					((TileEntityRedstoneWireless) tileentity).setFreq(packet
							.getFreq().toString());
					world.setBlockTileEntity(packet.xPosition,
							packet.yPosition, packet.zPosition, tileentity);

				}
				RedstoneEther.getInstance().addTransmitter(world,
						packet.xPosition, packet.yPosition, packet.zPosition,
						packet.getFreq().toString());

			} else if (packet.getCommand().equals("addReceiver")) {
				if (tileentity != null
						&& tileentity instanceof TileEntityRedstoneWirelessR) {
					((TileEntityRedstoneWireless) tileentity).setFreq(packet
							.getFreq().toString());
				} else {
					tileentity = new TileEntityRedstoneWirelessR();
					((TileEntityRedstoneWireless) tileentity).setFreq(packet
							.getFreq().toString());
					world.setBlockTileEntity(packet.xPosition,
							packet.yPosition, packet.zPosition, tileentity);
				}
				RedstoneEther.getInstance().addReceiver(world,
						packet.xPosition, packet.yPosition, packet.zPosition,
						packet.getFreq().toString());
			}
		}
	}

	public static class PacketHandlerOutput {
		public static void sendRedstoneEtherPacket(String command, int i,
				int j, int k, Object freq, boolean state) {
			PacketRedstoneEther packet = new PacketRedstoneEther(command);
			packet.setPosition(i, j, k);
			packet.setFreq(freq);
			packet.setState(state);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendRedstoneEtherPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}
	}
}
