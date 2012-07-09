package net.minecraft.src;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.overrides.RedstoneEtherOverrideServer;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnection;

public class mod_WirelessRedstoneSMP extends NetworkMod {
	public static NetworkMod instance;
	
	public void modsLoaded() {
		if (!WirelessRedstone.isLoaded && ModLoader.isModLoaded("mod_MinecraftForge")) {
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
	public boolean clientSideRequired() {
		return true;
	}

	@Override
	public boolean serverSideRequired() {
		return true;
	}
}
