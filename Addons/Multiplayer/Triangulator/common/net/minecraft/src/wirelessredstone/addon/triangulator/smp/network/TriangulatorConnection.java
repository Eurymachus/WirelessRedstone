package net.minecraft.src.wirelessredstone.addon.triangulator.smp.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.triangulator.smp.network.packet.PacketWirelessTriangulatorGui;
import net.minecraft.src.wirelessredstone.addon.triangulator.smp.network.packet.PacketWirelessTriangulatorSettings;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;

public class TriangulatorConnection extends NetworkConnections {

	public TriangulatorConnection(String channel) {
		super(channel);
	}

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = WirelessRedstone.getWorld(network);
			EntityPlayer entityplayer = WirelessRedstone.getPlayer(network);
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ADDON:
				PacketWirelessTriangulatorSettings pWT = new PacketWirelessTriangulatorSettings();
				pWT.readData(data);
				PacketHandlerWirelessTriangulator.handlePacket(pWT, world,
						entityplayer);
				break;
			case PacketIds.GUI:
				PacketWirelessTriangulatorGui pWTG = new PacketWirelessTriangulatorGui();
				pWTG.readData(data);
				PacketHandlerWirelessTriangulator.handlePacket(pWTG, world,
						entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onPacketData(EntityPlayer entityplayer,
			Packet250CustomPayload packet) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ADDON:
				PacketWirelessTriangulatorSettings pWT = new PacketWirelessTriangulatorSettings();
				pWT.readData(data);
				PacketHandlerWirelessTriangulator.handlePacket(pWT,
						entityplayer.worldObj, entityplayer);
				break;
			case PacketIds.GUI:
				PacketWirelessTriangulatorGui pWTG = new PacketWirelessTriangulatorGui();
				pWTG.readData(data);
				PacketHandlerWirelessTriangulator.handlePacket(pWTG,
						entityplayer.worldObj, entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onConnect(NetworkManager network) {
	}

	@Override
	public void onDisconnect(NetworkManager network, String message,
			Object[] args) {
	}
}