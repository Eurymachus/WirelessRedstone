package net.minecraft.src.wirelessredstone.addon.powerc.smp.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.powerc.smp.network.packet.PacketPowerConfigOpenGui;
import net.minecraft.src.wirelessredstone.addon.powerc.smp.network.packet.PacketPowerConfigSettings;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;

public class PowerConfigConnection extends NetworkConnections {

	public PowerConfigConnection(EntityPlayer entityplayer, String channel) {
		super(entityplayer, channel);
	}

	@Override
	public void onConnect(NetworkManager network) {
	}

	@Override
	public void onDisconnect(NetworkManager network, String message,
			Object[] args) {
	}

	@Override
	public void onPacketData(Packet250CustomPayload packet) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			World world = entityplayer.worldObj;
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ADDON:
				PacketPowerConfigSettings pPC = new PacketPowerConfigSettings();
				pPC.readData(data);
				PacketHandlerPowerConfig.handlePacket(pPC, world, entityplayer);
				break;
			case PacketIds.GUI:
				PacketPowerConfigOpenGui pPCGui = new PacketPowerConfigOpenGui();
				pPCGui.readData(data);
				PacketHandlerPowerConfig.handlePacket(pPCGui, world,
						entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}