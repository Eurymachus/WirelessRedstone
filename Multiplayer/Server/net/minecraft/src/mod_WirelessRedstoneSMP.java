package net.minecraft.src;

import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
import net.minecraft.src.wirelessredstone.smp.network.PacketHandlerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.RedstoneWirelessConnection;

public class mod_WirelessRedstoneSMP extends BaseMod {
	public static BaseMod instance;

	public void modsLoaded() {
		if (!WirelessRedstone.isLoaded
				&& ModLoader.isModLoaded("mod_MinecraftForge")) {
			WirelessRedstone.isLoaded = WirelessRedstone.initialize();
		}
	}

	public mod_WirelessRedstoneSMP() {
		instance = this;
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.6";
	}

	@Override
	public void onClientLogin(EntityPlayer entityplayer) {
		WirelessRedstone.addConnectionForPlayer(new RedstoneWirelessConnection(null, entityplayer, "WIFI"), entityplayer);
	}

    public void onClientLogout(EntityPlayer entityplayer)
    {
    	WirelessRedstone.removeConnectionForPlayer(entityplayer);
    }
	
/*	public void receiveCustomPacket(Packet250CustomPayload payload) {
		((RedstoneWirelessConnection)WirelessRedstone.redstoneWirelessConnection).onPacketData(payload);
	}*/
	
	@Override
    public void onPacket250Received(EntityPlayer entityplayer, Packet250CustomPayload payload)
    {
		NetworkConnections connection = WirelessRedstone.getConnectionForPlayer("WIFI", entityplayer);
		if (connection != null) {
			connection.onPacketData(payload);
		}
    }
}
