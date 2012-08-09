package net.minecraft.src;

import net.minecraft.src.wirelessredstone.WirelessRedstone;
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
		WirelessRedstone.registerConnHandler(entityplayer,
				new RedstoneWirelessConnection(null, entityplayer,
						WirelessRedstone.channel), mod_WirelessRedstoneSMP.instance);
	}

	public void onClientLogout(EntityPlayer entityplayer) {
		WirelessRedstone.removeConnectionForPlayer(entityplayer);
	}

	@Override
	public void onPacket250Received(EntityPlayer entityplayer,
			Packet250CustomPayload payload) {
		WirelessRedstone.handlePacket(WirelessRedstone.channel, entityplayer,
				payload);
	}
}
