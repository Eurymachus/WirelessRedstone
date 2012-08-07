package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
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
		EntityPlayerMP entityplayermp = null;
		if (entityplayer instanceof EntityPlayerMP) {
			entityplayermp = (EntityPlayerMP)entityplayer;
		}
		if (entityplayermp != null) {
			NetworkConnections connection;
			if (!WirelessRedstone.redstoneWirelessConnection.containsKey(entityplayermp)) {
				WirelessRedstone.redstoneWirelessConnection.put(entityplayermp, new RedstoneWirelessConnection(entityplayermp.playerNetServerHandler.netManager, entityplayermp, "WIFI"));
			}
			connection = WirelessRedstone.redstoneWirelessConnection.get(entityplayermp);
			connection.onLogin(entityplayermp.playerNetServerHandler.netManager, null, mod_WirelessRedstoneSMP.instance);
			ModLoader.getLogger().warning("Channel Mod is: " + FMLCommonHandler.instance().getModForChannel("WIFI"));
		}
	}
	
/*	public void receiveCustomPacket(Packet250CustomPayload payload) {
		((RedstoneWirelessConnection)WirelessRedstone.redstoneWirelessConnection).onPacketData(payload);
	}*/
	
	@Override
    public void onPacket250Received(EntityPlayer entityplayer, Packet250CustomPayload payload)
    {
		EntityPlayerMP entityplayermp = null;
    	if (entityplayer instanceof EntityPlayerMP) {
    		entityplayermp = (EntityPlayerMP)entityplayer;
    	}
    	if (entityplayermp != null) {
			NetworkConnections connection;
			if (!WirelessRedstone.redstoneWirelessConnection.containsKey(entityplayermp)) {
				onClientLogin(entityplayer);
			}
			connection = WirelessRedstone.redstoneWirelessConnection.get(entityplayermp);
			connection.onPacketData(payload);
    	}
    }
}
