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
import net.minecraft.src.wirelessredstone.addon.powerc.PowerConfigurator;
import net.minecraft.src.wirelessredstone.addon.powerc.PowerConfiguratorSMP;

public class mod_PowerConfiguratorSMP extends NetworkMod {
	public static NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (!PowerConfiguratorSMP.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")
				&& ModLoader.isModLoaded("mod_PowerConfigurator")) {
			PowerConfiguratorSMP.isLoaded = PowerConfiguratorSMP.initialize();
		}
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstoneSMP;after:mod_PowerConfigurator";
	}

	public mod_PowerConfiguratorSMP() {
		instance = this;
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
