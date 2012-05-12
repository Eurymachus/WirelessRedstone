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
package net.minecraft.src.wifi.network;

import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessR;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessT;
import net.minecraft.src.wifi.LoggerRedstoneWireless.LogLevel;
import net.minecraft.src.wifi.WirelessRedstone;
import net.minecraft.src.wifi.network.PacketOpenWindowRedstoneWireless;
import net.minecraft.src.wifi.network.PacketRedstoneEther;
import net.minecraft.src.wifi.network.PacketUpdate;

public class PacketHandlerRedstoneWireless {
	
	public static void handlePacket(PacketUpdate packet) {
		if ( packet instanceof PacketOpenWindowRedstoneWireless ) {
			PacketHandlerInput.openGUI((PacketOpenWindowRedstoneWireless)packet);
		} else if ( packet instanceof PacketRedstoneEther ) {
			PacketHandlerInput.handleEtherPacket((PacketRedstoneEther)packet);
		} else if ( packet instanceof PacketWirelessTile ) {
			PacketHandlerInput.handleTilePacket((PacketWirelessTile)packet);
		}
	}
	

	private static class PacketHandlerInput {
		private static void openGUI(PacketOpenWindowRedstoneWireless packet) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("openGUI:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity entity = ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(
					packet.xPosition,
					packet.yPosition, 
					packet.zPosition
			);
			
			// Wireless Receiver
			if ( packet.getType() == 0 ) {
				if ( entity == null ) 	{
					entity = new TileEntityRedstoneWirelessR();
					((TileEntityRedstoneWireless)entity).setFreq(packet.getCurrentFreq().toString());
					ModLoader.getMinecraftInstance().theWorld.setBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition, entity);
				}
				
				if ( entity instanceof TileEntityRedstoneWireless ) {
					WirelessRedstone.guiWirelessR.assTileEntity((TileEntityRedstoneWireless) entity);
					ModLoader.openGUI(
							ModLoader.getMinecraftInstance().thePlayer,
							WirelessRedstone.guiWirelessR
					);
				}
			// Wireless Transmitter
			} else if ( packet.getType() == 1 ) {
				if ( entity == null ) {
					entity = new TileEntityRedstoneWirelessT();
					((TileEntityRedstoneWireless)entity).setFreq(packet.getCurrentFreq().toString());
					ModLoader.getMinecraftInstance().theWorld.setBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition, entity);
				}

				if ( entity instanceof TileEntityRedstoneWireless ) {
					WirelessRedstone.guiWirelessT.assTileEntity((TileEntityRedstoneWireless) entity);
					ModLoader.openGUI(
							ModLoader.getMinecraftInstance().thePlayer,
							WirelessRedstone.guiWirelessT
					);
				}
			}
			

			
			if ( 
					((TileEntityRedstoneWireless)entity).getBlockCoord(0) != packet.xPosition || 
					((TileEntityRedstoneWireless)entity).getBlockCoord(1) != packet.yPosition || 
					((TileEntityRedstoneWireless)entity).getBlockCoord(2) != packet.zPosition 
				) 
			if ( !((TileEntityRedstoneWireless)entity).getFreq().equals(packet.getCurrentFreq()) )
				((TileEntityRedstoneWireless)entity).setFreq(packet.getCurrentFreq().toString());
		}

		public static void handleTilePacket(PacketWirelessTile packet) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleTilePacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity entity = ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition);
			if ( packet.getCommand().equals("fetchTile") )
			{
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Fetching Tile");
				if ( entity != null && entity instanceof TileEntityRedstoneWirelessT)
				{
					((TileEntityRedstoneWireless)entity).setFreq(packet.getFreq().toString());
					entity.onInventoryChanged();
					ModLoader.getMinecraftInstance().theWorld.markBlockAsNeedsUpdate(packet.xPosition, packet.yPosition, packet.zPosition);
				}
				if ( entity != null && entity instanceof TileEntityRedstoneWirelessR)
				{
					TileEntityRedstoneWireless teR = (TileEntityRedstoneWireless)entity;
					teR.setFreq(packet.getFreq().toString());
					teR.setPowerDirections(packet.getPowerDirections());
					teR.setInDirectlyPowering(packet.getInDirectlyPowering());
					entity.onInventoryChanged();
					ModLoader.getMinecraftInstance().theWorld.markBlockAsNeedsUpdate(packet.xPosition, packet.yPosition, packet.zPosition);
				}
			}		
		}

		private static void handleEtherPacket(PacketRedstoneEther packet)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleEtherPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity entity = ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition);
			if ( packet.getCommand().equals("addTransmitter") )
			{
				if ( entity != null && entity instanceof TileEntityRedstoneWirelessT)
				{
					((TileEntityRedstoneWireless)entity).setFreq(packet.getFreq().toString());
				}
				else
				{
					entity = new TileEntityRedstoneWirelessT();
					((TileEntityRedstoneWireless)entity).setFreq(packet.getFreq().toString());
					ModLoader.getMinecraftInstance().theWorld.setBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition, entity);
				}
			}
			else if ( packet.getCommand().equals("addReceiver") )
			{
				if ( entity != null && entity instanceof TileEntityRedstoneWirelessR)
				{
					((TileEntityRedstoneWireless)entity).setFreq(packet.getFreq().toString());
				}
				else
				{
					entity = new TileEntityRedstoneWirelessR();
					((TileEntityRedstoneWireless)entity).setFreq(packet.getFreq().toString());
					ModLoader.getMinecraftInstance().theWorld.setBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition, entity);
				}
			}
		}
	}

	public static class PacketHandlerOutput {
		public static void sendRedstoneEtherPacket(String command, int i, int j, int k, Object freq, boolean state) {
			PacketRedstoneEther packet = new PacketRedstoneEther(command);
			packet.setPosition(i, j, k);
			packet.setFreq(freq);
			packet.setState(state);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendRedstoneEtherPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}
}
