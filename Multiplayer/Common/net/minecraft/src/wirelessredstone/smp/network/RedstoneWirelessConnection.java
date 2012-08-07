package net.minecraft.src.wirelessredstone.smp.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketRedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketRedstoneWirelessOpenGui;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketWirelessTile;

public class RedstoneWirelessConnection extends NetworkConnections {

	public RedstoneWirelessConnection(String channel) {
		super(channel);
	}

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = WirelessRedstone.getWorld(network);
			EntityPlayer player = WirelessRedstone.getPlayer(network);
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ETHER:
				PacketRedstoneEther pRE = new PacketRedstoneEther();
				pRE.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pRE, world, player);
				break;
			case PacketIds.GUI:
				PacketRedstoneWirelessOpenGui pORW = new PacketRedstoneWirelessOpenGui();
				pORW.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pORW, world, player);
				break;
			case PacketIds.TILE:
				PacketWirelessTile pWT = new PacketWirelessTile();
				pWT.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pWT, world, player);
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

	@Override
	public void onPacketData(EntityPlayer entityplayer,
			Packet250CustomPayload packet) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			World world = entityplayer.worldObj;
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ETHER:
				PacketRedstoneEther pRE = new PacketRedstoneEther();
				pRE.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pRE, world,
						entityplayer);
				break;
			case PacketIds.GUI:
				PacketRedstoneWirelessOpenGui pORW = new PacketRedstoneWirelessOpenGui();
				pORW.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pORW, world,
						entityplayer);
				break;
			case PacketIds.TILE:
				PacketWirelessTile pWT = new PacketWirelessTile();
				pWT.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pWT, world,
						entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}