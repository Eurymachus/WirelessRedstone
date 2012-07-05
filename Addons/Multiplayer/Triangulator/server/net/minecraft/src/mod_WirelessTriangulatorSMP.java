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
import net.minecraft.src.wirelessredstone.addon.triangulator.WirelessTriangulator;

public class mod_WirelessTriangulatorSMP extends NetworkMod {
	public static NetworkMod instance;

	public mod_WirelessTriangulatorSMP() {
		instance = this;
	}

	@Override
	public void modsLoaded() {
		if (!WirelessTriangulator.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")) {
			WirelessTriangulator.isLoaded = WirelessTriangulator.initialize();
		}
	}

	/*
	 * @Override public boolean onTickInGame(MinecraftServer mc) { if
	 * (!WirelessTriangulator.isLoaded) return true; else { return
	 * WirelessTriangulator.tick(mc); } }
	 */

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstoneSMP";
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
