package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessTriangulator;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.addon.triangulator.data.WirelessTriangulatorData;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.PacketHandlerWirelessTriangulator;
import net.minecraft.src.wirelessredstone.addon.triangulator.overrides.GuiRedstoneWirelessTriangulatorOverrideSMP;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.overrides.BaseModOverrideSMP;

public class WirelessTriangulatorSMP {
	public static boolean isLoaded = false;
	private static int ticksInGame = 0;
	private static int ticksInGUI = 0;

	public static boolean initialize() {
		try {
			//ModLoader.setInGameHook(mod_WirelessTriangulator.instance, true, true);

			registerConnHandler();

			addBaseModOverride();
			
			addGuiOverrode();

			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless
							.filterClassName(WirelessTriangulatorSMP.class
									.toString())).write(
					"Initialization failed.",
					LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}

	private static void addGuiOverrode() {
		GuiRedstoneWirelessTriangulatorOverrideSMP override = new GuiRedstoneWirelessTriangulatorOverrideSMP();
		WirelessTriangulator.guiTriang.addOverride(override);
	}

	private static void addBaseModOverride() {
		BaseModOverrideSMP override = new BaseModOverrideSMP();
		WirelessTriangulator.addOverride(override);
	}

	private static void registerConnHandler() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

/*	public static boolean tick(Minecraft mc) {
		EntityPlayer entityplayer = mc.thePlayer;
		World world = mc.theWorld;
		if (mc.currentScreen instanceof GuiRedstoneWirelessTriangulator) {
			++ticksInGUI;
			return true;
		} else if (ticksInGUI != 0) {
			ticksInGUI = 0;
		}
		if (!(ticksInGUI > 0)) {
			if (ticksInGame == 40) {
				ticksInGame = 0;
				if (entityplayer.inventory
						.hasItem(WirelessTriangulator.triangID)
						&& entityplayer.inventory.getCurrentItem().equals(
								WirelessTriangulator.itemTriang)) {
					WirelessTriangulatorData data = WirelessTriangulator
							.getDeviceData(
									entityplayer.inventory.getCurrentItem(),
									world, entityplayer);
					if (mc.theWorld.isRemote)
						PacketHandlerWirelessTriangulator.PacketHandlerOutput
								.sendWirelessTriangulatorPacket(mc.thePlayer,
										"requestTriangulation", data.getFreq());
				}
			}
			++ticksInGame;
			return true;
		}
		return true;
	}*/
}
