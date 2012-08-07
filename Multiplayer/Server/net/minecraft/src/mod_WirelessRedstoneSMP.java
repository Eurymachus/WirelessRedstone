package net.minecraft.src;

import net.minecraft.src.wirelessredstone.WirelessRedstone;

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

	/**
	 * Called when a new client logs in.
	 * 
	 * @param player
	 */
	public void onClientLogin(EntityPlayer player) {
		WirelessRedstone.redstoneWirelessConnection.onLogin(
				((EntityPlayerMP) player).playerNetServerHandler.netManager,
				null, mod_WirelessRedstoneSMP.instance);
	}

	@Override
	public void onPacket250Received(EntityPlayer entityplayer,
			Packet250CustomPayload payload) {
		WirelessRedstone.redstoneWirelessConnection.onPacketData(entityplayer,
				payload);
	}
}
