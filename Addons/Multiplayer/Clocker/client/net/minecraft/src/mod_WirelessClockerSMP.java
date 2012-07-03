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
import net.minecraft.src.wirelessredstone.addon.clocker.WirelessClockerSMP;

public class mod_WirelessClockerSMP extends NetworkMod {
	public NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (!WirelessClockerSMP.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")
				&& ModLoader.isModLoaded("mod_WirelessClocker")) {
			WirelessClockerSMP.isLoaded = WirelessClockerSMP.initialize();
		}
	}

	public mod_WirelessClockerSMP() {
		instance = this;
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstoneSMP;after:mod_WirelessClocker";
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
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
