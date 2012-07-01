package net.minecraft.src;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.overrides.RedstoneEtherOverrideServer;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnection;

public class mod_WirelessRedstoneSMP extends NetworkMod {
	public static NetworkMod instance;

	public mod_WirelessRedstoneSMP() {
		instance = this;

		MinecraftForge.registerConnectionHandler(new NetworkConnection());

		RedstoneEtherOverrideServer etherOverride = new RedstoneEtherOverrideServer();
		RedstoneEther.getInstance().addOverride(etherOverride);

		WirelessRedstone.load();
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.6";
	}

	@Override
	public boolean clientSideRequired() {
		return true;
	}

	@Override
	public boolean serverSideRequired() {
		return true;
	}
}
