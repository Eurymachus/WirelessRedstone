/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package net.minecraft.src;

import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.wirelessredstone.WirelessRedstoneSMP;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class mod_WirelessRedstoneSMP extends NetworkMod {

	public static NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_MinecraftForge")) {
			if (!WirelessRedstoneSMP.isLoaded
					&& ModLoader.isModLoaded("mod_WirelessRedstone")) {
				WirelessRedstoneSMP.isLoaded = WirelessRedstoneSMP.initialize();
			}
		} else
			LoggerRedstoneWireless
					.getInstance(
							LoggerRedstoneWireless.filterClassName(this
									.toString()))
					.write("Minecraft Forge is not Installed, aborting load sequence!",
							LoggerRedstoneWireless.LogLevel.WARNING);
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstone";
	}

	public mod_WirelessRedstoneSMP() {
		/*
		 * if(!ModLoader.isModLoaded("mod_MinecraftForge")) {
		 * JOptionPane.showMessageDialog(null, this.getName() + ":" +
		 * this.getVersion() + ", requires Minecraft Forge to work.\n" +
		 * "Please download and install the The Forge Mod.\n" +
		 * "(Author Ali4z, Programmer Eurymachus)"); } if
		 * (!ModLoader.isModLoaded("mod_WirelessRedstone")) {
		 * JOptionPane.showMessageDialog(null, this.getName() + ":" +
		 * this.getVersion() + ", requires mod_WirelessRedstone to work.\n" +
		 * "Please download and install the Wireless Redstone Mod.\n" +
		 * "(Author Ali4z, Programmer Eurymachus)"); }
		 */
		instance = this;
	}

	@Override
	public String getVersion() {
		return "1.6";
	}

	@Override
	public void load() {
	}

	@Override
	public boolean clientSideRequired() {
		return true;
	}

	@Override
	public boolean serverSideRequired() {
		return false;
	}
}
